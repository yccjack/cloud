package com.mystical.cloud.auth.signature.service;

import com.mystical.cloud.auth.signature.annotation.*;
import com.mystical.cloud.auth.signature.exception.SignedException;
import com.mystical.cloud.auth.signature.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * @author veneris <ryu@oever.cn>
 * @version v1.0
 */
@Aspect
@Component
@Primary
@Slf4j
public class BaseSignedService {

    @Value("${signature.algorithm:HmacSHA1}")
    protected String ALGORITHM;
    @Value("${signature.time-diff-max:100}")
    protected long TIME_DIFF_MAX;
    @Autowired(required = false)
    protected RedisUtil redisUtil;
    protected static final String PREFIX = "atpapi:signature:";

    @Pointcut("@within(com.mystical.cloud.auth.signature.annotation.SignedMapping) || @annotation(com.mystical.cloud.auth.signature.annotation.SignedMapping) ")
    public void mapping() {
    }


    @Before("mapping() ")
    public void before(JoinPoint joinPoint) throws Exception {
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();

        //  Get the annotations on the class or method, and get the parameter
        SignedMapping signedMapping = AnnotationUtils.findAnnotation(method, SignedMapping.class);
        if (signedMapping == null) {
            signedMapping = AnnotationUtils.findAnnotation(joinPoint.getTarget().getClass(), SignedMapping.class);
        }

        if(signedMapping.resubmit()){
            return;
        }
        Class clazz = signedMapping.value();

        //  If there are other implementation classes, prevent multiple executions here
        if (!this.getClass().equals(clazz)) {
            return;
        }

        Object[] args = joinPoint.getArgs();

        boolean paramHasSignedEntity = false;
        for (Object obj : args) {
            SignedEntity signedEntity = AnnotationUtils.findAnnotation(obj.getClass(), SignedEntity.class);
            if (signedEntity != null) {
                paramHasSignedEntity = true;
                entry(obj);
                break;
            }
        }
        if(!paramHasSignedEntity){
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String appId = request.getHeader("aptapi-appid");
            long timestamp = Long.parseLong(request.getHeader("aptapi-timestamp"));
            int nonce = Integer.parseInt(request.getHeader("aptapi-nonce"));
            String signatureParam = request.getHeader("aptapi-signature");
            Map<String, Object> headerMap=new HashMap<>(3);
            //处理header name
            headerMap.put("appId",appId);
            headerMap.put("timestamp",timestamp);
            headerMap.put("nonce",nonce);

            isTimeDiffLarge(timestamp);

            isReplayAttack(appId, timestamp, nonce, signatureParam);

            String signature = getSignature(appId, headerMap);

            //  If the signatures are inconsistent, throw an exception
            if (!signatureParam.equals(signature))
                throw new SignedException.SignatureError(signatureParam);
        }

    }


    /**
     * Entry method, used to call other methods uniformly
     *
     * @param obj
     * @throws Exception
     */
    public void entry(Object obj) throws Exception {

        Map map = object2Map(obj);

        String appId = (String) getParamByAnnotation(obj, SignedAppId.class);
        long timestamp = (Long) getParamByAnnotation(obj, SignedTimestamp.class);
        int nonce = (Integer) getParamByAnnotation(obj, SignedNonce.class);
        String signatureParam = (String) getParamByAnnotation(obj, Signature.class);

        isTimeDiffLarge(timestamp);

        isReplayAttack(appId, timestamp, nonce, signatureParam);

        String signature = getSignature(appId, map);

        //  If the signatures are inconsistent, throw an exception
        if (!signatureParam.equals(signature))
            throw new SignedException.SignatureError(signatureParam);
    }


    /**
     * Convert obj to map and verify that the field is not empty
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public Map<String, Object> object2Map(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            //  If the field is not ignored, put it in the map
            //  If the field is null, throw an exception
            SignedIgnore signedIgnore = AnnotationUtils.findAnnotation(field, SignedIgnore.class);
            if (signedIgnore == null) {
                if (field.get(obj) == null) {
                    throw new SignedException.NullParam(field.getName());
                }
                map.put(field.getName(), field.get(obj));
            }
        }
        return map;
    }


    /**
     * Get the field value of obj through annotation
     *
     * @param obj
     * @param annotationType
     * @return
     * @throws IllegalAccessException
     */
    public final Object getParamByAnnotation(Object obj, Class<? extends Annotation> annotationType) throws IllegalAccessException {
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Annotation annotation = AnnotationUtils.findAnnotation(field, annotationType);
            if (annotation != null) {
                return field.get(obj);
            }
        }
        return null;
    }


    /**
     * Get the appSecret by appId
     *
     * @param appId
     * @return
     */
    public String getAppSecret(String appId) {
        Object appSecret = redisUtil.get(appId);
        if (appSecret == null)
            throw new SignedException.AppIdInvalid(appId);
        return appSecret.toString();
    }

    /**
     * If the time difference between the server and the client is too large, throw an exception
     *
     * @param timestamp
     */
    public void isTimeDiffLarge(long timestamp) {
        long diff = timestamp - System.currentTimeMillis() / 1000;
        if (Math.abs(diff) > TIME_DIFF_MAX) {
            throw new SignedException.TimestampError(diff + "");
        }
    }


    /**
     * Judge whether it is a replay attack by time stamp and nonce
     */
    public void isReplayAttack(String appId, long timestamp, int nonce, String signature) {
        String key = PREFIX + appId + "_" + timestamp + "_" + nonce;
        Object obj = redisUtil.get(key);
        if (obj != null && signature.equals(obj.toString()))
            throw new SignedException.ReplayAttack(appId, timestamp, nonce);
        else
            redisUtil.set(key, signature, TIME_DIFF_MAX);
    }


    /**
     * Signature calculation and verification
     *
     * @param appId
     * @param map
     */
    public String getSignature(String appId, Map map) throws NoSuchAlgorithmException, InvalidKeyException {

        String appSecret = getAppSecret(appId);

        //  Sort the parameters by ascending ASCII
        SortedMap<String, Object> sortedMap = new TreeMap(map);

        //  Splice the parameters
        //  e.g. "key1=value1&key2=value2"
        StringBuilder plainText = new StringBuilder();
        for (Map.Entry<String, Object> entry : sortedMap.entrySet()) {
            if(entry.getKey().equals("signature")){
                continue;
            }
            plainText.append(entry.getKey()).append("=").append(entry.getValue());
            plainText.append("&");
        }
        plainText.deleteCharAt(plainText.length() - 1);

        //  The plain text is encrypted by algorithm and converted to Base64
        SecretKeySpec secretKeySpec = new SecretKeySpec(StringUtils.getBytesUtf8(appSecret), ALGORITHM);
        Mac mac = Mac.getInstance(ALGORITHM);
        mac.init(secretKeySpec);
        String result = Base64.encodeBase64String(mac.doFinal(StringUtils.getBytesUtf8(plainText.toString())));

        return result;
    }
}
