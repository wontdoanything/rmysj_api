package org.rmysj.api.api.mobile.web.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserKeys implements Serializable{
	private String id;
	private String userCode;
	private String priority;
	private String deviceId;
	private String userName;
	
	public UserKeys(String id, String userCode,String userName,String priority,String deviceId) {
		super();
		this.id = id;
		this.userCode = userCode;
		this.priority = priority;
		this.deviceId = deviceId;
		this.setUserName(userName);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
