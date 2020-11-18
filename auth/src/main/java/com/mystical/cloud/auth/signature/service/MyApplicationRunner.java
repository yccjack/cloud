package com.mystical.cloud.auth.signature.service;


import com.mystical.cloud.auth.signature.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private RedisUtil redisUtil;
    public static String APP_ID;
    public static String APP_SECRET;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initAppId();
    }

    public void initAppId() {
        // You can initialize the cache here,
        // for example, write the appIds and appSecrets to redis from db
        log.info("Initialization the appId and appSecret...");
        redisUtil.set(APP_ID, APP_SECRET);
    }

    @Value("${signature.app.id:APP_ID_TEST}")
    public void setAppId(String appId) {
        APP_ID = appId;
    }

    @Value("${signature.app.secret:APP_SECRET_TEST}")
    public void setAppSecret(String appSecret) {
        APP_SECRET = appSecret;
    }
}
