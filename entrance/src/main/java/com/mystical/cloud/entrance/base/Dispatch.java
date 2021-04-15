package com.mystical.cloud.entrance.base;


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
    String dispatch(String source, String operation, T obj);
}
