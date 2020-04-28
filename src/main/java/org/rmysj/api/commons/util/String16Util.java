package org.rmysj.api.commons.util;

import java.io.UnsupportedEncodingException;

/**
 * Created by rmysj on 2017/7/31 下午5:22.
 */
public class String16Util {

    public static String convertUTF8ToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }

        try {
            s = s.toUpperCase();

            int total = s.length() / 2;
            int pos = 0;

            byte[] buffer = new byte[total];
            for (int i = 0; i < total; i++) {

                int start = i * 2;

                buffer[i] = (byte) Integer.parseInt(
                        s.substring(start, start + 2), 16);
                pos++;
            }

            return new String(buffer, 0, pos, "gbk");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名.
     *
     * @param s 原串
     * @return
     */
    public static String convertStringToUTF8(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        byte[] buffer = new byte[sb.length()/2];
        try {
            char c;
            for (int i = 0; i < s.length(); i++) {
                c = s.charAt(i);
//                if (c >= 0 && c <= 255) {
//                    sb.append(c);
//                } else {
                    byte[] b;
//                    if (" ".equals(c)) {
//
//                    }else {
//
//                    }else {
//
//                    }
                    b = Character.toString(c).getBytes("gbk");

                    for (int j = 0; j < b.length; j++) {
                        int k = b[j];
                        if (k < 0)
                            k += 256;
                        sb.append(Integer.toHexString(k).toUpperCase());
                        // sb.append("%" +Integer.toHexString(k).toUpperCase());
                    }
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return sb.toString();
    }

    public static byte[]  format16 (String inputStr){
        byte[] result = new byte[inputStr.length() / 2];
        for (int i = 0; i < inputStr.length() / 2; ++i)
            result[i] = (byte)(Integer.parseInt(inputStr.substring(i * 2, i * 2 +2), 16) & 0xff);
        return result;
    }

    public static void main(String[] args) {
//
        System.out.println(String16Util.convertStringToUTF8("安监局"));
        System.out.println(String16Util.convertStringToUTF8("A103"));

//        System.out.println(format16(String16Util.convertStringToUTF8("A001")));
//        System.out.println(String16Util.convertUTF8ToString("B0B2BCE0BED6"));

    }
}
