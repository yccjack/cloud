package com.mystical.cloud.entrance.service;

import com.mystical.cloud.entrance.mapper.CityMapper;
import com.mystical.cloud.entrance.mapper.TestUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EsService implements ApplicationRunner {

    @Autowired
    RestHighLevelClient client;


    @Autowired
    CityMapper cityMapper;

    @Autowired
    TestUserMapper testUserMapper;


    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
