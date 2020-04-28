package org.rmysj.api.commons.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by rmysj on 2017/7/6 下午2:35.
 */
public class ApiSignUtil {

    /**
     * 签名
     *
     * @param map 数据
     * @param ignore_keys 忽略的key
     * @return
     * @author  modify by 2015年8月2日
     * @throws Exception
     */
    public static String getSign(Map<String, String> map, String... ignore_keys) throws Exception {
        String result = "";
        String signResult = "";
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            /*if (StringUtils.isNotEmpty(ignore_key) && ignore_key.equals(entry.getKey())) {
                continue;
            }*/
            if (ignore_keys != null) {
                for (int i = 0; i < ignore_keys.length; i++) {
                    if (ignore_keys[i].equals(entry.getKey())) {
                        continue;
                    }
                }
            }
            if (entry.getValue() != null && !"".equals(entry.getValue())) {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        if (size > 0) {
            String[] arrayToSort = list.toArray(new String[size]);
            Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                sb.append(arrayToSort[i]);
            }
            result = sb.toString();
            signResult = WxPaySignUtil.MD5Encode(result,"UTF-8").toUpperCase();
        }
        return signResult;
    }
}
