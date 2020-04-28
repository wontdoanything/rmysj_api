package org.rmysj.api.commons.util;

import com.alibaba.fastjson.JSONObject;
import org.rmysj.api.commons.domain.Sms;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Map;

/**
 * Created by rmysj on 2018/4/9 上午10:38.
 */
public class SmsUtils {

    /**
     *
     * @param sms
     * @return
     */
    public static JSONObject sendSms(Sms sms) {
        JSONObject jsonObject = new JSONObject();
        String url = sms.getSendUrl();
        String content = sms.isSign()?("【" + sms.getSignName() + "】" + sms.getContent()):sms.getContent();
        String sendTime = sms.getSendTime() != null?DateFormatUtils.format(sms.getSendTime(),"yyyy-MM-dd HH:mm:ss"):"";
        String extno = StringUtils.isNotBlank(sms.getExtno())?sms.getExtno():"";
        try {
            url += "?action=" + sms.getAction() + "&userid=" + sms.getUserid() + "&account=" + sms.getAccount()
                    + "&password=" + sms.getPassowrd() + "&mobile=" + sms.getMobile()
                    + "&content=" + URLEncoder.encode(content, "utf-8") + "&sendTime=" + sendTime + "&extno=" + extno;
            String result = HttpRequestUtil.getSend(url,"utf-8");
            if (StringUtils.isNotBlank(result)) {
                Map<String, Object> resultMap = WxUtil.xmlToMap(result);
                if ("Success".equals(resultMap.get("returnstatus"))) {
                    jsonObject.put("code",0);
                }else {
                    jsonObject.put("code",1);
                    jsonObject.put("desc",resultMap.get("message"));
                }

            }
        } catch (ParseException e) {
            e.printStackTrace();
            jsonObject.put("code",1);
            jsonObject.put("desc",e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            jsonObject.put("code",1);
            jsonObject.put("desc",e.getMessage());
        }finally {
            return jsonObject;
        }
    }

    public static String randomCode(int length,boolean hasChar){
        String randomcode = "";
        // 用字符数组的方式随机
        String model = "";
        if(hasChar) {
            model = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        }else{
            model = "0123456789";
        }

        char[] m = model.toCharArray();
        for (int j = 0; j < length; j++) {
            char c = m[(int) (Math.random() * model.length())];
            // 保证六位随机数之间没有重复的
            if (randomcode.contains(String.valueOf(c))) {
                j--;
                continue;
            }
            randomcode = randomcode + c;
        }
        return randomcode;
    }
}
