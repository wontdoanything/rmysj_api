package org.rmysj.api.commons.service;

import net.oschina.j2cache.CacheChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class J2cacheServiceImpl implements J2cacheService{

    @Autowired
    CacheChannel cacheChannel;

    @Override
    public String addCache(String key, String value) {
        cacheChannel.set("Euser",key,value);
        return "region:Euser,key:"+key+",value:"+value;
    }

    @Override
    public String queryCache(String type) {
        StringBuilder sb = new StringBuilder();
        if("1".equals(type)){
            System.out.println("ehcache缓存==================");
            //CacheProvider l1 = cacheChannel.getL1Provider();
            Collection<String> keys = cacheChannel.keys("Euser");
            for(String key : keys){
                sb.append("key:"+key+",value:"+cacheChannel.get("Euser",key));
            }
        }else if("2".equals(type)){
            System.out.println("redis二级缓存================");
            //CacheProvider l2 = cacheChannel.getL2Provider();
            Collection<String> keys = cacheChannel.keys("Euser");
            for(String key : keys){
                sb.append("key:"+key+",value:"+cacheChannel.get("Euser",key));
            }
        }
        return sb.toString();
   }

    @Override
    public String getKey(String key) {
        Object v = cacheChannel.get("Euser",key);
        if(v != null){
            return v.toString();
        }
        return "null";
    }

    @Override
    public String deleteKey(String key) {
        cacheChannel.evict("Euser",key);
        return "success";
    }
}
