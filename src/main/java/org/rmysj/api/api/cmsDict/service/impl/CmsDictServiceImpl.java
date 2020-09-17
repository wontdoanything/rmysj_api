package org.rmysj.api.api.cmsDict.service.impl;

import org.rmysj.api.api.cmsDict.dao.CmsDictMapper;
import org.rmysj.api.api.cmsDict.domain.CmsDict;
import org.rmysj.api.api.cmsDict.domain.CmsDictCriteria;
import org.rmysj.api.api.cmsDict.service.CmsDictService;
import org.rmysj.api.commons.dao.BaseDao;
import org.rmysj.api.commons.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by rmysj on 2018/3/7 下午2:54.
 */
@Service
@Transactional(readOnly = true)
public class CmsDictServiceImpl extends BaseServiceImpl<CmsDict,CmsDictCriteria> implements CmsDictService {

    @Autowired
    private CmsDictMapper cmsDictDao;

    @Override
    public CmsDict findOneByType(String type) {
        CmsDictCriteria sysDictCriteria = new CmsDictCriteria();
        sysDictCriteria.or().andTypeEqualTo(type).andDelFlagEqualTo("0");
        List<CmsDict> sysDictList = cmsDictDao.selectByExample(sysDictCriteria);
        if(sysDictList.size() > 0)
            return sysDictList.get(0);
        return null;
    }


    @Override
    protected BaseDao<CmsDict, CmsDictCriteria, String> getDao() {
        return cmsDictDao;
    }
}
