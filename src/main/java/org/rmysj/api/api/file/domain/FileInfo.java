package org.rmysj.api.api.file.domain;

import org.rmysj.api.commons.domain.AbstractAuditingEntity;

import java.io.Serializable;

public class FileInfo extends AbstractAuditingEntity implements Serializable {

    private String name;

    private String userId;

    private String path;

    private String type; //1.录音，2图片，3视频

    private String md5;

    private Long size;

    private String url;

    private String suffix;

    private String status;

    private Long offset;

    private static final long serialVersionUID = 1L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5 == null ? null : md5.trim();
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(getId());
        sb.append(", userId=").append(userId);
        sb.append(", name=").append(name);
        sb.append(", path=").append(path);
        sb.append(", type=").append(type);
        sb.append(", md5=").append(md5);
        sb.append(", size=").append(size);
        sb.append(", url=").append(url);
        sb.append(", createBy=").append(getCreateBy());
        sb.append(", createDate=").append(getCreateDate());
        sb.append(", updateBy=").append(getUpdateBy());
        sb.append(", updateDate=").append(getCreateDate());
        sb.append(", remarks=").append(getRemarks());
        sb.append(", delFlag=").append(getDelFlag());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}