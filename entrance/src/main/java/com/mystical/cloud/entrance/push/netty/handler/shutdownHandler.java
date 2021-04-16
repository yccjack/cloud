package com.mystical.cloud.entrance.push.netty.handler;

import io.netty.channel.EventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Signal;
import sun.misc.SignalHandler;

@Slf4j
public class shutdownHandler implements SignalHandler {
    private EventLoopGroup boss;
    private EventLoopGroup worker;
    public shutdownHandler(EventLoopGroup boss, EventLoopGroup worker) {
        this.boss = boss;
        this.worker = worker;
    }

    @Override
    public void handle(Signal signal) {
        try {
            worker.shutdownGracefully().sync();
            boss.shutdownGracefully().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        NettyConnectDisService.running = false;
        log.info("服务关闭");
    }
}
