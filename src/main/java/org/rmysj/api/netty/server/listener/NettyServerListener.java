package org.rmysj.api.netty.server.listener;

import org.rmysj.api.netty.rpc.util.MessageDecoder;
import org.rmysj.api.netty.rpc.util.MessageEncoder;
import org.rmysj.api.netty.server.adapter.TcpServerHandlerAdapter;
import org.rmysj.api.netty.server.config.NettyServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * Netty服务器监听器
 * <p>
 * create by 叶云轩 at 2018/3/3-下午12:21
 * contact by tdg_yyx@foxmail.com
 */
//@Component
public class NettyServerListener {


    /**
     * NettyServerListener 日志控制器
     * Create by 叶云轩 at 2018/3/3 下午12:21
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerListener.class);

    private  final int MAX_FRAME_LENGTH = 1024 * 1024;
    private  final int LENGTH_FIELD_LENGTH = 4;
    private  final int LENGTH_FIELD_OFFSET = 0;
    private  final int LENGTH_ADJUSTMENT = 0;
    private  final int INITIAL_BYTES_TO_STRIP = 0;
    /**
     * 创建bootstrap
     */
    ServerBootstrap serverBootstrap = new ServerBootstrap();
    /**
     * BOSS
     */
    EventLoopGroup boss = new NioEventLoopGroup();
    /**
     * Worker
     */
    EventLoopGroup work = new NioEventLoopGroup();
    /**
     * 通道适配器
     */

    @Resource
    private TcpServerHandlerAdapter tcpServerHandlerAdapter;
    /**
     * NETT服务器配置类
     */
    @Resource
    private NettyServerConfig nettyConfig;

    /**
     * 关闭服务器方法
     */
    @PreDestroy
    public void close() {
        LOGGER.info("关闭服务器....");
        //优雅退出
        boss.shutdownGracefully();
        work.shutdownGracefully();
    }

    /**
     * 开启及服务线程
     */
    @PostConstruct
    public void start() {
        // 从配置文件中(application.yml)获取服务端监听端口号
        int port = nettyConfig.getPort();
        serverBootstrap.group(boss, work);
        serverBootstrap.channel(NioServerSocketChannel.class);
        new Thread(() -> {
            try {
                //设置事件处理
                serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
//                        pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
//                        pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                        //  4

//                        ByteBuf delimiter = Unpooled.copiedBuffer("\u0000\u0000\b\n".getBytes());
//                        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(1999999999,delimiter));
                        pipeline.addLast("decoder", new MessageDecoder(MAX_FRAME_LENGTH,LENGTH_FIELD_LENGTH,LENGTH_FIELD_OFFSET,LENGTH_ADJUSTMENT,INITIAL_BYTES_TO_STRIP,false));
                        pipeline.addLast("encoder", new MessageEncoder());

                        pipeline.addLast(tcpServerHandlerAdapter);
//                        pipeline.addLast(new ObjectCodec());
//                        pipeline.addLast(channelHandlerAdapter);
                    }
                });
                LOGGER.info("netty服务器在[{}]端口启动监听", port);
                ChannelFuture f = serverBootstrap.bind(port).sync();
                f.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
                boss.shutdownGracefully();
                work.shutdownGracefully();
            }
        }).start();
    }
}
