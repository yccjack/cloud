package com.mystical.cloud.entrance.message.service;




/**
 * @author MysticalYcc
 */
public abstract class AbstractService<T> implements BaseService<T> {
    @Override
    public Boolean dispatch(T param) {
        return deal(param);
    }

    /**
     * 处理
     * @param param
     * @return
     */
    protected abstract  boolean deal(T param);
}
