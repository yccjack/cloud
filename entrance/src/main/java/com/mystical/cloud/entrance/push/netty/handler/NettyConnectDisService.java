package com.mystical.cloud.entrance.push.netty.handler;

import com.mystical.cloud.entrance.base.AbstractService;
import com.mystical.cloud.entrance.bean.PushDataInfo;
import com.mystical.cloud.entrance.utils.JedisUtil;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @author: MysticalYcc
 * @Date: 2020/12/28
 */
@Service
@Slf4j
public class NettyConnectDisService {

    public static ChannelGroup channels = PushServerHandler.channels;
    public static volatile boolean running = true;

    public ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();


    private String pushKey="key";

    @Autowired
    private JedisUtil jedisUtil;
    @PostConstruct
    @SuppressWarnings("all")
    public void init() {
        new Thread(() -> {
            while (running) {
                try {
                    //使用redis的阻塞队列功能
                    PushDataInfo pushDataInfo = jedisUtil.blPop(pushKey);
                    //获取目标类的注册类型
                    String strategy = pushDataInfo.getOperation();
                    log.info("获取redis推送数据，类型：[{}]", strategy);
                    //从注册表中获取执行类，执行推送
                    AbstractService.getOperation(strategy).orElseThrow(() -> new IllegalArgumentException("Invalid Operator")).dispose(pushDataInfo);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }, "Netty-Push-2").start();

        //定时任务推送服务。
        executorService.scheduleAtFixedRate(() -> {
            try {
                log.info("定时任务执行，刷新缓存数据");
                if (!running) {
                    log.info("关闭定时任务");
                    executorService.shutdownNow();
                }
                write("定时任务测试消息");
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }, 0, 50, TimeUnit.SECONDS);

    }

    private void write(String msg) {
        channels.forEach((p)->p.writeAndFlush(msg));
    }

}
