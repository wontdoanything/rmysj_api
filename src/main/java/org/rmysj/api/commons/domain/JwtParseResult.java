package org.rmysj.api.commons.domain;

import java.util.Date;

public class JwtParseResult{
	private Boolean pass;
	private String code;
	private String msg;
	public JwtInfo jwt = new JwtInfo();
	
	public class JwtInfo{
		private String id;
		private Date nowMillis;
		private String userCode;
		private String priority;
		private String deviceId;
		private Date expiration;
		private String userName;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public Date getNowMillis() {
			return nowMillis;
		}
		public void setNowMillis(Date nowMillis) {
			this.nowMillis = nowMillis;
		}
		public String getPriority() {
			return priority;
		}
		public void setPriority(String priority) {
			this.priority = priority;
		}
		/**
		 * @return the audience
		 */
		public String getDeviceId() {
			return deviceId;
		}
		/**
		 * @param audience the audience to set
		 */
		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}
		public String getUserCode() {
			return userCode;
		}
		public void setUserCode(String userCode) {
			this.userCode = userCode;
		}
		public Date getExpiration() {
			return expiration;
		}
		public void setExpiration(Date expiration) {
			this.expiration = expiration;
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
	
	public Boolean getPass() {
		return pass;
	}

	public void setPass(Boolean pass) {
		this.pass = pass;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
