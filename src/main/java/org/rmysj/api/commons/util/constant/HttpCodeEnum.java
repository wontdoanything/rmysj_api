package org.rmysj.api.commons.util.constant;
/**
 * 消息类标识
 * @Description:TODO
 * @author rmysj
 * @date 2016年9月20日 下午6:12:51
 * @version 1.0
 *
 */
public enum HttpCodeEnum {
	TOKEN_REFRESH("801","token即将过期，请根据消息头refresh_token进行重置！"),
	
	QUERY_NULL("910","没有查到符合的记录！"),
	CREATE_FAILURE("911","创建失败！"),
	CREATE_FAILURE_EXISTS("9110","已经存在，不能重复！"),
	UPATE_FAILURE("912","更新失败！"),
	DELETE_FAILURE("913","删除失败！"),
	
	USER_CODE_NULL("998","userCode不能为空！"),
	TOKEN_NULL("999","token不能为空！"),
	SIGN_PASS("1000","token尚未过期，校验成功！"),
	SIGN_ERROR("1001","签名异常！"),
	JWT_FORMAT_ERROR("1002","JWT格式错误异常！"),
	JWT_EXPIRE_ERROR("1003","token过期，刷新token！"),
	UNSUPPORTED_JWT_ERROR("1004","不支持的JWT异常！"),
	UNMATCH_JWT_ERROR("1005","令牌无效，与当前用户身份不符！"),

	UN_ONLINE("901","当前用户不在线"),
	CANTTALK_CHLUSED("902","有人在说话"),
	CANTTALK_TALKOVER("904","会话已结束"),
	FAIL_CHLUSED("903","抢占失败"),
	DISPER_UN_ONLINE("905","当前无调度员在线"),
	DISPER_VV_CACHE_ERROR("906","调度员信息获取失败，无法正常挂断");
	
	
	String code;
	String desc;
	HttpCodeEnum(String code,String desc){
		this.code=code;
		this.desc=desc;
	}
	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static HttpCodeEnum getMessageEnum(String code)
	{
		HttpCodeEnum[]  messageEnums =  HttpCodeEnum.values();
	    for (HttpCodeEnum messageEnum : messageEnums)
        {
	        if(messageEnum.getCode().equalsIgnoreCase(code))
	        {
	            return messageEnum;
	        }
        }
	    return null;
	   
	}
}
