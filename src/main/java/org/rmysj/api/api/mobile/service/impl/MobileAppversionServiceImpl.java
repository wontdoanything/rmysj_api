package org.rmysj.api.api.mobile.service.impl;

import org.rmysj.api.api.mobile.dao.MobileAppversionMapper;
import org.rmysj.api.api.mobile.domain.MobileAppversion;
import org.rmysj.api.api.mobile.domain.MobileAppversionCriteria;
import org.rmysj.api.commons.dao.BaseDao;
import org.rmysj.api.commons.service.BaseServiceImpl;
import org.rmysj.api.commons.service.redis.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.rmysj.api.api.mobile.service.MobileAppversionService;


@Service(value = "MobileAppversionServiceImpl")
@Transactional
public class MobileAppversionServiceImpl extends BaseServiceImpl<MobileAppversion, MobileAppversionCriteria>
        implements MobileAppversionService {

    @Autowired
    private MobileAppversionMapper versionDao;
    
    @Autowired
    private IRedisService redisService;

    @Override
    protected BaseDao<MobileAppversion, MobileAppversionCriteria, String> getDao() {
        return versionDao;
    }

}
