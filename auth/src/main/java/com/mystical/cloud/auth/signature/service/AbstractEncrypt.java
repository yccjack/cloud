package com.mystical.cloud.auth.signature.service;


import com.mystical.cloud.auth.signature.annotation.EncryptEntity;
import com.mystical.cloud.auth.signature.annotation.EncryptFiled;
import com.mystical.cloud.auth.signature.annotation.EncryptMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Description:
 * @author: MysticalYcc
 * @Date: 2020/12/18
 */
public abstract class AbstractEncrypt {

    @Pointcut("@within(com.mystical.cloud.auth.signature.annotation.EncryptMapper) || @annotation(com.mystical.cloud.auth.signature.annotation.EncryptMapper) ")
    public void mapping() {
    }

    ThreadLocal<String> threadPwd = ThreadLocal.withInitial(() -> "");

    @Around("mapping() ")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        EncryptMapper annotation = AnnotationUtils.findAnnotation(method, EncryptMapper.class);
        if (annotation == null) {
            annotation = AnnotationUtils.findAnnotation(joinPoint.getTarget().getClass(), EncryptMapper.class);
        }
        return disAround(joinPoint, sign, annotation);
    }

    private Object disAround(ProceedingJoinPoint joinPoint, MethodSignature sign, EncryptMapper annotation) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (annotation == null) {
            return getApiResult(joinPoint, args, false);
        }
        //版本支持
        int versionSupport = annotation.versionSupport();
        if (versionSupport == 1) {
            return joinPoint.proceed(args);
        }
        //加密字段名
        String value = annotation.value();
        if ("".equals(value)) {
            value = "param";
        }
        //加密分割符。
        String segment = annotation.segment();
        boolean returnSecret = annotation.returnSecret();
        String[] parameterNames = sign.getParameterNames();
        int count = 0;

        if (parameterNames != null && parameterNames.length > 0) {
            if ("all".equalsIgnoreCase(value)) {
                for (Object arg : args) {
                    decrypt(args, segment, count, arg);
                    count++;
                }
            } else {
                for (String param : parameterNames) {
                    if (value.equalsIgnoreCase(param)) {
                        Object arg = args[count];
                        if ("param".equalsIgnoreCase(value) && arg == null && versionSupport == 2) {
                            return joinPoint.proceed(args);
                        }
                        decrypt(args, segment, count, arg);
                    }
                    count++;
                }
            }

        }
        Object apiResult = getApiResult(joinPoint, args, returnSecret);
        threadPwd.remove();
        return apiResult;
    }


    /**
     * 获取加密字段，对加密数据进行解析，
     * 1。解析List数据，遍历List
     * 2. 对目标类型进行解析，非String类型需要找到对应的加解密字段进行解密
     * 3. 对目标类型的父类进行解析，找到需要加解密的父类字段，
     *
     * @param args
     * @param segment
     * @param count
     * @param arg
     * @throws Exception
     */
    private void decrypt(Object[] args, String segment, int count, Object arg) throws Exception {
        if (arg instanceof String) {
            String secretParam = String.valueOf(arg);
            args[count] = algorithm(secretParam, segment);
        } else {
            if (arg instanceof List) {
                List list = (List) arg;
                for (Object obj : list) {
                    Class<?> superclass = obj.getClass().getSuperclass();
                    Class<?> aClass = obj.getClass();
                    EncryptEntity encryptEntitySuper = AnnotationUtils.findAnnotation(superclass, EncryptEntity.class);
                    EncryptEntity encryptEntity = AnnotationUtils.findAnnotation(aClass, EncryptEntity.class);
                    if (encryptEntitySuper != null) {
                        disSecretFiled(segment, obj, superclass);
                    } else {
                        if (encryptEntity != null) {
                            disSecretFiled(segment, obj, aClass);
                        }
                    }
                }
            } else {
                Class<?> aClass = arg.getClass();
                Class<?> superclass = arg.getClass().getSuperclass();
                EncryptEntity encryptEntitySupper = AnnotationUtils.findAnnotation(superclass, EncryptEntity.class);
                EncryptEntity encryptEntity = AnnotationUtils.findAnnotation(aClass, EncryptEntity.class);
                if (encryptEntitySupper != null) {
                    disSecretFiled(segment, arg, superclass);
                } else {
                    if (encryptEntity != null) {
                        disSecretFiled(segment, arg, aClass);
                    }
                }
            }

        }
    }

    /**
     * 解析实体类中需要解密的字段
     *
     * @param segment
     * @param obj
     * @param aClass
     * @throws Exception
     */
    private void disSecretFiled(String segment, Object obj, Class<?> aClass) throws Exception {
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Annotation annotation = AnnotationUtils.findAnnotation(field, EncryptFiled.class);
            if (annotation != null) {
                Object o = field.get(obj);
                if (o instanceof List) {
                    List list = (List) o;
                    for (Object innerObj : list) {
                        Class<?> innerClass = innerObj.getClass();
                        EncryptEntity encryptEntity = AnnotationUtils.findAnnotation(innerClass, EncryptEntity.class);
                        if (encryptEntity != null) {
                            disSecretFiled(segment, innerObj, innerClass);
                        }
                    }
                } else {
                    if (o == null) {
                        continue;
                    }
                    String secretFiled = (String) o;
                    field.set(obj, algorithm(secretFiled, segment));
                }
            }
        }
    }


    /**
     * 根据解析完的参数列表执行目标方法，并根据配置对返回值进行处理(加密/不加密)
     *
     * @param joinPoint
     * @param args
     * @param returnSecret
     * @return
     * @throws Exception
     */
    private Object getApiResult(ProceedingJoinPoint joinPoint, Object[] args, boolean returnSecret) throws Exception {
        Object result = null;
        try {
            result = joinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        if (returnSecret) {
            disReturnData(result);
        }
        return result;
    }

    /**
     * 对返回数据进行加密
     *
     * @param apiResult
     * @throws Exception
     */
    protected abstract String disReturnData(Object apiResult) throws Exception;


    /**
     * 具体的解密方法，可自定义
     *
     * @param secretFiled
     * @param segment
     * @return
     * @throws Exception
     */
    protected abstract Object algorithm(String secretFiled, String segment) throws Exception;
}
