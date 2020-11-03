package com.mystical.cloud.entrance.base;


import com.mystical.cloud.entrance.bean.CommonResponse;

/**
 * @author MysticalYcc
 */
public interface Dispatch<T> {

    /**
     * 分发
     * @param source 页面
     * @param operation 操作
     * @param obj 携带数据
     * @return json数据
     */
    CommonResponse<String> dispatch(String source, String operation, T obj);
}
