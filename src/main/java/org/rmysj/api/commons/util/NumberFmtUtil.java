package org.rmysj.api.commons.util;

import java.math.BigDecimal;

/**
 * Created by rmysj on 16/4/15.
 */
public class NumberFmtUtil {

    /**
     * 清零
     * @param str 原始字符串
     * @return
     */
    public static String trim(String str) {
        if (str.indexOf(".") != -1 && str.charAt(str.length() - 1) == '0') {
            return trim(str.substring(0, str.length() - 1));
        } else {
            return str.charAt(str.length() - 1) == '.' ? str.substring(0, str.length() - 1) : str;
        }
    }

    /**
     * 清零
     * @param bd 原始字符串
     * @return
     */
    public static String trim(BigDecimal bd) {
        String str = bd.toString();
        if (str.indexOf(".") != -1 && str.charAt(str.length() - 1) == '0') {
            return trim(str.substring(0, str.length() - 1));
        } else {
            return str.charAt(str.length() - 1) == '.' ? str.substring(0, str.length() - 1) : str;
        }
    }

    /**
     * string转化Long
     * @param ss
     * @return
     */
    public static Long parseLong(String ss) {
        if (ss.indexOf(".") >= 0) {
            ss = ss.substring(0, ss.indexOf("."));
        }
        return Long.parseLong(ss);
    }
}
