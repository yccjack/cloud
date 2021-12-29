package com.mystical.cloud.auth.service;

public class BaseStrategyService implements StrategyExecutor<String> {

    public boolean handler(String data) {
        return false;
    }
}
