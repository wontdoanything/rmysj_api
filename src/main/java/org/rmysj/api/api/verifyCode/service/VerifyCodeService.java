package org.rmysj.api.api.verifyCode.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * Created by rmysj on 2018/4/9 上午11:14.
 */
public interface VerifyCodeService {

    JSONObject sendSms(String content,String mobile,Date sendTime);
}
