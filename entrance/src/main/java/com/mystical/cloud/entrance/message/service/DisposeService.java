package com.mystical.cloud.entrance.message.service;


import com.mystical.cloud.entrance.message.bean.BaseDto;

/**
 * @author MysticalYcc
 */
public class DisposeService extends AbstractService<BaseDto> {

    DisposeService(){

    }
    @Override
    protected boolean deal(BaseDto param) {
        return true;
    }

}



