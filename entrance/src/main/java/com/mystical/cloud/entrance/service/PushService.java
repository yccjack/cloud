package com.mystical.cloud.entrance.service;

import com.mystical.cloud.entrance.base.AbstractService;
import com.mystical.cloud.entrance.bean.PushDto;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author: MysticalYcc
 * @Date: 2021/4/16
 */
@Service
public class PushService extends AbstractService {

    public static final String STRATEGY = "push_location";

    public PushService() {
        super(STRATEGY);
    }

    public void push(PushDto pushDto){
        //todo 推送实现类
    }
}
