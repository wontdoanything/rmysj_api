package org.rmysj.api.sync.lock;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.rmysj.api.commons.service.redis.IRedisService;
import org.rmysj.api.commons.util.Collections3;
import org.rmysj.api.commons.util.StringUtils;
import org.rmysj.api.sync.component.P4jSyn;
import org.rmysj.api.sync.component.P4jSynKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class LockUserCode {

    private int i = 0;

    private static Logger logger = LoggerFactory.getLogger(LockUserCode.class);

    @Autowired
    private IRedisService redisService;

    @P4jSyn(synKey="changeUserCode")
    public Map<String,Object> addSycLock(@P4jSynKey(index=1)String flag, @P4jSynKey(index=2) String channelCode,String personsKey,String value,int mode){
        i++;
        System.out.println("i =====================" + i);
        Map<String,Object> map = Maps.newHashMap();
        map.put("isDel",false);
        List<String> joinIdList = Lists.newArrayList();
        String joinIds = "";
        if(mode == 1) {
            if(!redisService.exists(personsKey)) {
                //每次刷新这个组拥有的人
                joinIdList = Lists.newArrayList();
                if (!joinIdList.contains(value)) {
                    joinIdList.add(value);
                }
                joinIds = Collections3.convertToString(joinIdList,",");
                redisService.set(personsKey, joinIds);
            }else {
                String ids = redisService.get(personsKey);
                joinIdList = Collections3.String2ArrayList(ids,",");
                if (!joinIdList.contains(value)) {
                    joinIdList.add(value);
                }
                joinIds = Collections3.convertToString(joinIdList,",");
                redisService.set(personsKey, joinIds);
            }
        }else {
            String userIds = redisService.get(personsKey);
            if(StringUtils.isNotBlank(userIds)) {
                joinIdList = Collections3.String2ArrayList(userIds,",");
                if(joinIdList.size() == 0) {
                    redisService.del(personsKey);
                    map.put("isDel",true);
                }else if(joinIdList.size() == 1){
                    if(joinIdList.contains(value)) {
                        redisService.del(personsKey);
                        map.put("isDel",true);
                    }
                }else {
                    if(joinIdList.contains(value)) {
                        joinIdList.remove(value);
                    }
                    joinIds = Collections3.convertToString(joinIdList,",");
                    redisService.set(personsKey,joinIds,0);
                }
            }
        }
        logger.debug("[" + personsKey + "]存储的值：" + joinIds);
        logger.debug("[" + personsKey + "]存储的值的长度：" + joinIdList.size());
        map.put("userIds",joinIds);
        return map;
    }

}
