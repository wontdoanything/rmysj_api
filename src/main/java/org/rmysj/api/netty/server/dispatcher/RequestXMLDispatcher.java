package org.rmysj.api.netty.server.dispatcher;

import org.rmysj.api.netty.rpc.entity.NullWritable;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 请求分排器
 * <p>
 * create by 叶云轩 at 2018/3/3-下午1:31
 * contact by tdg_yyx@foxmail.com
 */
//@Component
public class RequestXMLDispatcher implements ApplicationContextAware {
    private ExecutorService executorService = Executors.newFixedThreadPool(65535);
    /**
     * Spring上下文
     */
    private ApplicationContext app;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.app = applicationContext;
    }

    /**
     * 发送
     *
     * @param ctx
     * @param invokeMeta
     */
    public void rmysjer(final ChannelHandlerContext ctx, final String invokeMeta) {
        executorService.submit(() -> {
            ChannelFuture f = null;
            try {

                if (invokeMeta == null) {
                    f = ctx.writeAndFlush(NullWritable.nullWritable());
                } else {
                    String obj = invokeMeta + "的返回值";
                    f = ctx.writeAndFlush(obj);
                }
                f.addListener(ChannelFutureListener.CLOSE);
            } catch (Exception e) {
                f = ctx.writeAndFlush(e.getMessage());
            } finally {
                f.addListener(ChannelFutureListener.CLOSE);
            }
        });
    }

}
