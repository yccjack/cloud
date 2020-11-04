package com.mystical.cloud.entrance.service;

import com.mystical.cloud.entrance.base.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author MysticalYcc
 */
@Service("authorization")
@Slf4j
public class AuthorizationService extends AbstractService {

    public String submit(String data) {
        //处理数据
        //todo 完成后续操作
        log.info("待处理的数据:[{}]", data);
        return data + " 已处理完成";
    }


    public String renew(String data) {
        log.info("待处理的数据:[{}]", data);
        return data + " 已处理完成";
    }

}
