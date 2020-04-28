package org.rmysj.api.netty.rpc.util;

import org.rmysj.api.netty.rpc.entity.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

/**
 * Created by rmysj on 2018/3/14 下午3:43.
 */
public class MessageEncoder extends MessageToByteEncoder<Message> {

    private final Charset charset = Charset.forName("utf-8");

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {

        //
        byte[] data = msg.getXml().getBytes(charset);
        out.writeInt(data.length);
        out.writeBytes(data);
    }
}
