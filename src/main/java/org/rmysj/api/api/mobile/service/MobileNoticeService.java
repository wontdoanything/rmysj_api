package org.rmysj.api.api.mobile.service;

import org.rmysj.api.api.mobile.domain.MobileNotice;
import org.rmysj.api.api.mobile.domain.MobileNoticeCriteria;
import org.rmysj.api.commons.service.BaseService;

import java.util.Map;

/**
 * MobileNoticeService接口，可以自定义扩展接口
 * @Description:TODO
 * @author rmysj
 * @date 2018年11月21日 下午4:29:33
 * @version 1.0
 *
 */
public interface MobileNoticeService extends BaseService<MobileNotice, MobileNoticeCriteria> {

	/**
	 * 分页查找消息列表
	 * @param entity
	 * @return
	 * @version 1.0
	 * @author rmysj
	 * @throws Exception 
	 * @date 2018年11月30日 上午9:22:12
	 */
	Map<String,Object> findMessagesByPage(MobileNotice entity,Map<String,Object> map) throws Exception;

}
