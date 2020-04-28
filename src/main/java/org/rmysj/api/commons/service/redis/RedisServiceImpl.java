package org.rmysj.api.commons.service.redis;

import com.google.common.collect.Lists;
import org.rmysj.api.commons.util.ObjectUtils;
import org.rmysj.api.commons.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

/**
 * Created by rmysj on 2017/6/28 上午11:13.
 */
@Service
public class RedisServiceImpl implements IRedisService  {

    Logger logger = Logger.getLogger(RedisServiceImpl.class);

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private JedisOperateComponent jedisOperateComponent;

    @Override
    public Jedis getResource() {
        return jedisPool.getResource();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void returnResource(Jedis jedis) {
        if(jedis != null){
            jedis.close();
        }
    }

    @Override
    public boolean exists(String key) {
        boolean result = false;
        Jedis jedis = null;

        try {
            jedis = getResource();
            result = jedis.exists(key);
            logger.debug("exists " + key);
        } catch (Exception var7) {
            logger.warn("exists " + key,  var7);
        } finally {
            returnResource(jedis);
        }

        return result;
    }

    @Override
    public void set(String key, String value) {
        Jedis jedis=null;
        try{
            jedis = getResource();
            jedis.set(key, value);
            logger.info("Redis set success - " + key + ", value:" + value);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Redis set error: "+ e.getMessage() +" - " + key + ", value:" + value);
        }finally{
            returnResource(jedis);
        }
    }

    @Override
    public void set(String key, String value,int time) {
        Jedis jedis=null;
        try{
            jedis = getResource();
            jedis.set(key, value);
            if (time != 0) {
                jedis.expire(key,time);
            }
            logger.info("Redis set success - " + key + ", value:" + value);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Redis set error: "+ e.getMessage() +" - " + key + ", value:" + value);
        }finally{
            returnResource(jedis);
        }
    }

    @Override
    public String get(String key) {
        String result = null;
        Jedis jedis=null;
        try{
            jedis = getResource();
            result = jedis.get(key);
            logger.info("Redis get success - " + key + ", value:" + result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Redis set error: "+ e.getMessage() +" - " + key + ", value:" + result);
        }finally{
            returnResource(jedis);
        }
        return result;
    }

    @Override
    public List<String> getKeys(String key) {
        List<String> list = Lists.newArrayList();
        String result = null;
        Jedis jedis=null;
        try{
            jedis = getResource();
            Set<String> set = jedis.keys(key);
            for(String k:set) {
                list.add(k);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Redis set error: "+ e.getMessage() +" - " + key + ", value:" + result);
        }finally{
            returnResource(jedis);
        }
        return list;
    }

    @Override
    public void del(String key) {
        String result = null;
        Jedis jedis=null;
        try{
            jedis = getResource();
            if (jedis.exists(key)) {
                jedis.del(key);
            }
            logger.info("Redis del success - " + key );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Redis set error: "+ e.getMessage() +" - " + key + ", value:" + result);
        }finally{
            returnResource(jedis);
        }
    }

    @Override
    public void fuzzyDel(String key) {
        jedisOperateComponent.delKeys(key);
    }

    @Override
    public Object getObject(String key) {
        Object result = null;
        Jedis jedis=null;
        try{
            jedis = getResource();
            if (jedis.exists(getBytesKey(key))) {
                result = toObject(jedis.get(getBytesKey(key)));
                logger.info("Redis get success - " + key + ", value:" + result);
            }else{
                logger.info("Redis not exists - " + key );
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Redis set error: "+ e.getMessage() +" - " + key + ", value:" + result);
        }finally{
            returnResource(jedis);
        }
        return result;
    }

    @Override
    public void setObject(String key, Object object, int time) {
        Jedis jedis=null;
        try{
            jedis = getResource();
            jedis.set(getBytesKey(key), toBytes(object));
            if (time != 0) {
                jedis.expire(key,time);
            }
            logger.info("Redis set success - " + key + ", value:" + object);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Redis set error: "+ e.getMessage() +" - " + key + ", value:" + object);
        }finally{
            returnResource(jedis);
        }
    }

    /**
     * 获取byte[]类型Key
     * @param object
     * @return
     */
    public static byte[] getBytesKey(Object object){
        if(object instanceof String){
            return StringUtils.getBytes((String)object);
        }else{
            return ObjectUtils.serialize(object);
        }
    }

    /**
     * 获取byte[]类型Key
     * @param key
     * @return
     */
    public static Object getObjectKey(byte[] key){
        try{
            return StringUtils.toString(key);
        }catch(UnsupportedOperationException uoe){
            try{
                return toObject(key);
            }catch(UnsupportedOperationException uoe2){
                uoe2.printStackTrace();
            }
        }
        return null;
    }



    /**
     * Object转换byte[]类型
     * @param object
     * @return
     */
    public static byte[] toBytes(Object object){
        return ObjectUtils.serialize(object);
    }

    /**
     * byte[]型转换Object
     * @param bytes
     * @return
     */
    public static Object toObject(byte[] bytes){
        return ObjectUtils.unserialize(bytes);
    }
}
