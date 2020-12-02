package com.mystical.cloud.entrance.message.service;


import com.mystical.cloud.entrance.bean.response.CommonResponse;

/**
 * @author MysticalYcc
 */
public interface BaseService<T> {

    /**
     * 处理业务逻辑
     * @param obj 参数
     * @return CommonResponse
     */
      CommonResponse<Boolean> dispatch(T obj);
}
