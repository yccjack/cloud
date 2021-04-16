package com.mystical.cloud.entrance.service;


import com.mystical.cloud.entrance.base.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author MysticalYcc
 */
@Service("longApplication")
@Slf4j
public class LongApplicationService extends AbstractService {
    private final static String STRATEGY = "la";

    public LongApplicationService() {
        super(STRATEGY);
    }

    public String submit(String data) {
        log.info("待处理的数据:[{}]", data);
        return data + " 已处理完成";
    }
}
