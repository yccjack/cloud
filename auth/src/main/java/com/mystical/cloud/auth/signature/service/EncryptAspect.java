package com.mystical.cloud.auth.signature.service;

import com.alibaba.fastjson.JSON;
import com.mystical.cloud.auth.utils.EncryptUtil;
import com.mystical.cloud.auth.utils.RSAEncrypt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.security.InvalidKeyException;

/**
 * @Description:
 * @author: MysticalYcc
 * @Date: 2020/12/15
 */
@Aspect
@Component
@Primary
@Slf4j
public class EncryptAspect extends AbstractEncrypt {

    /**
     * Get the field value of obj through annotation
     *
     * @param obj            目标实体
     * @param annotationType 获取元素的注解类型
     * @return 标识 annotationType的 元素
     * @throws IllegalAccessException InvalidKeyException {@link InvalidKeyException}
     */
    public final Object getParamByAnnotation(Object obj, Class<? extends Annotation> annotationType) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
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
     * 对返回数据进行整体AES加密
     *
     * @param apiResult
     * @throws Exception
     */
    @Override
    public String disReturnData(Object apiResult) throws Exception {

        return EncryptUtil.encrypt(JSON.toJSONString(apiResult), threadPwd.get());
    }

    /**
     * 解密算法
     *
     * @param secretParam
     * @param segment
     * @return
     * @throws Exception
     */
    @Override
    public String algorithm(String secretParam, String segment) throws Exception {
        String pwd = secretParam.substring(secretParam.lastIndexOf(segment) + segment.length());
        byte[] bytes = Base64.decodeBase64(pwd);
        String decryptPwd = RSAEncrypt.decryptByPrivateKey(bytes);
        log.info("请求的RSA加密串：[{}]", decryptPwd);
        String requestParamSecret = secretParam.substring(0, secretParam.lastIndexOf(segment));
        String decrypt = EncryptUtil.decrypt(requestParamSecret, decryptPwd);
        threadPwd.set(decryptPwd);
        log.info("AES解析的数据为：[{}],解析前数据:[{}]", decrypt, requestParamSecret);
        return decrypt;
    }
}
