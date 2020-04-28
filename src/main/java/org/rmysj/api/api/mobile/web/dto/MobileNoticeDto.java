package org.rmysj.api.api.mobile.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.rmysj.api.api.mobile.domain.MobileNotice;
import org.rmysj.api.api.mobile.web.dto.base.BaseDto;

import java.util.Date;

@SuppressWarnings("serial")
public class MobileNoticeDto extends BaseDto<MobileNotice, MobileNoticeDto> {
	private String content;
	private String id;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createDate;
	private Integer signFlag;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getSignFlag() {
		return signFlag;
	}
	public void setSignFlag(Integer signFlag) {
		this.signFlag = signFlag;
	}
}