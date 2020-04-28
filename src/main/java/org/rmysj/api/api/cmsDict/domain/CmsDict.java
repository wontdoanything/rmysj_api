package org.rmysj.api.api.cmsDict.domain;

import org.rmysj.api.commons.domain.AbstractAuditingEntity;

import java.io.Serializable;

public class CmsDict extends AbstractAuditingEntity implements Serializable {

    private String value;

    private String label;

    private String type;

    private String description;

    private Long sort;

    private String parentId;

    private static final long serialVersionUID = 1L;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(getId());
        sb.append(", value=").append(value);
        sb.append(", label=").append(label);
        sb.append(", type=").append(type);
        sb.append(", description=").append(description);
        sb.append(", sort=").append(sort);
        sb.append(", parentId=").append(parentId);
        sb.append(", createBy=").append(getCreateBy());
        sb.append(", createDate=").append(getCreateDate());
        sb.append(", updateBy=").append(getUpdateBy());
        sb.append(", updateDate=").append(getUpdateDate());
        sb.append(", remarks=").append(getRemarks());
        sb.append(", delFlag=").append(getDelFlag());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}