package com.mystical.cloud.entrance.utils;

import com.mystical.cloud.entrance.bean.PushDataInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Jedis工具类
 *
 * @author wangx147
 */
@Slf4j
@Component
public class JedisUtil {

    ArrayBlockingQueue<PushDataInfo> pushQueue = new ArrayBlockingQueue<>(16);
    /**
     *     模拟redis队列
     */
    public PushDataInfo blPop(String key) throws InterruptedException {

        return pushQueue.take();

    }

}
