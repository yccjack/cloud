package com.mystical.cloud.auth.signature.annotation;



import com.mystical.cloud.auth.signature.service.BaseSignedService;
import com.mystical.cloud.auth.signature.util.RedisUtil;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * The annotation is in the Application class and is used to scan other implementation classes
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import({BaseSignedService.class, RedisUtil.class})
public @interface SignedScan {
}
