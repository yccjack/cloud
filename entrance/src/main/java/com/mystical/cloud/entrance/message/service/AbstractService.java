package com.mystical.cloud.entrance.message.service;


import com.mystical.cloud.entrance.bean.response.CommonResponse;
import com.mystical.cloud.entrance.bean.response.CommonResultEnum;

/**
 * @author MysticalYcc
 */
public abstract class AbstractService<T> implements BaseService<T> {
    @Override
    public CommonResponse<Boolean> dispatch(T param) {
        if (!deal(param)) {
            return new CommonResponse<>(CommonResultEnum.FAILED);
        }
        return new CommonResponse<>(CommonResultEnum.SUCCESS);
    }

    /**
     * 处理
     * @param param
     * @return
     */
    protected abstract  boolean deal(T param);
}
