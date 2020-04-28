package org.rmysj.api.api.sysDict.service;

import org.rmysj.api.api.sysDict.domain.SysDict;
import org.rmysj.api.api.sysDict.domain.SysDictCriteria;
import org.rmysj.api.commons.service.BaseService;

/**
 * @Title: SysDictService
 * @Description: $todo
 * @Author Devil
 * @Date 2017/3/13
 * @Version V1.0.0
 */
public interface SysDictService extends BaseService<SysDict, SysDictCriteria> {

    SysDict findOneByType(String type);

}
