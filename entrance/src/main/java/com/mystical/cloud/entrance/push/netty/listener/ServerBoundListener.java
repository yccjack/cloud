package com.mystical.cloud.entrance.push.netty.listener;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @author MysticalYcc
 * @date 2020/5/29
 */
@Component
public class ServerBoundListener implements ChannelFutureListener {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
            logger.info("服务器绑定成功,端口号：10001");

        } else {
            Throwable cause = future.cause();
            if (cause == null) {
                logger.error("服务器绑定失败,原因未知");
            } else {
                cause.printStackTrace();
            }
        }
    }
}
