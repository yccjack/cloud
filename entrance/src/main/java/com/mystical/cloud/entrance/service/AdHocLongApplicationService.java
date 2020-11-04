package com.mystical.cloud.entrance.service;

import com.mystical.cloud.entrance.base.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author MysticalYcc
 */
@Service("adHocLongApplication")
@Slf4j
public class AdHocLongApplicationService extends AbstractService {



    public String submit(String data) {
        //处理数据
        log.info("待处理的数据:[{}]", data);
        return data + " 已处理完成";
    }
}
