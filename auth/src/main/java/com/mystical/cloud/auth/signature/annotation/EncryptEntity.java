package com.mystical.cloud.auth.signature.annotation;

import java.lang.annotation.*;

/**
 * 配置在需要解密的类上，如果有父类(非Object)直接注解在父类上，子类无需重复增加此注解
 * @Description:
 * @author: MysticalYcc
 * @Date: 2020/12/16
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EncryptEntity {
}
