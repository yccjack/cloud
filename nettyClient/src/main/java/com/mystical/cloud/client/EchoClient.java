package com.mystical.cloud.client;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.ScheduledFuture;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;


/**
 * 客户端
 * 1.为初始化客户端，创建一个Bootstrap实例
 * 2.为进行事件处理分配了一个NioEventLoopGroup实例，其中事件处理包括创建新的连接以及处理入站和出站数据；
 * 3.当连接被建立时，一个EchoClientHandler实例会被安装到（该Channel的一个ChannelPipeline中；
 * 4.在一切都设置完成后，调用Bootstrap.connect()方法连接到远程节点。
 *
 *  * @author: MysticalYcc
 */
public class EchoClient {

    private final String host;
    private final int port;
    private Channel channel;
    private Bootstrap bootstrap;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    /**
     * 运行流程：
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        new EchoClient("localhost", 10001).start();
    }

    public void start()  {
        /**
         * Netty用于接收客户端请求的线程池职责如下。
         * （1）接收客户端TCP连接，初始化Channel参数；
         * （2）将链路状态变更事件通知给ChannelPipeline
         */
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
//                            SSLEngine engine = ContextSSLFactory.getSslContext().createSSLEngine();
//                            engine.setUseClientMode(true); //设置为服务端模式
//                            engine.setNeedClientAuth(true); //需要验证客户端
                            //pipeline.addFirst("ssl", new SslHandler(engine));   //这个handler需要加到最前面
                            pipeline
                                    //.addLast("framer", new DelimiterBasedFrameDecoder(1024, Delimiters.lineDelimiter()))
                                    .addLast("decoder", new StringDecoder())
                                    .addLast("encoder", new StringEncoder())

                                      .addLast(new IdleStateHandler(0, 0, 10, TimeUnit.SECONDS))
                                    //.addLast(new HeartbeatHandler())
                                    .addLast("handler", new EchoClientHandler(EchoClient.this));

                        }
                    });
            //绑定端口
            ChannelFuture f = bootstrap.connect().sync();
            f.addListener(new ConnectionListener());
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }
    protected void doConnect() {
        if (channel != null && channel.isActive()) {
            return;
        }

        ChannelFuture future = bootstrap.connect(host, port);

        future.addListener((ChannelFutureListener) futureListener -> {
            if (futureListener.isSuccess()) {
                channel = futureListener.channel();
                System.out.println("Connect to server successfully!");
                Thread.sleep(2000);
            } else {
                System.out.println("Failed to connect to server, try connect after 10s");

                ScheduledFuture<?> schedule = futureListener.channel().eventLoop().schedule(this::doConnect, 10, TimeUnit.SECONDS);

            }
        });
    }

}