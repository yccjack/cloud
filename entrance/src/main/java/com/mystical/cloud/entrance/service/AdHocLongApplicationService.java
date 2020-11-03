package com.mystical.cloud.entrance.service;

import com.alibaba.fastjson.JSON;

import com.mystical.cloud.entrance.base.AbstractService;
import com.mystical.cloud.entrance.bean.NewEvent;
import com.mystical.cloud.entrance.mapper.AuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author MysticalYcc
 */
@Service("adHocLongApplication")
@Slf4j
public class AdHocLongApplicationService extends AbstractService {

    @Autowired
    AuMapper auMapper;


    public String submit(String data) {
        NewEvent newEvent = JSON.parseObject(data, NewEvent.class);
       // auMapper.insert(newEvent);
        log.info("待处理的数据:[{}]", data);
        return data + " 已处理完成";
    }
}
