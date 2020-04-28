package org.rmysj.api.api.verifyCode.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.rmysj.api.api.verifyCode.service.VerifyCodeService;
import org.rmysj.api.commons.controller.BaseController;
import org.rmysj.api.commons.service.redis.IRedisService;
import org.rmysj.api.commons.util.SmsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by rmysj on 2018/4/9 上午10:32.
 */
@RestController
@RequestMapping(value = "/api/rmysj")
public class VerifyCodeController extends BaseController {

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Autowired
    private IRedisService redisService;

    /**
     * 5分钟有效
     * @param userMobile
     * @param verifyType
     * @return
     */
    @RequestMapping(value = "/get_verify_code",method = {RequestMethod.POST,RequestMethod.GET},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVerifyCode(String userMobile,
                                           String verifyType
    ) {
        log.debug("REST request to /api/rmysj/get_verify_code : " +
                "{ userMobile:" + userMobile + ",verifyType:" + verifyType + " }");
        Map<String, Object> map = Maps.newHashMap();
        map.put(STATUS, OK);
        map.put(DESC, SUCCESS);
        if (StringUtils.isNoneBlank(userMobile)
                && StringUtils.isNoneBlank(verifyType)) {
            JSONObject authResult = mobileUser(userMobile);
            if(RESULTCODE_OK.equals(authResult.getString(STATUS))) {
                String content = "";
                String randomCode  = "";
                if ("1".equals(verifyType)) {
                    randomCode = SmsUtils.randomCode(6,false);
//                    您的验证码是008046。请在页面中提交验证码完成验证。
                    content = "您的验证码是" + randomCode + "。请在页面中提交验证码完成验证。";
                    redisService.set("defender_verify_code_1_" + userMobile,randomCode,300);
                }
                JSONObject jsonObject = verifyCodeService.sendSms(content,userMobile,null);
                if (jsonObject != null && "0".equals(jsonObject.getString("code"))) {

                    map.put(STATUS, OK);
                    map.put(DESC, SUCCESS);
                }else {
                    map.put(STATUS, WARN);
                    map.put(DESC, jsonObject.getString("desc"));
                }
            }else {
                map.put(STATUS,WARN);
                map.put(DESC,authResult.getString(DESC));
            }
        }else {
            map.put(STATUS,WARN);
            map.put(DESC,"参数错误");
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
