package com.mystical.cloud.entrance.base;

/**
 * @author MysticalYcc
 */
public interface BaseService<T> {

    /**
     * 处理
     * @param data 数据
     * @return json数据
     */
     String dispose(T data);
}
