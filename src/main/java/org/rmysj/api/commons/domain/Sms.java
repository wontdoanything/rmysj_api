package org.rmysj.api.commons.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by rmysj on 2018/4/9 上午10:39.
 */
public class Sms implements Serializable {

    private String mobile; //待发送手机号，多个用逗号隔开 半角逗号,

    private boolean isSign = true; //是否签名

    private String signName;

    private String content;

    private String userid; //企业ID

    private String account; //发送用户帐号

    private String passowrd; //发送帐号密码

    private Date sendTime; //发送时间

    private String action = "send"; // 发送任务命令 设置为固定的:send

    private  String extno; // 扩展子号

    private String sendUrl;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isSign() {
        return isSign;
    }

    public void setSign(boolean sign) {
        isSign = sign;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassowrd() {
        return passowrd;
    }

    public void setPassowrd(String passowrd) {
        this.passowrd = passowrd;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getExtno() {
        return extno;
    }

    public void setExtno(String extno) {
        this.extno = extno;
    }

    public String getSendUrl() {
        return sendUrl;
    }

    public void setSendUrl(String sendUrl) {
        this.sendUrl = sendUrl;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }
}
