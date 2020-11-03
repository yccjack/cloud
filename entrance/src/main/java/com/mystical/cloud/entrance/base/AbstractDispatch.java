package com.mystical.cloud.entrance.base;

import org.springframework.beans.factory.InitializingBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author MysticalYcc
 */
public abstract class AbstractDispatch<T, V> implements Dispatch<T>, InitializingBean {


    protected Map<T, BaseService<V>> baseServices = new ConcurrentHashMap<>();

    /**
     * 注册服务
     */
    public abstract void registerService();

    protected final BaseService<V> getSource(T key) {
        BaseService<V> vBaseService = baseServices.get(key);
        if (vBaseService == null) {
            return new DefaultService<>();
        }
        return vBaseService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        registerService();
    }
}
