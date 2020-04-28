package org.rmysj.api.api.mobile.web.dto;

import org.rmysj.api.api.mobile.domain.MobileAppversion;
import org.rmysj.api.api.mobile.web.dto.base.BaseDto;

@SuppressWarnings("serial")
public class AppVersionDto extends BaseDto<MobileAppversion, AppVersionDto>{
	private String versionNo;
	private String upgradeType;
	private String downloadUrl;
	private String remark;
	private String isUpgrade;
	private long size;
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getUpgradeType() {
		return upgradeType;
	}
	public void setUpgradeType(String upgradeType) {
		this.upgradeType = upgradeType;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsUpgrade() {
		return isUpgrade;
	}
	public void setIsUpgrade(String isUpgrade) {
		this.isUpgrade = isUpgrade;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
}
