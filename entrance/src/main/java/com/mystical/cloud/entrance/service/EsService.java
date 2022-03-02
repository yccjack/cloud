package com.mystical.cloud.entrance.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mystical.cloud.entrance.bean.City;
import com.mystical.cloud.entrance.bean.User;
import com.mystical.cloud.entrance.mapper.CityMapper;
import com.mystical.cloud.entrance.mapper.TestUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<User> users = testUserMapper.selectList(Wrappers.<User>lambdaQuery());

        BulkRequest request = new BulkRequest();
        request.timeout(TimeValue.timeValueMinutes(2));
        request.timeout("2m");
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        request.setRefreshPolicy("wait_for");
        int count=0;
        int size = users.size();
        for (int i = 0; i < size; i++) {
            request.add(new IndexRequest("user").source(JSON.toJSONString(users.get(i)), XContentType.JSON));
            if (i % 10000 == 0) {
                client.bulk(request, RequestOptions.DEFAULT);
                request =new BulkRequest();
            }
            if(i==size-1){
                client.bulk(request, RequestOptions.DEFAULT);
            }
        }
    }
}
