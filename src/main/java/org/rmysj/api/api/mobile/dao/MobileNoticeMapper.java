package org.rmysj.api.api.mobile.dao;

import org.rmysj.api.api.mobile.domain.MobileNotice;
import org.rmysj.api.api.mobile.domain.MobileNoticeCriteria;
import org.rmysj.api.commons.dao.BaseDao;

import java.util.List;

public interface MobileNoticeMapper extends BaseDao<MobileNotice, MobileNoticeCriteria,String> {

	List<MobileNotice> findMessagesByPage(MobileNotice entity);
}