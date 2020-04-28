package org.rmysj.api.api.mobile.service.impl;

import java.util.List;
import java.util.Map;

import org.rmysj.api.api.mobile.dao.MobileNoticeMapper;
import org.rmysj.api.api.mobile.domain.MobileNotice;
import org.rmysj.api.api.mobile.domain.MobileNoticeCriteria;
import org.rmysj.api.api.mobile.web.dto.MobileNoticeDto;
import org.rmysj.api.commons.controller.BaseController;
import org.rmysj.api.commons.dao.BaseDao;
import org.rmysj.api.commons.service.BaseServiceImpl;
import org.rmysj.api.commons.service.redis.IRedisService;
import org.rmysj.api.commons.util.constant.HttpCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.rmysj.api.api.mobile.service.MobileNoticeService;


@Service(value = "mobileNoticeServiceImpl")
@Transactional
public class MobileNoticeServiceImpl extends BaseServiceImpl<MobileNotice, MobileNoticeCriteria>
        implements MobileNoticeService {

    @Autowired
    private MobileNoticeMapper mobileNoticeDao;
    
    @Autowired
    private IRedisService redisService;

    @Override
    protected BaseDao<MobileNotice, MobileNoticeCriteria, String> getDao() {
        return mobileNoticeDao;
    }

	@Override
	public Map<String,Object> findMessagesByPage(MobileNotice entity,Map<String,Object> map) throws Exception{
		List<MobileNotice> noticeList = mobileNoticeDao.findMessagesByPage(entity);
		if(noticeList.size()>0){
			List<MobileNoticeDto> messageList = new MobileNoticeDto().convert(noticeList);
	        
	        MobileNoticeCriteria mCritera = new MobileNoticeCriteria();
	        mCritera.createCriteria().andDelFlagEqualTo("0").andCompanyIdEqualTo(entity.getCompanyId());
	        Integer count = mobileNoticeDao.countByExample(mCritera);
	        
	        map.put("totalPage", count/entity.getPageSize()+1);
	        map.put("messageList", messageList);
		}else{
			map.put(BaseController.STATUS, HttpCodeEnum.QUERY_NULL.getCode());
			map.put(BaseController.DESC, HttpCodeEnum.QUERY_NULL.getDesc()+"消息列表为空");
		}
        
		return map;
	}

}
