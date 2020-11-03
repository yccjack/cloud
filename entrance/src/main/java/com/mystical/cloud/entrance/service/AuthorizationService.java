package com.mystical.cloud.entrance.service;

import com.alibaba.fastjson.JSON;

import com.mystical.cloud.entrance.base.AbstractService;
import com.mystical.cloud.entrance.bean.AuthorizationDto;
import com.mystical.cloud.entrance.mapper.AuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author MysticalYcc
 */
@Service("authorization")
@Slf4j
public class AuthorizationService extends AbstractService {

    private FlowService flowService;

    private AuMapper auMapper;

    public String submit(String data) {
        AuthorizationDto authorizationDto = JSON.parseObject(data, AuthorizationDto.class);
         auMapper.insert(authorizationDto);

        String flowId = flowService.startFlow(authorizationDto.getId());
        //todo 完成后续操作
        log.info("待处理的数据:[{}]", data);
        return data + " 已处理完成";
    }


    public String renew(String data) {
        log.info("待处理的数据:[{}]", data);
        return data + " 已处理完成";
    }


    @Autowired
    public void setFlowService(FlowService flowService) {
        this.flowService = flowService;
    }
}
