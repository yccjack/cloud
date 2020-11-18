package com.mystical.cloud.auth.signature.annotation;




import com.mystical.cloud.auth.signature.service.BaseSignedService;

import java.lang.annotation.*;

/**
 * The annotation indicates that the RESTful api needs to be signed
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
public @interface SignedMapping {
    Class<?> value() default BaseSignedService.class;
    boolean resubmit() default false;//允许重复请求
}
