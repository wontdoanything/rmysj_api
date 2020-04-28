package org.rmysj.api.api.mobile.web.dto;

import org.rmysj.api.api.mobile.domain.SysOffice;
import org.rmysj.api.api.mobile.web.dto.base.BaseDto;

import java.util.List;

@SuppressWarnings("serial")
public class TreeOfficeDto extends BaseDto<SysOffice, TreeOfficeDto> {
	private String id;
    private String parentId;
    private String code;
    private String name;
    private String type;
    private String areaId;
    private List<TreeOfficeDto> officeList;
	
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public List<TreeOfficeDto> getOfficeList() {
		return officeList;
	}
	public void setOfficeList(List<TreeOfficeDto> officeList) {
		this.officeList = officeList;
	}
    
}