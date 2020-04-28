package org.rmysj.api.commons.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractAuditingEntity<T> extends AbstractEntity<T> {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;




	private Integer pageSize= new Integer(0);
	private Integer pageNo = new Integer(10);
	private String orderBy;


	@JsonIgnore
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@JsonIgnore
	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	@JsonIgnore
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}
