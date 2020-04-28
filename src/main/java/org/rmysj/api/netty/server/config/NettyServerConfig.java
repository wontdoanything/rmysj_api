package org.rmysj.api.netty.server.config;

/**
 * Netty服务器配置信息
 * <p>
 * create by rmysj at 2018/3/3-下午12:14
 */
//@Component
//@ConfigurationProperties(prefix = "netty")
public class NettyServerConfig {

    /**
     * 端口
     */
    private int port;
    /**
     * 最大线程数
     */
    private int maxThreads;
    /**
     * 最大数据包长度
     */
    private int maxFrameLength;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public int getMaxFrameLength() {
        return maxFrameLength;
    }

    public void setMaxFrameLength(int maxFrameLength) {
        this.maxFrameLength = maxFrameLength;
    }
}
