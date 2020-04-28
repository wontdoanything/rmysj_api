package org.rmysj.api.netty.server.adapter;

import org.rmysj.api.api.cmsDict.service.CmsDictService;
import org.rmysj.api.commons.service.redis.IRedisService;
import org.rmysj.api.config.Glob;
import org.rmysj.api.netty.rpc.entity.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by rmysj on 2018/3/8 下午5:50.
 */
//@Component
//@ChannelHandler.Sharable
public class TcpServerHandlerAdapter extends SimpleChannelInboundHandler<Message> {



    private static String cache_perfix = "rmysj_QSWarnNotify_";

    @Autowired
    private IRedisService iRedisService;


    @Autowired
    private CmsDictService cmsDictService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TcpServerHandlerAdapter.class);

//    @Override
//    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object s) throws Exception {
//        LOGGER.info("客户端传入 :{}",
//                s);
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message o) throws Exception {
            if (o != null && StringUtils.isNotBlank(o.getXml()) && o.getXml().indexOf("alert_info") >= 0) {
                String notifyXmlString = o.getXml();
//                String notifyXmlString = Glob.testQSxml;
               //todo 处理你的逻辑
            }
        }


    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public String getIpAddrByMac(String qsMac){
       return Glob.getConfig(qsMac);
    }
}
