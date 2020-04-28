package org.rmysj.api.api.cmsDict.service;

import org.rmysj.api.api.cmsDict.domain.CmsDict;
import org.rmysj.api.api.cmsDict.domain.CmsDictCriteria;
import org.rmysj.api.commons.service.BaseService;

/**
 * Created by rmysj on 2018/3/7 下午2:53.
 */
public interface CmsDictService extends BaseService<CmsDict,CmsDictCriteria> {

    CmsDict findOneByType(String type);
}
