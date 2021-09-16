package com.mystical.cloud.auth.signature.service;

import com.mystical.cloud.auth.signature.annotation.Signature;
import com.mystical.cloud.auth.signature.annotation.SignedAppId;
import com.mystical.cloud.auth.signature.annotation.SignedNonce;
import com.mystical.cloud.auth.signature.annotation.SignedTimestamp;
import com.mystical.cloud.auth.signature.exception.SignedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * @author ycc
 * @version v1.0
 */
@Aspect
@Component
@Primary
@Slf4j
public class BaseSignedService extends SignedService {
    /**
     * 组装验证sign
     *
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException {@link NoSuchAlgorithmException }
     * @throws InvalidKeyException      InvalidKeyException {@link InvalidKeyException}
     */
    @Override
    protected void signedValidate() throws NoSuchAlgorithmException, InvalidKeyException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String appId = request.getHeader("aptapi-appid");
        long timestamp = Long.parseLong(request.getHeader("aptapi-timestamp"));
        int nonce = Integer.parseInt(request.getHeader("aptapi-nonce"));
        String signatureParam = request.getHeader("aptapi-signature");
        String data = request.getHeader("aptapi-data");
        Map<String, Object> headerMap = new HashMap<>(4);
        //处理header name
        headerMap.put("appId", appId);
        headerMap.put("timestamp", timestamp);
        headerMap.put("nonce", nonce);
        if (!StringUtils.isNotBlank(data)) {
            headerMap.put("data", data);
        }
        isTimeDiffLarge(timestamp);
        isReplayAttack(appId, timestamp, nonce, signatureParam);
        String signature = getSignature(appId, headerMap);
        //  If the signatures are inconsistent, throw an exception
        if (!signatureParam.equals(signature)) {
            throw new SignedException.SignatureError(signatureParam);
        }

    }
    /**
     * Entry method, used to call other methods uniformly
     *
     * @param obj
     * @throws Exception
     */
    @Override
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
     * Signature calculation and verification
     *
     * @param appId
     * @param map
     */
    public String getSignature(String appId, Map map) throws NoSuchAlgorithmException, InvalidKeyException {

        String appSecret = getAppSecret(appId);

        //  Sort the parameters by ascending ASCII
        SortedMap<String, Object> sortedMap = new TreeMap<>(map);

        //  Splice the parameters
        //  e.g. "key1=value1&key2=value2"
        StringBuilder plainText = new StringBuilder();
        for (Map.Entry<String, Object> entry : sortedMap.entrySet()) {
            if (entry.getKey().equals("signature")) {
                continue;
            }
            if (entry.getValue() == null) {
                continue;
            }
            plainText.append(entry.getKey()).append("=").append(entry.getValue());
            plainText.append("&");
        }
        plainText.deleteCharAt(plainText.length() - 1);

        //  The plain text is encrypted by algorithm and converted to Base64
        SecretKeySpec secretKeySpec = new SecretKeySpec(org.apache.commons.codec.binary.StringUtils.getBytesUtf8(appSecret), ALGORITHM);
        Mac mac = Mac.getInstance(ALGORITHM);
        mac.init(secretKeySpec);

        return Base64.encodeBase64String(mac.doFinal(org.apache.commons.codec.binary.StringUtils.getBytesUtf8(plainText.toString())));
    }
}
