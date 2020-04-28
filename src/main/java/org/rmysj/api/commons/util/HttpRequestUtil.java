package org.rmysj.api.commons.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.Map;

public class HttpRequestUtil {

    public static final String readBytes(HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        int size = request.getContentLength();
        InputStream is =request.getInputStream();
        if (size > 0) {
            int readLen = 0;

            int readLengthThisTime = 0;

            byte[] message = new byte[size];

            try {

                while (readLen != size) {

                    readLengthThisTime = is.read(message, readLen, size
                            - readLen);

                    if (readLengthThisTime == -1) {// Should not happen.
                        break;
                    }

                    readLen += readLengthThisTime;
                }
                String res = new String(message);
                return res;
            } catch (IOException e) {
                // Ignore
                // e.printStackTrace();
            }
        }

        return "";
    }

    public static void writeResp (HttpServletResponse response,String res) throws UnsupportedEncodingException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getOutputStream().write(res.getBytes("utf-8"));
        response.flushBuffer();
    }
    
    public static void writeResp (HttpServletResponse response,JSONObject json) throws UnsupportedEncodingException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getOutputStream().write(json.toJSONString().getBytes("utf-8"));
        response.flushBuffer();
    }

    /**
     * post请求
     * @param url
     * @param json
     * @return
     */
    public static JSONObject doPost(String url, JSONObject json){

        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);
        String response = "";
        try {
            StringRequestEntity s = new StringRequestEntity(json.toString(),"application/json","UTF-8");
            post.setRequestEntity(s);
            int res = client.executeMethod(post);
            if(res == HttpStatus.SC_OK){
                InputStream inputStream = post.getResponseBodyAsStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String str = "";
                while ((str = br.readLine()) != null) {
                    stringBuffer.append(str);
                }
                response = stringBuffer.toString();
                if (org.apache.commons.lang3.StringUtils.isNotBlank(response)) {
                    return JSON.parseObject(response);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * 模拟请求
     *
     * @param url       资源地址
     * @param map   参数列表
     * @param encoding  编码
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static JSONObject send(String url, Map<String,String> map, String encoding) throws ParseException, IOException{
        String body = "";
        String response = "";

        //创建httpclient对象
        HttpClient client = new HttpClient();
        //创建post方式请求对象
        PostMethod httpPost = new PostMethod(url);

        //设置参数到请求对象中
        httpPost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        httpPost.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(5, true));
        NameValuePair[] pairs = buildNameValuePairs(map);
        if(pairs != null) {
            httpPost.setRequestBody(pairs);
        }
        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpPost.setRequestHeader(new Header("Content-type","application/x-www-form-urlencoded"));
        httpPost.setRequestHeader(new Header("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)"));
        //执行请求操作，并拿到结果（同步阻塞）
        int res = client.executeMethod(httpPost);
        if(res == HttpStatus.SC_OK){
            InputStream inputStream = httpPost.getResponseBodyAsStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }
            response = stringBuffer.toString();
            if (StringUtils.isNotBlank(response)) {
                return JSON.parseObject(response);
            }
        }
        return null;
    }

    /**
     * 模拟请求
     *
     * @param url       资源地址
     * @param map   参数列表
     * @param encoding  编码
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String getSend(String url, String encoding) throws ParseException, IOException{
        String body = "";
        String response = "";

        //创建httpclient对象
        HttpClient client = new HttpClient();
        //创建post方式请求对象
        GetMethod httpGet = new GetMethod(url);

        //设置参数到请求对象中
        httpGet.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        httpGet.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(5, true));
        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpGet.setRequestHeader(new Header("Content-type","application/x-www-form-urlencoded"));
        httpGet.setRequestHeader(new Header("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)"));
        //执行请求操作，并拿到结果（同步阻塞）
        int res = client.executeMethod(httpGet);
        if(res == HttpStatus.SC_OK){
            InputStream inputStream = httpGet.getResponseBodyAsStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }
            response = stringBuffer.toString();
        }
        return response;
    }


    /**
     * 模拟请求
     *
     * @param url       资源地址
     * @param encoding  编码
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String doDelete(String url, String encoding) throws ParseException, IOException{
        String body = "";
        String response = "";

        //创建httpclient对象
        HttpClient client = new HttpClient();
        //创建post方式请求对象
        DeleteMethod httpDel = new DeleteMethod(url);

        //设置参数到请求对象中
        httpDel.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);
        httpDel.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(5, true));
        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpDel.setRequestHeader(new Header("Content-type","application/x-www-form-urlencoded"));
        httpDel.setRequestHeader(new Header("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)"));
        //执行请求操作，并拿到结果（同步阻塞）
        int res = client.executeMethod(httpDel);
        if(res == HttpStatus.SC_OK){
            InputStream inputStream = httpDel.getResponseBodyAsStream();
            StringBuilder stringBuffer = new StringBuilder();
            byte[] bytes = new byte[4096];
            int size = 0;
            while ((size = inputStream.read(bytes)) > 0) {
                String str = new String(bytes, 0, size, encoding);
                stringBuffer.append(str);
            }
            response = stringBuffer.toString();
            return response;
        }
        return null;
    }


    /**
     * 组装参数,这里用Map,一键一值比较通用,可以当做共用方法
     * @param params
     * @return
     */
    private static NameValuePair[] buildNameValuePairs(Map<String, String> params) {
        if (params != null && params.size() > 0) {
            Object[] keys = params.keySet().toArray();
            NameValuePair[] pairs = new NameValuePair[keys.length];

            for (int i = 0; i < keys.length; i++) {
                String key = (String) keys[i];
                pairs[i] = new NameValuePair(key, params.get(key));
            }
            return pairs;
        } else {
            return null;
        }
    }

        public static String getIpAddress() {
            try {
                Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
                InetAddress ip = null;
                while (allNetInterfaces.hasMoreElements()) {
                    NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                    if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                        continue;
                    } else {
                        Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                        while (addresses.hasMoreElements()) {
                            ip = addresses.nextElement();
                            if (ip != null && ip instanceof Inet4Address) {
                                return ip.getHostAddress();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("IP地址获取失败" + e.toString());
            }
            return "";
        }
    }
