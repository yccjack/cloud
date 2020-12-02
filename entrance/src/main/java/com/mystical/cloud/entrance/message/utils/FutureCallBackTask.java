package com.mystical.cloud.entrance.message.utils;

import com.google.common.util.concurrent.FutureCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ListenableFuture回调任务
 *
 * @author MysticalYcc
 */
@Component
@Slf4j
public class FutureCallBackTask implements FutureCallback<Integer> {

    /**
     * 成功的回调
     *
     * @param result
     */
    @Override
    public void onSuccess(Integer result) {
        //执行回调函数
        log.info("发送邮件成功，回调succeed方法!执行数据更新");

        log.info("成功发送的数据:[{}],");
    }

    /**
     * 失败的回调
     *
     * @param t
     */
    @Override
    public void onFailure(Throwable t) {
        log.error("出错了");
    }

}
