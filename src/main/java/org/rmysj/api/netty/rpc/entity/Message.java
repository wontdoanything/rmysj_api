package org.rmysj.api.netty.rpc.entity;

import java.nio.charset.Charset;

/**
 * Created by rmysj on 2018/3/14 下午3:40.
 */
public class Message {

    private final Charset charset = Charset.forName("utf-8");

    private int length;
    private String xml;

    public Message(){

    }

    public Message(int length, byte[] data) {
        this.length = length;
        this.xml = new String(data, charset);
    }

    public Message(int length,String body) {
        this.length = length;
        this.xml = body;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }
}
