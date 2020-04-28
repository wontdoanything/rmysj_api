package org.rmysj.api.netty.rpc.util;

import org.rmysj.api.config.Glob;
import org.rmysj.api.netty.rpc.entity.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Created by rmysj on 2018/3/14 下午3:43.
 */
public class MessageDecoder extends LengthFieldBasedFrameDecoder {

    private  final int MAX_FRAME_LENGTH = 1024 * 1024;
    //32bit网络字节序 默认为4
    private  final int LENGTH_FIELD_LENGTH = 4;
    private  final int LENGTH_FIELD_OFFSET = 0;
    private  final int LENGTH_ADJUSTMENT = 0;
    private  final int INITIAL_BYTES_TO_STRIP = 0;

    private Logger logger = LoggerFactory.getLogger(getClass());

    //头部信息的大小应该是 byte+byte+int = 1+1+8+4 = 14
    private static final int HEADER_SIZE = 4;
    private int length;
    private String msgBody;

    public MessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, false);
    }


    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
//        String ip = getIpAddrByCtx(ctx);
        if (in == null) {
            return null;
        }

        if (in.readableBytes() <= HEADER_SIZE) {
            return null;
        }

        in.markReaderIndex();

        int dataLength = in.readInt();

        // FIXME 如果dataLength过大，可能导致问题
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return null;
        }

        byte[] data = new byte[dataLength];
        in.readBytes(data);

        String body = new String(data, "UTF-8");
        Message msg = new Message(dataLength, body);
        return msg;
    }

    public String getIpAddrByCtx(ChannelHandlerContext channelHandlerContext){
        InetSocketAddress insocket = (InetSocketAddress) channelHandlerContext.channel().remoteAddress();
        String clientIP = insocket.getAddress().getHostAddress();
        return clientIP;
    }

    public String getIpAddrByMac(String qsMac){
        return Glob.getConfig(qsMac);
    }
}
