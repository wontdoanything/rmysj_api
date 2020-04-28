package org.rmysj.api.api.version.domain;

import org.rmysj.api.commons.domain.AbstractAuditingEntity;

import java.io.Serializable;
import java.util.Date;

public class DefenderAppversion extends AbstractAuditingEntity implements Serializable {

    private String appType;

    private String versionNo;

    private String versionWgtu;

    private String latest;

    private String upgradeType;

    private Date releaseDate;

    private String downloadUrl;

    private Date modifyDate;

    private String remark;

    private static final long serialVersionUID = 1L;

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType == null ? null : appType.trim();
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo == null ? null : versionNo.trim();
    }

    public String getVersionWgtu() {
        return versionWgtu;
    }

    public void setVersionWgtu(String versionWgtu) {
        this.versionWgtu = versionWgtu == null ? null : versionWgtu.trim();
    }

    public String getLatest() {
        return latest;
    }

    public void setLatest(String latest) {
        this.latest = latest == null ? null : latest.trim();
    }

    public String getUpgradeType() {
        return upgradeType;
    }

    public void setUpgradeType(String upgradeType) {
        this.upgradeType = upgradeType == null ? null : upgradeType.trim();
    }


    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl == null ? null : downloadUrl.trim();
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(getId());
        sb.append(", appType=").append(appType);
        sb.append(", versionNo=").append(versionNo);
        sb.append(", versionWgtu=").append(versionWgtu);
        sb.append(", latest=").append(latest);
        sb.append(", upgradeType=").append(upgradeType);
        sb.append(", releaseDate=").append(releaseDate);
        sb.append(", downloadUrl=").append(downloadUrl);
        sb.append(", createDate=").append(getCreateDate());
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", remark=").append(remark);
        sb.append(", createBy=").append(getCreateBy());
        sb.append(", updateDate=").append(getUpdateDate());
        sb.append(", updateBy=").append(getUpdateBy());
        sb.append(", remarks=").append(getRemarks());
        sb.append(", delFlag=").append(getDelFlag());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}