package com.mystical.cloud.entrance.push.netty;



import com.mystical.cloud.entrance.push.netty.handler.PushServerHandler;
import com.mystical.cloud.entrance.push.netty.handler.shutdownHandler;
import com.mystical.cloud.entrance.push.netty.listener.ServerBoundListener;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import sun.misc.Signal;

import java.util.concurrent.TimeUnit;

/**
 * @author MysticalYcc
 * @date 2020/5/28
 */
@Slf4j
@Component
public class Server implements ApplicationRunner, ApplicationListener<ContextClosedEvent>, ApplicationContextAware {

    private int port = 10001;
    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup worker = new NioEventLoopGroup();

    private void serverStart(ServerBootstrap b) throws InterruptedException {

        log.info("netty服务启动");
        ChannelFuture f = b.bind(port).sync();
        f.addListener(new ServerBoundListener());
        Signal signal = new Signal(getOOSignalType());
        Signal.handle(signal, new shutdownHandler(boss, worker));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> f.channel().close()));
    }

    private String getOOSignalType() {
        return System.getProperties().getProperty("os.name").toLowerCase().startsWith("win") ? "INT" : "TERM";

    }


    private ApplicationContext applicationContext;
    ServerBootstrap b = new ServerBootstrap();
    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            b.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new  ChannelInitializer<SocketChannel>(){
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
//                            SSLEngine engine = ContextSSLFactory.getSslContext().createSSLEngine();
//                            engine.setUseClientMode(false); //设置为服务端模式
//                            engine.setNeedClientAuth(true); //需要验证客户端
                           // pipeline.addFirst("ssl", new SslHandler(engine));   //这个handler需要加到最前面
                            pipeline
                                    //.addLast("framer", new DelimiterBasedFrameDecoder(1024, Delimiters.lineDelimiter()))
                                    .addLast("decoder", new StringDecoder())
                                    .addLast("encoder", new StringEncoder())
                                    .addLast(new IdleStateHandler(0, 0, 10, TimeUnit.SECONDS))
                                    //.addLast(new HeartbeatHandler())
                                    .addLast("handler", applicationContext.getBean(PushServerHandler.class));
                        }
            })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            serverStart(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {

    }
}
