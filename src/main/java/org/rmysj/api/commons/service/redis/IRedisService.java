package org.rmysj.api.commons.service.redis;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by rmysj on 2017/6/28 上午11:11.
 */
public interface IRedisService {

    public Jedis getResource();

    public void returnResource(Jedis jedis);

    public void set(String key, String value);

    public void set(String key,String value,int time);

    void del(String key);

    void fuzzyDel(String key);

    public String get(String key);

    public List<String> getKeys (String key);

    public Object getObject(String key);

    public void setObject(String key,Object object,int time);

    boolean exists(String key);
}
