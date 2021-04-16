package com.mystical.cloud.entrance.base;

import com.mystical.cloud.entrance.bean.BaseDto;
import com.mystical.cloud.entrance.emun.CommonEnum;
import com.mystical.cloud.entrance.exception.EventBaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author MysticalYcc
 */
@Slf4j
public abstract class AbstractService implements BaseService<BaseDto> {

    protected String strategy = "ABS_BUS";

    /**
     * 注册表
     */
    protected static Map<String, BaseService<BaseDto>> strategyMap = new HashMap<>();

    public AbstractService(String strategy) {
        this.addBean(strategy);
    }

    @Override
    public String dispose(BaseDto data) {
        String operation = data.getOperation();
        Object invoke = null;
        try {
            invoke = getObject(data, operation);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
            e.printStackTrace();
            String format = String.format("未设置此方法：%s,请检查是否输错，或者联系管理员设置%s方法", operation, operation);
            log.error(format);
            e.printStackTrace();
            throw new EventBaseException(CommonEnum.METHOD_LOST.getResultCode(), format);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Throwable cause = e.getCause();
            //获取反射调用方法抛出的自定义异常
            if (cause instanceof EventBaseException) {
                EventBaseException ex = (EventBaseException) cause;
                throw new EventBaseException(ex.getErrorCode(), ex.getMessage());
            }
        }
        return (String) invoke;
    }

    public String create(BaseDto baseDto) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String event = baseDto.getEvent();
        String data = baseDto.getData();
        Object object = null;
        //获取需要执行的创建事件。
        object = getObject(data, event);
        return (String) object;
    }

    protected <T> Object getObject(T data, String operation) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object invoke;
        if (StringUtils.isEmpty(operation)) {
            throw new EventBaseException(CommonEnum.METHOD_LOST.getResultCode(), "无法找到对应的执行事件，请检查请求路劲和请求体参数");
        }
        Method method = this.getClass().getMethod(operation, data.getClass());
        invoke = method.invoke(this, data);
        System.out.println(invoke);

        return invoke;
    }

    /**
     * 将实现类注册到注册表中，如果存在则忽略，即无法覆盖已有注册类。
     *
     * @param strategy
     */
    protected void addBean(String strategy) {
        strategyMap.putIfAbsent(strategy, this);
    }

    public static Optional<BaseService<BaseDto>> getOperation(String strategy) {
        return Optional.ofNullable(strategyMap.get(strategy));
    }

}
