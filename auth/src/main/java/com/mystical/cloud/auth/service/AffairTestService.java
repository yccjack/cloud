package com.mystical.cloud.auth.service;

import com.mystical.cloud.auth.bean.UserInfo;
import com.mystical.cloud.auth.listener.QueueListener;
import com.mystical.cloud.auth.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ArrayBlockingQueue;

@Service
@Log4j2
public class AffairTestService {
    public static ArrayBlockingQueue<String> queue = QueueListener.queue;
    @Autowired
    AffairTestService affairTestService;

    @Autowired
    UserMapper userMapper;

    public String testAffair(){
        try {
            log.info("测试事务bug");
            String username = "testAffair";
            process(username);
            queue.add(username);
        }catch (Exception e){
            throw new ClassCastException();
        }

        return null;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void process( String username) {
        UserInfo userInfo = userMapper.selectById(1);
        userInfo.setUsername(username);
        userMapper.updateById(userInfo);
    }

}
