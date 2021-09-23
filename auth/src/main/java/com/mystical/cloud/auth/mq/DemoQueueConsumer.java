package com.mystical.cloud.auth.mq;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
/**
 * @Description:
 * @author: MysticalYcc
 * @Date: 2021/9/23
 */



@Component
@RabbitListener(queues = "demoQueue")
public class DemoQueueConsumer {

    /**
     * 消息消费
     * @RabbitHandler 代表此方法为接受到消息后的处理方法
     */
    @RabbitHandler
    public void recieved(String msg) {
        System.out.println("[demoQueue] recieved message: " + msg);
    }

}
