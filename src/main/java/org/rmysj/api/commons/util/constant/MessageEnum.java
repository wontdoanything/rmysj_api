package org.rmysj.api.commons.util.constant;
/**
 * 消息类标识
 * @Description:TODO
 * @author rmysj
 * @date 2016年9月20日 下午6:12:51
 * @version 1.0
 *
 */
public enum MessageEnum {
	UNREAD_STATE("0","未读"),
	READ_STATE("1","已读"),
	NORMAL_TYPE("1","普通消息类型"),
	RE_SEND_TYPE("2","普通重发消息类型"),
	SYS_TYPE("3","系统消息类型"),
	APPOINTED_OVERTIME_TYPE("4","指派超时消息类型"),
	PICKUP_OVERTIME_TYPE("5","取件超时消息类型"),
	MONTH_REPORT_TYPE("6","云柜月使用报告类型");

	String code;
	String desc;
	MessageEnum(String code,String desc){
		this.code=code;
		this.desc=desc;
	}
	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static MessageEnum getMessageEnum(String code)
	{
		MessageEnum[]  messageEnums =  MessageEnum.values();
	    for (MessageEnum messageEnum : messageEnums)
        {
	        if(messageEnum.getCode().equalsIgnoreCase(code))
	        {
	            return messageEnum;
	        }
        }
	    return null;
	   
	}
}
