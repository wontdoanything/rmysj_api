package org.rmysj.api.api.file.domain;

import java.io.Serializable;

public class FileForm implements Serializable {

    private String md5;

    private String uuid;

    private String date;

    private String name;

    private String size;

    private long totalSize;

    private String partMd5;

    private long offset;

    private String userId;

    private String type;

    private String suffix;

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPartMd5() {
        return partMd5;
    }

    public void setPartMd5(String partMd5) {
        this.partMd5 = partMd5;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSuffix() {
        if(suffix != null && !"".equals(suffix.trim() )) {
            return suffix;
        }else {
            return "";
        }
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

}
