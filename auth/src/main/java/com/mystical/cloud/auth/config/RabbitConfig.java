package com.mystical.cloud.auth.config;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @Description:
 * @author: MysticalYcc
 * @Date: 2021/9/23
 */


@Configuration
public class RabbitConfig {

    /**
     * 定义demoQueue队列
     * @return
     */
    @Bean
    public Queue demoString() {
        return new Queue("demoQueue");
    }

}