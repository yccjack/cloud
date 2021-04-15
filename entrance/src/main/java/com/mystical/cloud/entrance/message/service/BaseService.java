package com.mystical.cloud.entrance.message.service;


/**
 * @author MysticalYcc
 */
public interface BaseService<T> {

    /**
     * 处理业务逻辑
     * @param obj 参数
     * @return CommonResponse
     */
    Boolean dispatch(T obj);
}
