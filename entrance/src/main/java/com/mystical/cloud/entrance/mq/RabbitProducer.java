package com.mystical.cloud.entrance.mq;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: MysticalYcc
 * @Date: 2021/9/23
 */


@Component
@Log4j2
public class RabbitProducer {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendDemoQueue() {
        Date date = new Date();
        String dateString = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
        log.info("[demoQueue] send msg:[{}] ", dateString);
        // 第一个参数为刚刚定义的队列名称
        this.rabbitTemplate.convertAndSend("demoQueue", dateString);
    }
}
