package org.rmysj.api.api.mobile.service.impl;

import org.rmysj.api.api.mobile.dao.MobileMsgTplMapper;
import org.rmysj.api.api.mobile.domain.MobileMsgTpl;
import org.rmysj.api.api.mobile.domain.MobileMsgTplCriteria;
import org.rmysj.api.commons.dao.BaseDao;
import org.rmysj.api.commons.service.BaseServiceImpl;
import org.rmysj.api.commons.service.redis.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.rmysj.api.api.mobile.service.MobileMsgTplService;


@Service(value = "mobileMsgTplServiceImpl")
@Transactional
public class MobileMsgTplServiceImpl extends BaseServiceImpl<MobileMsgTpl, MobileMsgTplCriteria>
        implements MobileMsgTplService {

    @Autowired
    private MobileMsgTplMapper mobileMsgTplDao;
    
    @Autowired
    private IRedisService redisService;

    @Override
    protected BaseDao<MobileMsgTpl, MobileMsgTplCriteria, String> getDao() {
        return mobileMsgTplDao;
    }

}
