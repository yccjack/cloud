package com.mystical.cloud.entrance.base;

import com.mystical.cloud.entrance.bean.BaseDto;
import com.mystical.cloud.entrance.emun.CommonEnum;
import com.mystical.cloud.entrance.exception.EventBaseException;
import jdk.internal.reflect.CallerSensitive;
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

    /**
     * 强制要求实现类构造方法调用 super(strategy)，注册实现类的自定义类型；
     * 如果实现类不覆盖属性 strategy {@link AbstractService#strategy},则注册为ABS_BUS方案。
     *
     * @param strategy
     */
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

            String format = String.format("未设置此方法：%s,请检查是否输错，或者联系管理员设置%s方法", operation, operation);
            log.error("{}",format,e);
            throw new EventBaseException(CommonEnum.METHOD_LOST.getResultCode(), format);
        } catch (InvocationTargetException e) {
            log.error("",e);
            Throwable cause = e.getCause();
            //获取反射调用方法抛出的自定义异常
            if (cause instanceof EventBaseException) {
                EventBaseException ex = (EventBaseException) cause;
                throw new EventBaseException(ex.getErrorCode(), ex.getMessage());
            }
        }
        return (String) invoke;
    }

    /**
     * 公共创建事件方法
     *
     * @param baseDto {@link BaseDto}
     * @return result
     * @throws NoSuchMethodException     NoSuchMethodException {@link NoSuchMethodException}
     * @throws IllegalAccessException    IllegalAccessException{@link IllegalAccessException}
     * @throws InvocationTargetException InvocationTargetException {@link InvocationTargetException}
     */
    public String create(BaseDto baseDto) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String event = baseDto.getEvent();
        String data = baseDto.getData();
        Object object = null;
        //获取需要执行的创建事件。
        object = getObject(data, event);
        return (String) object;
    }

    /**
     * 获取具体的执行方案
     *
     * @param data
     * @param operation 方案
     * @param <T>
     * @return
     * @throws NoSuchMethodException     NoSuchMethodException {@link NoSuchMethodException}
     * @throws IllegalAccessException    IllegalAccessException{@link IllegalAccessException}
     * @throws InvocationTargetException InvocationTargetException {@link InvocationTargetException}
     */
    protected <T> Object getObject(T data, String operation) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object invoke;
        if (StringUtils.isEmpty(operation)) {
            throw new EventBaseException(CommonEnum.METHOD_LOST.getResultCode(), "无法找到对应的执行事件，请检查请求路劲和请求体参数");
        }
        Method method = this.getClass().getMethod(operation, data.getClass());
        method.setAccessible(true);
        invoke = method.invoke(this, data);
        log.debug("{}",invoke);

        return invoke;
    }

    /**
     * 将实现类注册到注册表中，如果存在则忽略，即无法覆盖已有注册类。
     *
     * @param strategy {@link AbstractService#strategy}
     */
    protected void addBean(String strategy) {
        strategyMap.putIfAbsent(strategy, this);
    }

    /**
     * 获取方案
     *
     * @param strategy {@link AbstractService#strategy}
     * @return 方案具体实现类 extends AbstractService {@link AbstractService}
     */
    public static Optional<BaseService<BaseDto>> getOperation(String strategy) {
        return Optional.ofNullable(strategyMap.get(strategy));
    }

    /**
     * 在具有指定参数的指定对象上调用此{@code Method} 对象表示的基础方法。
     * 各个参数将自动拆包以匹配原始形式参数，并且
     * 原始和引用参数都必须根据需要进行方法调用转换。
     * 如果基础方法是静态的，则忽略指定的{@code obj} 参数。它可以为空。
     * 如果基础方法所需的形式参数数量为0，则提供的{@code args}数组的长度可以为0或为null。
     * 如果基础方法是实例方法，则使用动态方法查找来调用它，如Java语言规范第二版15.12.4.4中所述；特别是，将基于目标对象的运行时类型进行覆盖。
     * 如果基础方法是静态的，则声明该方法的类将被初始化（如果尚未初始化）。
     * 如果该方法正常完成，则它返回的值将返回给invoke的调用者；
     * 如果该值具有原始类型，则首先将其适当包装在一个对象中。但是，如果该值具有原始类型的数组的类型，则该数组的元素不包装在对象中；
     * 换句话说，将返回原始类型的数组。
     * 如果基础方法的返回类型为void，则调用返回null。
     * @exception IllegalAccessException 如果此{@code Method}对象正在强制执行Java语言访问控制，并且基础方法是不可访问的。
     * @exception IllegalArgumentException，如果该方法是实例方法，
     *  并且指定的对象参数不是类或接口的实例声明基础方法（或子类或其实现）；
     *  如果实际和形式参数的数量不同；如果对原始参数的拆包转换失败；或者，如果在可能的拆包之后，
     *  则无法通过方法调用转换将参数值转换为相应的形式参数类型。
     * @exception InvocationTargetException 如果基础方法引发异常。
     * @exception NullPointerException 在调用invoke方法是以实例对象的形式时，obj为null则抛出
     *
     * @Exception ExceptionInInitializerError。 方法初始化失败
     * @param args
     */
    @CallerSensitive
    public Object invoke(Object obj, Object... args)
            throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        return null;
    }

}
