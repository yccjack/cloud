package com.mystical.cloud.auth.service;


@FunctionalInterface
public interface StrategyExecutor<T> {

    boolean handler(T data);

}
