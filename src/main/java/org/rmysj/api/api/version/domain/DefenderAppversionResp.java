package org.rmysj.api.api.version.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class DefenderAppversionResp  implements Serializable {


    private String versionNo;

    private String upgradeType;

    private Date releaseDate;

    private String downloadUrl;

    private String remark;

    private String isUpgrade;

    private static final long serialVersionUID = 1L;

    public DefenderAppversionResp(){
        super();
    }

    public DefenderAppversionResp(DefenderAppversion version,String resourceUrl){
        this.versionNo = version.getVersionNo();
        this.upgradeType = version.getUpgradeType();
        this.releaseDate = version.getReleaseDate();
        this.downloadUrl = resourceUrl + version.getDownloadUrl();
        this.remark = version.getRemark();
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo == null ? null : versionNo.trim();
    }

    public String getUpgradeType() {
        return upgradeType;
    }

    public void setUpgradeType(String upgradeType) {
        this.upgradeType = upgradeType == null ? null : upgradeType.trim();
    }

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIsUpgrade() {
        return isUpgrade;
    }

    public void setIsUpgrade(String isUpgrade) {
        this.isUpgrade = isUpgrade;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", versionNo=").append(versionNo);
        sb.append(", upgradeType=").append(upgradeType);
        sb.append(", releaseDate=").append(releaseDate);
        sb.append(", downloadUrl=").append(downloadUrl);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}