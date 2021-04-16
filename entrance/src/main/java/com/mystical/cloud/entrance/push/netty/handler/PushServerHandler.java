package com.mystical.cloud.entrance.push.netty.handler;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ycc
 * @version 1.0
 * @date 2019/1/22 8:43
 */
@ChannelHandler.Sharable
@Component
@Slf4j
public class PushServerHandler extends SimpleChannelInboundHandler<String> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    /**
     *
     */
    public static Map<Channel, String> msgMap = new ConcurrentHashMap<>(1024);

    @Autowired
    NettyConnectDisService nettyConnectDisService;


    /**
     * 获取客户端发送的消息并向所有关注的客户端推送消息
     *
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String pushMsg) throws Exception {
        log.info("ChatServerHandler->channelRead0：" + pushMsg);
        Channel channel = channelHandlerContext.channel();
        log.info("channelRead0接收到的数据：[{}]", pushMsg);

        try {
            if (pushMsg.contains("PROXY")) {
            //防止Nginx代理，Apache代理产生的PROXY 连接报错
            } else if (pushMsg.contains("ping")) {
                //启用心跳
                log.debug("客户端发送ping");
            } else {


                if (unpackDis(pushMsg, channel)) return;
                Map<String, Object> map = JSON.parseObject(msgMap.get(channel), new TypeReference<Map<String, Object>>() {
                });

            }
        } catch (Exception e) {
            Map<String, Object> map = new HashMap<>(4);
            e.printStackTrace();
            //传输的数据有误；
            map.put("mode", -1);
            map.put("data", "");
            channel.writeAndFlush(JSON.toJSONString(map));
        }
    }

    /**
     * 针对不同类型的客户端发送数据时拆包情况，组装数据包
     * @param pushMsg
     * @param channel
     * @return
     */
    private boolean unpackDis(String pushMsg, Channel channel) {

        return false;
    }


    /**
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Map<String, Object> map = new HashMap<>(4);
        map.put("mode", 0);
        map.put("data", "");
        Channel channel = ctx.channel();
        channel.writeAndFlush(JSON.toJSONString(map));
    }

    /**
     * 掉线
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }


    /**
     * 异常
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        String addr = channel.remoteAddress().toString();
        log.error("ChatServerHandler" + addr + "异常!");
        cause.printStackTrace();
        ctx.close();
        channels.remove(channel);
    }

    /**
     * 新增
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();
        channels.add(channel);
    }

    /**
     * 移除
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.remove(channel);
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        if (evt instanceof IdleStateEvent) {
//            log.info("客户端断开连接，关闭通道");
//            ctx.disconnect();
//        } else {
//            super.userEventTriggered(ctx, evt);
//        }
    }
}
