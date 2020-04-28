package org.rmysj.api.api.sysDict.service.impl;

import org.rmysj.api.api.sysDict.dao.SysDictMapper;
import org.rmysj.api.api.sysDict.domain.SysDict;
import org.rmysj.api.api.sysDict.domain.SysDictCriteria;
import org.rmysj.api.api.sysDict.service.SysDictService;
import org.rmysj.api.commons.dao.BaseDao;
import org.rmysj.api.commons.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Title: SysDictServiceImpl
 * @Description: $todo
 * @Author Devil
 * @Date 2017/3/13
 * @Version V1.0.0
 */
@Service
@Transactional
public class SysDictServiceImpl extends BaseServiceImpl<SysDict, SysDictCriteria>
        implements SysDictService {

    @Autowired
    private SysDictMapper sysDictDao;

    @Override
    protected BaseDao<SysDict, SysDictCriteria, String> getDao() {
        return sysDictDao;
    }

    @Override
    public SysDict findOneByType(String type) {
        SysDictCriteria sysDictCriteria = new SysDictCriteria();
        sysDictCriteria.or().andTypeEqualTo(type).andDelFlagEqualTo("0");
        List<SysDict> sysDictList = sysDictDao.selectByExample(sysDictCriteria);
        if(sysDictList.size() > 0)
            return sysDictList.get(0);
        return null;
    }

}
