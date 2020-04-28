package org.rmysj.api.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.rmysj.api.api.mobile.web.dto.UserKeys;
import org.rmysj.api.commons.controller.BaseController;
import org.rmysj.api.commons.domain.JwtParseResult;
import org.rmysj.api.commons.service.redis.IRedisService;
import org.rmysj.api.commons.util.HttpRequestUtil;
import org.rmysj.api.commons.util.JSONUtil;
import org.rmysj.api.commons.util.JwtTokenUtil;
import org.rmysj.api.commons.util.URLPermissionUtil;
import org.rmysj.api.commons.util.constant.HttpCodeEnum;
import org.rmysj.api.config.Glob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class SecurityInterceptor extends HandlerInterceptorAdapter {
	
	protected Logger log = Logger.getLogger(getClass());
	
	@Autowired
	private IRedisService redisService;
//	@Autowired
//	private MobileUserService userService;

	@Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
		String servletPath = request.getServletPath();
		String userCode = request.getParameter("userCode");//用来校验token内容与当前用户是否一致
		
		String token = getRequestToken(request);
		if(validateURL(servletPath)){
			JSONObject json = new JSONObject();
			if(StringUtils.isEmpty(token)){
				json.put(BaseController.STATUS, HttpCodeEnum.TOKEN_NULL.getCode());
				json.put(BaseController.DESC, HttpCodeEnum.TOKEN_NULL.getDesc());
				HttpRequestUtil.writeResp(response, json);
				return false;
			}
			
			if(StringUtils.isEmpty(userCode)){
				json.put(BaseController.STATUS, HttpCodeEnum.USER_CODE_NULL.getCode());
				json.put(BaseController.DESC, HttpCodeEnum.USER_CODE_NULL.getDesc());
				HttpRequestUtil.writeResp(response, json);
				return false;
			}
			
			JwtParseResult parseResult = JwtTokenUtil.parseJWT(token);
			if(!parseResult.getCode().equals(HttpCodeEnum.SIGN_PASS.getCode())){
				json.put(BaseController.STATUS, parseResult.getCode());
				json.put(BaseController.DESC, parseResult.getMsg());
				HttpRequestUtil.writeResp(response, json);
				return false;
			}
			if(!parseResult.jwt.getUserCode().equals(userCode) && !parseResult.jwt.getUserName().equals(userCode)){
				json.put(BaseController.STATUS, HttpCodeEnum.UNMATCH_JWT_ERROR.getCode());
				json.put(BaseController.DESC, HttpCodeEnum.UNMATCH_JWT_ERROR.getDesc());
				HttpRequestUtil.writeResp(response, json);
				return false;
			}
			//小于5分钟准备延期操作
			Date expireDate = parseResult.jwt.getExpiration();
			String userId = parseResult.jwt.getId();
			String uCode = parseResult.jwt.getUserCode();
			String deviceId = parseResult.jwt.getDeviceId();
			String userName = parseResult.jwt.getUserName();
			if(expireDate.getTime() - System.currentTimeMillis() < 300000){
				String black_key = JwtTokenUtil.PREIX_BLACK+uCode+"_"+token;
				String backToken = redisService.get(black_key);
				//第一次刷新生成新的token
				if(StringUtils.isEmpty(backToken)){
					//如果客户端用刷新后的refresh_token，并且再次过期，要删除历史黑名单缓存，只保留一份
					Jedis jedis=null;
					try {
						jedis = redisService.getResource();
						Set<String> keySet = jedis.keys(JwtTokenUtil.PREIX_BLACK+uCode+"*");
						Iterator<String> it=keySet.iterator() ;   
						while(it.hasNext()){
						    String key = it.next();   
						    redisService.del(key);
						}
						
						String refreshToken = JwtTokenUtil.createJWT(userId, userName,parseResult.jwt.getPriority(), uCode, deviceId, Glob.getConfig("jwt.expire.mills"));
						redisService.set(black_key, refreshToken,1800);//周期30分钟，不能太长，小于jwt.expire.mills的值（最好两者相等就不会重复生成token）
						response.setHeader("refresh_token", refreshToken);
						response.setHeader("code", HttpCodeEnum.TOKEN_REFRESH.getCode());
						response.setHeader("msg", HttpCodeEnum.TOKEN_REFRESH.getDesc());
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}finally{
						redisService.returnResource(jedis);
					}
				}else{
					//非第一次直接返回第一次刷新后的token
					response.setHeader("refresh_token", backToken);
					response.setHeader("code", HttpCodeEnum.TOKEN_REFRESH.getCode());
					response.setHeader("msg", HttpCodeEnum.TOKEN_REFRESH.getDesc());
				}
			}else{
				//比较当前的token和缓存中的token，如果不一致把当前的token更新入库
				String cacheToken = redisService.get(userId);
				if(!token.equals(cacheToken)){
					redisService.set(userId, token);
					//更新token的逻辑
//					MobileUser user = new MobileUser();
//					user.setId(userId);
//					user.setToken(token);
//					user.setUpdateDate(new Date());
//					userService.update(user);
				}
			}
			request.setAttribute("userKeys", new UserKeys(parseResult.jwt.getId(),parseResult.jwt.getUserCode(),
					parseResult.jwt.getUserName(),parseResult.jwt.getPriority(),parseResult.jwt.getDeviceId()));
		}
		return true;
    }
 
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }
 
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        StringBuilder reqInfo = new StringBuilder();
        String reqUri = request.getRequestURI();
        reqInfo.append(">>>REST response end:").append(reqUri);

        log.info(reqInfo.toString());
    }
    
    private String getRequestToken(HttpServletRequest request) {
    	String token = null;
		Enumeration<String> params = request.getParameterNames();
		
		Map<String, String> paramMap = new HashMap<String, String>();
		while (params.hasMoreElements()) {
			String key = params.nextElement();
			String value = request.getParameter(key).replace(" ", "");
			paramMap.put(key, value);
			
			if(key.equals("token"))
				token = value;
		}
		
		StringBuilder reqInfo = new StringBuilder();
		String reqUri = request.getRequestURI();
        reqInfo.append(">>>REST request entry to :").append(reqUri).append(",")
                .append("parameters:").append(JSONUtil.toJSONString(paramMap));


        log.info(reqInfo.toString());
		return token;
	}
    
    /**
	 * 判断是否需要登录权限
	 * 
	 * @param servletPath
	 * @return
	 */
	private boolean validateURL(String servletPath) {
		boolean state = false;
		for (Map.Entry<String, String> entity : URLPermissionUtil.map
				.entrySet()) {
			if (servletPath.equals(entity.getKey())) {// 需要登录权限的
				state = true;
			}
		}
		return state;
	}
}
