package org.rmysj.api.api.sms;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.rmysj.api.api.verifyCode.service.VerifyCodeService;
import org.rmysj.api.commons.controller.BaseController;
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
 * Created by rmysj on 2018/5/22 下午3:57.
 */
@RestController
@RequestMapping(value = "/api/rmysj")
public class SmsController extends BaseController {

    @Autowired
    private VerifyCodeService verifyCodeService;

    /**
     * 下发短信
     * @param userMobile
     * @param verifyType 9 普通下发短信
     * @return
     */
    @RequestMapping(value = "/sendSms",method = {RequestMethod.POST},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendSms(String userMobile,
                                           String verifyType,
                                            String content
    ) {
        log.debug("REST request to /api/rmysj/sendSms : " +
                "{ userMobile:" + userMobile + ",verifyType:" + verifyType + ",content:" + content + " }");
        Map<String, Object> map = Maps.newHashMap();
        map.put(STATUS, OK);
        map.put(DESC, SUCCESS);
        if (StringUtils.isNoneBlank(userMobile)
                && StringUtils.isNoneBlank(verifyType)
                && StringUtils.isNoneBlank(content)) {
                if ("9".equals(verifyType)) {
                    content = content;
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
            map.put(DESC,"参数错误");
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
