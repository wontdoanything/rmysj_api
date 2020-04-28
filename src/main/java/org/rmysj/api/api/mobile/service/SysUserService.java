package org.rmysj.api.api.mobile.service;

import org.rmysj.api.api.mobile.domain.SysUser;
import org.rmysj.api.api.mobile.domain.SysUserCriteria;
import org.rmysj.api.commons.service.BaseService;

import java.util.List;

/**
 * SysUserService接口，可以自定义扩展接口
 * @Description:TODO
 * @author rmysj
 * @date 2018年11月21日 下午4:29:33
 * @version 1.0
 *
 */
public interface SysUserService extends BaseService<SysUser, SysUserCriteria> {
	/**
	 * 查看控制台所有的用户ID集合
	 * @return
	 * @version 1.0
	 * @author rmysj
	 * @date 2018年11月23日 下午4:28:36
	 */
	List<String> findAllUserIds();

	SysUser findSysUserByLoginName(String loginName);
}
