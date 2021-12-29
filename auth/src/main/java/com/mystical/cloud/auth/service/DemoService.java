package com.mystical.cloud.auth.service;

import java.util.Objects;

@FunctionalInterface
public interface DemoService<T> {

    boolean execute(T t);

    default DemoService<T> dispatch(DemoService<? super T> other) {
        Objects.requireNonNull(other);
        return other::execute;
    }
}
