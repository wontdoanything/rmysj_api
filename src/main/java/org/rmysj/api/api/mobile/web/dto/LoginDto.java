package org.rmysj.api.api.mobile.web.dto;

import java.util.List;

@SuppressWarnings("serial")
public class LoginDto  {
	private String token;
    private String id;
    private String companyId;
    private String officeName;
    private String userName;
    private String userCode;
    private String deviceId;
    private Long priority;
    private Integer state;
    private String isTalk;
    private String userMobile;
    private List<String> teamIdList;
    
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public Long getPriority() {
		return priority;
	}
	public void setPriority(Long priority) {
		this.priority = priority;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getIsTalk() {
		return isTalk;
	}
	public void setIsTalk(String isTalk) {
		this.isTalk = isTalk;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public List<String> getTeamIdList() {
		return teamIdList;
	}
	public void setTeamIdList(List<String> teamIdList) {
		this.teamIdList = teamIdList;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
}