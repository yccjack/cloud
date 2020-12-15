package com.mystical.cloud.auth.signature.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    private Map<String, String> cache = new ConcurrentHashMap<>(1024);


    public void remove(String key) {
        if (exists(key)) {
            cache.remove(key);
        }
    }

    public boolean exists(String key) {
        return cache.get(key) == null;
    }

    public String get(String key) {
        return cache.get(key);
    }


    public void set(String key, String value) {
        cache.put(key, value);
    }

    public void set(String key, String value, Long expire) {
        cache.put(key, value);
    }
}
