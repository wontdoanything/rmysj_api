package org.rmysj.api.api.mobile.dao;

import java.util.List;

import org.rmysj.api.api.mobile.domain.SysUser;
import org.rmysj.api.api.mobile.domain.SysUserCriteria;
import org.rmysj.api.commons.dao.BaseDao;

public interface SysUserMapper extends BaseDao<SysUser, SysUserCriteria,String> {

	/**
	 * 查看控制台所有的用户ID集合
	 * @return
	 * @version 1.0
	 * @author rmysj
	 * @date 2018年11月23日 下午4:28:36
	 */
	List<String> findAllUserIds();
}