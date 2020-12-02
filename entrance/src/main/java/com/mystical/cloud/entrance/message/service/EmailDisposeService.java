package com.mystical.cloud.entrance.message.service;

import com.mystical.cloud.entrance.message.bean.BaseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * @author MysticalYcc
 */
@Component
@Log4j2
public class EmailDisposeService extends DisposeService {
    @Override
    public boolean deal(BaseDto baseDto) {

        log.info("组装完成");
        //todo 组装email
        return true;
    }
}
