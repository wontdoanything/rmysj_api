package org.rmysj.api.api.verifyCode.service;

import com.alibaba.fastjson.JSONObject;
import org.rmysj.api.commons.domain.Sms;
import org.rmysj.api.commons.util.SmsUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by rmysj on 2018/4/9 上午11:15.
 */
@Service
public class VerifyCodeServiceImpl implements VerifyCodeService{


    private String smsUrl;


    private String smsUserid;


    private String smsAccount;


    private String smsPassword;

    private String smsSignName;




    @Override
    public JSONObject sendSms(String content,String mobile,Date sendTime) {
        JSONObject jsonObject = new JSONObject();
        Sms sms = new Sms();
        sms.setContent(content);
        sms.setAccount(smsAccount);
        sms.setUserid(smsUserid);
        sms.setSignName(smsSignName);
        sms.setPassowrd(smsPassword);
        sms.setMobile(mobile);
        sms.setSendUrl(smsUrl);
        sms.setSendTime(sendTime);
        return SmsUtils.sendSms(sms);
    }
}
