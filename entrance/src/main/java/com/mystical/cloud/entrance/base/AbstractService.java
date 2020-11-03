package com.mystical.cloud.entrance.base;

import com.mystical.cloud.entrance.bean.BaseDto;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author MysticalYcc
 */
@Slf4j
public class AbstractService implements BaseService<BaseDto> {


    @Override
    public String dispose(BaseDto data) {
        String operation = data.getOperation();
        Object invoke = getObject(data, operation);
        return (String) invoke;
    }
    public String create(BaseDto baseDto) {
        String event = baseDto.getEvent();
        String data = baseDto.getData();
        Object object = getObject(data, event);

        return (String) object;
    }

    protected <T> Object getObject(T data, String operation) {
        Object invoke = null;
        try {
            Method method = this.getClass().getMethod(operation, data.getClass());
            invoke = method.invoke(this, data);
        } catch (ReflectiveOperationException e) {
            log.error(" 未设置此方法：[{}],请检查是否输错，或者联系管理员设置[{}]方法", operation, operation);
            e.printStackTrace();
        }
        return invoke;
    }

}
