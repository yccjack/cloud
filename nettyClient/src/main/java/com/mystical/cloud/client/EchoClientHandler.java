package com.mystical.cloud.client;

/**
 * @Description:
 * @author: MysticalYcc
 * @Date: 2020/12/29
 */

import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * 客户端处理类
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<String> {
    private EchoClient imConnection = new EchoClient("gschaos.club",7001);
    EchoClient client;
    public EchoClientHandler(EchoClient client){
       this.client = client;
    }
    /**
     * 在到服务器的连接已经建立之后将被调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("{'mode':1,'data':'p8h4Y5f7kd6txdodfExCS2eN7zcUvsaCKKPE5Q2h3FeWNb/rq59LqmhhmEQZ2vInfZAoq5tFrrEWFRxU6b1CEY8oG5Tujfi20QPrpkI5HqI=@@cVmRc6RTkEdLsC0X8OhJI4pf0dqFIXNfI8ZNJYtZY5WhT1E1Uj6Y7so2ofcl8m7NncnG7bxhGlmSiUPg9Dt8Ul5Yi8iteufzlh3Vg3WyDXhsn9SwWvOCs55JWcEoi61Vzjy8ozZAd6a6wx81Q0cmZ0e4hvfw7ZivRtrut41F6M8='}", CharsetUtil.UTF_8));
      //  ctx.writeAndFlush(Unpooled.copiedBuffer("cAmgvVyMwA3n0qSlV3EtwZx5v4KeBxFjepV0F4BgnqCS2Fo31FbX+sVt3cF7N2WX@@KCvCSy3kq0setiUOs6b4jWWFzTrwBRjA7YExDjPK1uqMKbOWhNrGQbT0OGi1viuaiFN5Eb4thSbHL8oWsH+ffs/9OS1xZGgqknXxa3pJ0TauD2rF05w9TsyBlS/DxOhyS491VtC2W8QfT3J8zTLbuK2WEvMykSHAiY9lMNtBJE0=", CharsetUtil.UTF_8));
    }

    /**
     * 当从服务器接收到一个消息时被调用
     *
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        String key ="MIGfMA0GCSqGSIb3";
        System.out.println("Client received: " + msg);
    }

    /**
     * 在处理过程中引发异常时被调用
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        if (evt instanceof IdleStateEvent) {
//            ctx.writeAndFlush("ping").addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
//            System.out.println("心跳检测");
//        } else {
//            super.userEventTriggered(ctx, evt);
//        }
//    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.err.println("掉线了...");
        //使用过程中断线重连
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(() -> imConnection.start(), 1L, TimeUnit.SECONDS);
        super.channelInactive(ctx);

    }

//    public static void main(String[] args) {
//        String code = "5b7b22636c6f7365537461747573223a312c2269734e6f7469667943697479537461747573223a302c22697353686f774c6f636174696f6e537461747573223a302c22697353686f774d6f62696c65537461747573223a302c226e65656453656e64223a747275652c22726f616d43697479436f6465223a223235222c22726f616d436974794e616d65223a22e58d97e4baac222c2274617267657450686f6e65223a223137363031353433323134222c2274797065223a307d5d";
//        byte[] bytes = ByteBufUtil.decodeHexDump(code);
//        System.out.println(new String(bytes));
//    }
}
