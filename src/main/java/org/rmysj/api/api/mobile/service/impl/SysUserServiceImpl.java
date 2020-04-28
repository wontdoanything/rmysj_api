package org.rmysj.api.api.mobile.service.impl;

import java.util.List;

import org.rmysj.api.api.mobile.dao.SysUserMapper;
import org.rmysj.api.api.mobile.domain.SysUser;
import org.rmysj.api.api.mobile.domain.SysUserCriteria;
import org.rmysj.api.commons.dao.BaseDao;
import org.rmysj.api.commons.service.BaseServiceImpl;
import org.rmysj.api.commons.service.redis.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.rmysj.api.api.mobile.service.SysUserService;


@Service(value = "sysUserServiceImpl")
@Transactional
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, SysUserCriteria>
        implements SysUserService {

    @Autowired
    private SysUserMapper sysUserDao;
    
    @Autowired
    private IRedisService redisService;

    @Override
    protected BaseDao<SysUser, SysUserCriteria, String> getDao() {
        return sysUserDao;
    }

	@Override
	public List<String> findAllUserIds() {
		return sysUserDao.findAllUserIds();
	}

    @Override
    public SysUser findSysUserByLoginName(String loginName) {
        SysUserCriteria sysUserCriteria = new SysUserCriteria();
        sysUserCriteria.or().andDelFlagEqualTo("0").andLoginNameEqualTo(loginName);
        List<SysUser> sysUserList = this.search(sysUserCriteria);
        if (sysUserList != null && sysUserList.size() == 1) {
            return sysUserList.get(0);
        }else {
            return null;
        }
    }

}
