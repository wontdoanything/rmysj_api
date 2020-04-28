package org.rmysj.api.api.version.service.impl;

import org.rmysj.api.api.version.dao.DefenderAppversionMapper;
import org.rmysj.api.api.version.domain.DefenderAppversion;
import org.rmysj.api.api.version.domain.DefenderAppversionCriteria;
import org.rmysj.api.api.version.service.DefenderAppversionService;
import org.rmysj.api.commons.dao.BaseDao;
import org.rmysj.api.commons.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by rmysj on 2018/1/18 下午2:58.
 */
@Service
@Transactional(readOnly = true)
public class DefenderAppversionServiceImpl extends BaseServiceImpl<DefenderAppversion,DefenderAppversionCriteria> implements DefenderAppversionService {

    @Autowired
    private DefenderAppversionMapper defenderAppversionDao;

    @Override
    protected BaseDao<DefenderAppversion, DefenderAppversionCriteria, String> getDao() {
        return defenderAppversionDao;
    }
}
