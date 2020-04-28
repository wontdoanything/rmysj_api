package org.rmysj.api.commons.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


public class PostOrGet {

	protected final static Logger logger = LoggerFactory.getLogger(PostOrGet.class);
	
	public static final String POST_FORM_SUBMIT_TYPE = "application/x-www-form-urlencoded";

	public static String sendGet(String url) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlName = url;
			URL realUrl = new URL(urlName);

			_FakeX509TrustManager.allowAllSSL();
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");

			conn.setRequestProperty("Cookie", "foo=bar");
			conn.connect();

			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null)
				result = result + "\n" + line;
		} catch (Exception ex) {
			logger.error("数据传输失败!",ex);
			return "数据传输失败";
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException ex) {
				logger.error("数据传输失败!",ex);
				return "数据传输失败";
			}
		}
		return result;
	}

	public static String sendPost(String url, String param, String contentType) {
		if (contentType == null)
			contentType = "text/html; charset=utf-8";
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuffer result = new StringBuffer();
		URLConnection conn = null;
		try {
			URL realUrl = new URL(url);
			_FakeX509TrustManager.allowAllSSL();
			conn = realUrl.openConnection();
			conn.setRequestProperty("content-type", contentType);
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new PrintWriter(conn.getOutputStream());
			out.print(param);
			out.flush();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

			String line = null;
			while ((line = in.readLine()) != null) {
				result.append(line + "\\n");
			}
			if (result != null) {
				result = result.delete(result.length() - 2, result.length());
			}
			line = null;
		} catch (Exception ex) {
			logger.error("数据传输失败!",ex);
			return "数据传输失败";
		} finally {
			conn = null;
			try {
				if (out != null) {
					out.close();
				}
				if (in != null)
					in.close();
			} catch (IOException ex) {
				logger.error("数据传输失败!",ex);
				return "数据传输失败";
			}
		}
		return result.toString();
	}

	/**
	 * 读取输入流数�?
	 * 
	 * @param request
	 * @param charset
	 * @return
	 */
	public static String getInputStream(HttpServletRequest request, String charset) {
		// url参数的方�?
		String jsonStr = showParams(request);
		if (jsonStr != null) {
			return jsonStr;
		} else {
			// 流的方式
			InputStream in = null;
			StringBuffer sb = new StringBuffer();
			try {
				in = request.getInputStream();
				String line = null;
				BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			} catch (IOException e) {
				logger.error("数据传输失败!",e);
			} finally {
				if (null != in) {
					try {
						in.close();
					} catch (IOException e) {
						logger.error("数据传输失败!",e);
					}
				}
			}
			return sb.toString();
		}
	}

	private static String showParams(HttpServletRequest request) {
		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		Map map = new HashMap();
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();

			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					json.put(paramName, paramValue);
				}
			}
		}
		return json.isEmpty() ? null : json.toJSONString();
	}

	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			X509TrustManager xtm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			TrustManager[] tm = { xtm };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new SecureRandom());

			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}

			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();

				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();

			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.parseObject(buffer.toString());
		} catch (ConnectException ce) {
			logger.error("远程地址无响!",ce);
		} catch (Exception e) {
			logger.error("数据传输失败!",e);
		}
		return jsonObject;
	}

	public static class _FakeX509TrustManager implements X509TrustManager {
		private static TrustManager[] trustManagers;
		private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[0];

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public boolean isClientTrusted(X509Certificate[] chain) {
			return true;
		}

		public boolean isServerTrusted(X509Certificate[] chain) {
			return true;
		}

		public X509Certificate[] getAcceptedIssuers() {
			return _AcceptedIssuers;
		}

		public static void allowAllSSL() {
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			SSLContext context = null;
			if (trustManagers == null) {
				trustManagers = new TrustManager[] { new _FakeX509TrustManager() };
			}
			try {
				context = SSLContext.getInstance("TLS");
				context.init(null, trustManagers, new SecureRandom());
			} catch (NoSuchAlgorithmException e) {
				logger.error("生成秘钥失败!",e);
			} catch (KeyManagementException e) {
				logger.error("生成秘钥失败!",e);
			}

			HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
		}
	}

	/**
	 * post提交到微信服务器
	 * 
	 * @param requestXML
	 * @param instream
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws UnrecoverableKeyException
	 * @throws KeyStoreException
	 */
	public static String _WxPost(String url,String requestXML, InputStream instream, String MCH_ID)
			throws NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException,
			UnrecoverableKeyException, KeyStoreException {
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		try {
			keyStore.load(instream, MCH_ID.toCharArray());
		} finally {
			instream.close();
		}
		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, MCH_ID.toCharArray()).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		String result = "";
		try {
			HttpPost httpPost = new HttpPost(url);
			StringEntity reqEntity = new StringEntity(requestXML, "utf-8"); // 如果此处编码不对，可能导致客户端签名跟微信的签名不一�?
			reqEntity.setContentType("application/x-www-form-urlencoded");
			httpPost.setEntity(reqEntity);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(entity.getContent(), "UTF-8"));
					String text;
					while ((text = bufferedReader.readLine()) != null) {
						result += text;
					}
				}
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
		return result;
	}
}