package org.rmysj.api.commons.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.IOException;
import java.util.*;

public class WxUtil {
	/**
     * 随机字符串，不长于32 位
     * 
     * @return
     * @author  modify by 2015年8月2日
     */
    public static String randomStr() {
        String template = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        while (buffer.length() < 32) {
            int index = random.nextInt(36);
            char c = template.charAt(index);
            buffer.append(c);
        }
        return buffer.toString();
    }
 
    /**
     * 签名
     * 
     * @param map 数据
     * @param password 密钥
     * @return
     * @author  modify by 2015年8月2日
     * @throws Exception 
     */
    public static String getSign(Map<String, String> map, String password) throws Exception {
        return getSign(map, password, "");
    }
     
    /**
     * 签名
     * 
     * @param map 数据
     * @param password 密钥
     * @param ignore_keys 忽略的key
     * @return
     * @author  modify by 2015年8月2日
     * @throws Exception 
     */
    public static String getSign(Map<String, String> map, String password, String... ignore_keys) throws Exception {
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
            if (StringUtils.isNotBlank(password)) {
            	result += "key=" + password;
            }
            signResult = WxPaySignUtil.MD5Encode(result,"UTF-8").toUpperCase();
        }
        return signResult;
    }
 
    /**
     * 组装请求数据字符串
     * 
     * @param postData 请求数据
     * @return
     * @author  modify by 2015年8月2日
     */
    public static String toXml(Map<String, String> postData) {
        return HttpsUtils.toXml("xml", postData);
    }
 
    /**
     * 提交http请求，获取响应数据字符串
     * 
     * @param url 请求URL
     * @param xml 请求数据字符串
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @author  by 2015年8月2日
     */
    public static String postXml(String url, String xml) throws Exception {
        Map<String, String> headerInfo = new HashMap<String, String>();
        headerInfo.put("Content-Type", "text/xml");
        headerInfo.put("Connection", "keep-alive");
        headerInfo.put("Accept", "*/*");
        headerInfo.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headerInfo.put("Host", "api.mch.weixin.qq.com");
        headerInfo.put("X-Requested-With", "XMLHttpRequest");
        headerInfo.put("Cache-Control", "max-age=0");
        headerInfo.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
        return HttpsUtils.postXml(url, headerInfo, xml);
    }
 
    /**
     * 提交https请求，获取响应数据字符串
     * 
     * @param url 请求URL
     * @param xml 请求数据字符串
     * @keyStorePath 证书存放路径
     * @keysecret 证书密码
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @author  modify by 2015年8月2日
     */
    public static String postXmlSSL(String url, String xml, String keyStorePath, String keysecret) throws IllegalStateException, IOException, Exception {
        Map<String, String> headerInfo = new HashMap<String, String>();
        headerInfo.put("Content-Type", "text/xml");
        headerInfo.put("Connection", "keep-alive");
        headerInfo.put("Accept", "*/*");
        headerInfo.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headerInfo.put("Host", "api.mch.weixin.qq.com");
        headerInfo.put("X-Requested-With", "XMLHttpRequest");
        headerInfo.put("Cache-Control", "max-age=0");
        headerInfo.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
        return HttpsUtils.postXmlSSL(url, headerInfo, xml, keyStorePath, keysecret);
    }
    
    /**
     * 生成微信要求的xml数据格式
     *
     * @param map
     * @return
     */
    public static String generateXml(Map<String, String> map) {
        StringBuffer xml = new StringBuffer("<xml>");
        Iterator<String> iter = map.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            String value = map.get(key);
            xml.append("<");
            xml.append(key);
            xml.append(">");
            xml.append(value);
            xml.append("</");
            xml.append(key);
            xml.append(">");
        }
        xml.append("</xml>");
        return xml.toString();
    }
 
/**
     * 将微信返回的xml数据转化为map
     * @param xml
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> xmlToMap(String xml){
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Document doc = null;
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            Element rootElt = doc.getRootElement(); // 获取根节点
            List<Element> list = rootElt.elements();
            for (Element element : list) {

                if(element.elements().size() == 0) {
                    map.put(element.getName(), element.getText());
                }else {
                    map.put(element.getName(), xmlToMap(element.asXML()));
                }

            }
        } catch (DocumentException e) {
            //throw new Exception(e);
        }
        return map;
    }
    
    
}
