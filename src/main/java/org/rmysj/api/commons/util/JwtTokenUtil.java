package org.rmysj.api.commons.util;

import org.rmysj.api.commons.domain.JwtParseResult;
import org.rmysj.api.commons.util.constant.HttpCodeEnum;
import org.rmysj.api.config.Glob;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * 如果需要生命周期管理，参考拦截器逻辑（配合缓存黑名单机制）
 * @Description:TODO
 * @author
 * @date 2018年11月28日 下午3:26:10
 * @version 1.0
 *
 */
public class JwtTokenUtil {
	public static String APP_KEY = "^^___^^J!z@_^&*()P-?mn$#__^!32#^^___^^J!z@_()P-?mn$#DfEGc^%!+^-^.^__^!32#^z@_^&*()P-?mn$#Df^___^^J!z@_^&*()P-?mn$#DfEGc^%!+^-^.^__^!32#^^_ CDEFGAB";
//	public static long JWT_EXPIRE_Mills = 1800000;//jwt默认30分钟生效
//	public static long JWT_EXPIRE_Mills = 36000000;//测试10个小时
	public static String PREIX_BLACK = "back_list_";
	/**
	 *  JWT创建Token
	 * @param ttlMillis
	 * @return
	 * @version 1.0
	 * @author
	 * @date 2018年11月20日 下午4:27:35
	 * Payload：{  
	 * 	  "iss": "Online JWT Builder", 该JWT的签发者，是否使用是可选的
		  "iat": 1416797419,  在什么时候签发的(UNIX时间)，是否使用是可选的
		  "exp": 1448333419,  什么时候过期，这里是一个Unix时间戳，是否使用是可选的
		  "aud": "www.example.com", 接收该JWT的一方，是否使用是可选的
		  "sub": "jrocket@example.com", 该JWT所面向的用户，是否使用是可选的
		  "GivenName": "Johnny", 
		  "Surname": "Rocket", 
		  "Email": "jrocket@example.com", 
		  "Role": [ "Manager", "Project Administrator" ] 
		}
	 */
	public static String createJWT(String userId,String userName, String priority, String userCode,String deviceId,String ttlMillis) throws Exception {
		long JWT_EXPIRE_Mills = Long.parseLong( Glob.getConfig("jwt.expire.mills"));
		//The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		 
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		 
		//方法一
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(APP_KEY);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		//Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().claim("userName", userName)
										.setId(userId)
		                                .setIssuedAt(now)
		                                .setSubject(userCode)
		                                .setIssuer(priority)
		                                .setAudience(deviceId)
		                                .signWith(signingKey,signatureAlgorithm);
		//if it has been specified, let's add the expiration
	    long expMillis = StringUtils.isEmpty(ttlMillis) ? nowMillis+JWT_EXPIRE_Mills : nowMillis+Long.valueOf(ttlMillis);
	    Date exp = new Date(expMillis);
	    builder.setExpiration(exp);
		 
		//Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
	}
	
	/**
	 * 验证jwt有效性
	 * @param jwt
	 * @version 1.0
	 * @author rmysj
	 * @date 2018年11月20日 下午4:49:53
	 */
	public static JwtParseResult parseJWT(String jwt) {
		JwtParseResult res = new JwtParseResult();
	 	try {
	 		 //This line will throw an exception if it is not a signed JWS (as expected)
		    Claims claims = Jwts.parser()         
		       .setSigningKey(DatatypeConverter.parseBase64Binary(APP_KEY))
		       .parseClaimsJws(jwt).getBody();
		    res.setPass(true);
		    res.setCode(HttpCodeEnum.SIGN_PASS.getCode());
		    res.setMsg(HttpCodeEnum.SIGN_PASS.getDesc());
		    res.jwt.setId(claims.getId());
		    res.jwt.setNowMillis(claims.getIssuedAt());
		    res.jwt.setPriority(claims.getIssuer());
		    res.jwt.setUserCode(claims.getSubject());
		    res.jwt.setExpiration(claims.getExpiration());
		    res.jwt.setUserName(claims.get("userName").toString());
		    res.jwt.setDeviceId(claims.getAudience());
		} catch (SignatureException e) {
	 		e.printStackTrace();
			res.setCode(HttpCodeEnum.SIGN_ERROR.getCode());
		    res.setMsg(HttpCodeEnum.SIGN_ERROR.getDesc());
		} catch (MalformedJwtException e) {
			res.setCode(HttpCodeEnum.JWT_FORMAT_ERROR.getCode());
		    res.setMsg(HttpCodeEnum.JWT_FORMAT_ERROR.getDesc());
		} catch (ExpiredJwtException e) {
			res.setCode(HttpCodeEnum.JWT_EXPIRE_ERROR.getCode());
		    res.setMsg(HttpCodeEnum.JWT_EXPIRE_ERROR.getDesc());
		} catch (UnsupportedJwtException e) {
			res.setCode(HttpCodeEnum.UNSUPPORTED_JWT_ERROR.getCode());
		    res.setMsg(HttpCodeEnum.UNSUPPORTED_JWT_ERROR.getDesc());
		}
	 	return res;
	}
	
	public static void main(String[] args) {
//		String jwt = createJWT("12345","jack","api_admin","jack","22222","1800000");
//		System.out.println(jwt);
//		JwtParseResult res = parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6Inh5ZiIsImp0aSI6IjEyMzQ1IiwiaWF0IjoxNTUzNjk1NTE0LCJzdWIiOiJqYWNrIiwiaXNzIjoiYXBpX2FkbWluIiwiYXVkIjoiMjIyMjIiLCJleHAiOjE1NTM2OTczMTR9.r_Aa90teRcsQdWn1HkRnJhFvcSCNgbxdzi98szsvdPQ");
//		JwtParseResult res = parseJWT(jwt);
//		String str = JSONUtil.toJSONNoFeatures(res);
//		System.out.println(str);
	}
}
