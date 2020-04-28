package org.rmysj.api.commons.util.constant;
/**
 * 版本类标识
 * @Description:TODO
 * @author rmysj
 * @date 2016年9月20日 下午6:12:51
 * @version 1.0
 *
 */
public enum VersionEnum {
	ANDRIOD_TYPE("1","手机安卓"),
	IOS_TYPE("2","手机IOS"),
	CABINET_TYPE("3","柜机安卓");
	
	String code;
	String desc;
	VersionEnum(String code,String desc){
		this.code=code;
		this.desc=desc;
	}
	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static VersionEnum getVersionEnum(String code)
	{
		VersionEnum[]  versionEnums =  VersionEnum.values();
	    for (VersionEnum versionEnum : versionEnums)
        {
	        if(versionEnum.getCode().equalsIgnoreCase(code))
	        {
	            return versionEnum;
	        }
        }
	    return null;
	   
	}
}
