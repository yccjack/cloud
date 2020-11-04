package com.mystical.cloud.entrance.base;

import lombok.extern.slf4j.Slf4j;

/**
 * @author MysticalYcc
 */
@Slf4j
public class DefaultService<T> implements BaseService<T> {
    @Override
    public String dispose(T data) {
        log.warn("source参数错误，或者未注册服务，返回defaultService处理。");
        return "未发现相关服务。";
    }
}
