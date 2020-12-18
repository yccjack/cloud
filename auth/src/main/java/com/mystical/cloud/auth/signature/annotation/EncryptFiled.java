package com.mystical.cloud.auth.signature.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description:
 * @author: MysticalYcc
 * @Date: 2020/12/16
 */
@Target({ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface EncryptFiled {
}
