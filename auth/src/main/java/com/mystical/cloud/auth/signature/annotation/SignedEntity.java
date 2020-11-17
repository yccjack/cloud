package com.mystical.cloud.auth.signature.annotation;


import java.lang.annotation.*;

/**
 * The annotation indicates that this is an entity class used as a request
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SignedEntity {
}
