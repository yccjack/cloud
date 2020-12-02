package com.mystical.cloud.entrance.message.conf;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

/**
 * 公用的线程池
 *
 * @author MysticalYcc
 */
@Component
public class ListeningExecutors {
    static int count = Runtime.getRuntime().availableProcessors();

    @Bean
    public ListeningExecutorService createListeningExecutorService() {

        // 创建线程池

        return MoreExecutors.
                listeningDecorator(Executors.newFixedThreadPool(count));
    }
}