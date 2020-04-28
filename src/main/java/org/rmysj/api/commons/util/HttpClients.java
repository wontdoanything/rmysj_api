package org.rmysj.api.commons.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class HttpClients {
	
	/** UTF-8 */
    private static final String UTF_8 = "UTF-8";
    /** 日志记录tag */
    private static final String TAG = "HttpClients";
 
    /** 用户host */
    private static String proxyHost = "";
    /** 用户端口 */
    private static int proxyPort = 80;
    /** 是否使用用户端口 */
    private static boolean useProxy = false;
 
    /** 连接超时 */
    private static final int TIMEOUT_CONNECTION = 60000;
    /** 读取超时 */
    private static final int TIMEOUT_SOCKET = 60000;
    /** 重试3次 */
    private static final int RETRY_TIME = 3;
 
    /**
     * @param url
     * @param requestData
     * @return
     */
    public String doHtmlPost(HttpClient httpClient,HttpPost httpPost )
    {
        String responseBody = null;
 
        int statusCode = -1;
 
        try {
             
            HttpResponse httpResponse = httpClient.execute(httpPost);
            Header lastHeader = httpResponse.getLastHeader("Set-Cookie");
            if(null != lastHeader)
            {
                httpPost.setHeader("cookie", lastHeader.getValue());
            }
            statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                System.out.println("HTTP" + "  " + "HttpMethod failed: " + httpResponse.getStatusLine());
            }
            InputStream is = httpResponse.getEntity().getContent();
            responseBody = getStreamAsString(is, HTTP.UTF_8);
 
        } catch (Exception e) {
            // 发生网络异常
            e.printStackTrace();
        } finally {
//          httpClient.getConnectionManager().shutdown();
//          httpClient = null;
        }
 
        return responseBody;
    }
     
     
    /**
     * 
     * 发起网络请求
     * 
     * @param url
     *            URL
     * @param requestData
     *            requestData
     * @return INPUTSTREAM
     * @throws AppException
     */
    public static String doPost(String url, String requestData) throws Exception {
        String responseBody = null;
        HttpPost httpPost = null;
        HttpClient httpClient = null;
        int statusCode = -1;
        int time = 0;
        do {
            try {
                httpPost = new HttpPost(url);
                httpClient = getHttpClient();
                // 设置HTTP POST请求参数必须用NameValuePair对象
                List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                params.add(new BasicNameValuePair("param", requestData));
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
                // 设置HTTP POST请求参数
                httpPost.setEntity(entity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                statusCode = httpResponse.getStatusLine().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("HTTP" + "  " + "HttpMethod failed: " + httpResponse.getStatusLine());
                }
                InputStream is = httpResponse.getEntity().getContent();
                responseBody = getStreamAsString(is, HTTP.UTF_8);
                break;
            } catch (UnsupportedEncodingException e) {
                time++;
                if (time < RETRY_TIME) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    }
                    continue;
                }
                // 发生致命的异常，可能是协议不对或者返回的内容有问题
                e.printStackTrace();
 
            } catch (ClientProtocolException e) {
                time++;
                if (time < RETRY_TIME) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    }
                    continue;
                }
                // 发生致命的异常，可能是协议不对或者返回的内容有问题
                e.printStackTrace();
            } catch (IOException e) {
                time++;
                if (time < RETRY_TIME) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    }
                    continue;
                }
                // 发生网络异常
                e.printStackTrace();
            } catch (Exception e) {
                time++;
                if (time < RETRY_TIME) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    }
                    continue;
                }
                // 发生网络异常
                e.printStackTrace();
            } finally {
                httpClient.getConnectionManager().shutdown();
                httpClient = null;
            }
        } while (time < RETRY_TIME);
        return responseBody;
    }
 
    /**
     * 
     * 将InputStream 转化为String
     * 
     * @param stream
     *            inputstream
     * @param charset
     *            字符集
     * @return
     * @throws IOException
     */
    private static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset), 8192);
            StringWriter writer = new StringWriter();
 
            char[] chars = new char[8192];
            int count = 0;
            while ((count = reader.read(chars)) > 0) {
                writer.write(chars, 0, count);
            }
 
            return writer.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }
     
    /**
     * 得到httpClient
     * 
     * @return
     */
    public HttpClient getHttpClient1() {
        final HttpParams httpParams = new BasicHttpParams();
 
        if (useProxy) {
            HttpHost proxy = new HttpHost(proxyHost, proxyPort, "http");
            httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        }
 
        HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_CONNECTION);
        HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_SOCKET);
        HttpClientParams.setRedirecting(httpParams, true);
        final String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.14) Gecko/20110218 Firefox/3.6.14";
 
        HttpProtocolParams.setUserAgent(httpParams, userAgent);
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpClientParams.setCookiePolicy(httpParams, CookiePolicy.RFC_2109);
         
        HttpProtocolParams.setUseExpectContinue(httpParams, false);
        HttpClient client = new DefaultHttpClient(httpParams);
 
        return client;
    }
 
    /**
     * 
     * 得到httpClient
     * 
     * @return
     */
    private static HttpClient getHttpClient() {
        final HttpParams httpParams = new BasicHttpParams();
 
        if (useProxy) {
            HttpHost proxy = new HttpHost(proxyHost, proxyPort, "http");
            httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        }
 
        HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_CONNECTION);
        HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_SOCKET);
        HttpClientParams.setRedirecting(httpParams, true);
        final String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.14) Gecko/20110218 Firefox/3.6.14";
 
        HttpProtocolParams.setUserAgent(httpParams, userAgent);
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpClientParams.setCookiePolicy(httpParams, CookiePolicy.BROWSER_COMPATIBILITY);
        HttpProtocolParams.setUseExpectContinue(httpParams, false);
        HttpClient client = new DefaultHttpClient(httpParams);
 
        return client;
    }
 
    /**
     * 打印返回内容
     * @param response
     * @throws ParseException
     * @throws IOException
     */
    public static void showResponse(String str) throws Exception {
    	
        //Gson gson = new Gson();    
        //Map<String, Object> map = (Map<String, Object>) gson.fromJson(str, Object.class);
    	Map<String, Object> map =  (Map<String, Object>) JSON.parseObject(str, Object.class);
        String value = (String) map.get("data");       
        //String decodeValue =  Des3Request.decode(value);
        //System.out.println(decodeValue);
        //logger.debug(decodeValue);
    }
     
    /**
     * 
     * 发起网络请求
     * 
     * @param url
     *            URL
     * @param requestData
     *            requestData
     * @return INPUTSTREAM
     * @throws AppException
     */
    public static String doGet(String url) throws Exception {
        String responseBody = null;
        HttpGet httpGet = null;
        HttpClient httpClient = null;
        int statusCode = -1;
        int time = 0;
        do {
            try {
                httpGet = new HttpGet(url);
                httpClient = getHttpClient();
                HttpResponse httpResponse = httpClient.execute(httpGet);
                statusCode = httpResponse.getStatusLine().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("HTTP" + "  " + "HttpMethod failed: " + httpResponse.getStatusLine());
                }
                InputStream is = httpResponse.getEntity().getContent();
                responseBody = getStreamAsString(is, HTTP.UTF_8);
                break;
            } catch (UnsupportedEncodingException e) {
                time++;
                if (time < RETRY_TIME) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    }
                    continue;
                }
                // 发生致命的异常，可能是协议不对或者返回的内容有问题
                e.printStackTrace();
 
            } catch (ClientProtocolException e) {
                time++;
                if (time < RETRY_TIME) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    }
                    continue;
                }
                // 发生致命的异常，可能是协议不对或者返回的内容有问题
                e.printStackTrace();
            } catch (IOException e) {
                time++;
                if (time < RETRY_TIME) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    }
                    continue;
                }
                // 发生网络异常
                e.printStackTrace();
            } catch (Exception e) {
                time++;
                if (time < RETRY_TIME) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    }
                    continue;
                }
                // 发生网络异常
                e.printStackTrace();
            } finally {
                httpClient.getConnectionManager().shutdown();
                httpClient = null;
            }
        } while (time < RETRY_TIME);
        return responseBody;
    }
}
