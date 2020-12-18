package com.mystical.cloud.auth.signature.annotation;

import java.lang.annotation.*;

/**

 * @Description:
 * @author: MysticalYcc
 * @Date: 2020/12/15
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
public @interface EncryptMapper {
    /**
     * 需要解密的字段名称，Get请求不支持设置多个，请增加Get请求拼接后请求的加密串，
     * 不输入默认取名字param的解析
     * @return
     */
    String value() default "param";

    /**
     * 分割符
     * @return
     */
    String segment() default "@@";

    /**
     * 默认加此注解后旧版不支持，0:不支持 ,1:仅支持旧版，2：支持旧版+新加密版本
     * @return
     */
    int versionSupport() default 0;

    /**
     * 返回数据的加密，默认需要加密
     * @return
     */
    boolean returnSecret() default true;
}
