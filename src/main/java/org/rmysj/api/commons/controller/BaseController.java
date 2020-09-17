package org.rmysj.api.commons.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.rmysj.api.api.mobile.web.dto.UserKeys;
import org.rmysj.api.commons.util.constant.HttpCodeEnum;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

@RestController
public  class BaseController {

	/**
	 * 日志对象
	 */
	//protected org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	protected Logger log = Logger.getLogger(getClass());

	public static String STATUS = "result_code";

	public static String SUCCESS = "success";

	public static String OK = "200";

	public static String CREATED = "201";

	public static String AUTHFAIL = "202";

	public static String WARN = "900";

	public static String MONEY_LESS = "909";

	public static String POINT_LESS = "9098";

	public static String ACCOUNT_NO_ACTIVE = "903";

	public static String DESC = "result_desc";

	public static String NO_STATE="0";

	public static String OK_STATE="1";

	public static String RESULTCODE_OK = "0";

	public static String RESULTCODE_WARN = "1";
	public static final String NOT_FOUND = "404";




	public JSONPObject callbackJSONP(HttpServletRequest request,JSONObject json) {
		String jsoncallback = request.getParameter("jsoncallback");
		if (StringUtils.isNotBlank(jsoncallback)) {
			JSONPObject result = new JSONPObject(jsoncallback,json.toJSONString());
			return result;
		}
		return null;
	}

	public static String getStackTraceAsString(Throwable e) {
		if (e == null){
			return "";
		}
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}

	/**
	 * 获取当前用户的关键信息
	 * @param request
	 * @return
	 * @version 1.0
	 * @author rmysj
	 * @date 2018年12月13日 下午7:17:20
	 */
	protected UserKeys getUserKeysByToken(HttpServletRequest request){
		String token = request.getParameter("token");
		if(StringUtils.isEmpty(token)){
			return null;
		}
		UserKeys uk = (UserKeys)request.getAttribute("userKeys");
		return uk;
	}

	/**
	 * 判断用户合法性
	 */
	public JSONObject mobileUser( String userid){
		JSONObject result = new JSONObject();

		return result;
	}

	/**
	 * 检测当前token是否需要刷新，重置code
	 * @param map
	 * @param request
	 * @param response
	 * @version 1.0
	 * @author rmysj
	 * @date 2018年12月13日 下午7:16:52
	 */
	protected void refreshTokenOper(Map<String,Object> map,HttpServletRequest request, HttpServletResponse response){
		String refreshToken = response.getHeader("refresh_token");
		if(StringUtils.isNotEmpty(refreshToken)){
			//todo 是否需要屏蔽该提示
			map.put(STATUS, HttpCodeEnum.TOKEN_REFRESH.getCode());
			map.put(DESC, HttpCodeEnum.TOKEN_REFRESH.getDesc());
		}
	}

}
