package org.rmysj.api.commons.service.redis;

import org.rmysj.api.commons.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * JedisOperateComponent.delKeys
 */
@Service
public class JedisOperateComponent {

    private static final Logger LOGGER = Logger.getLogger(JedisOperateComponent.class);

    //用来获取JedisCluster,可以自己实现
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JedisPool jedisPool;

    //线程池
    @Autowired
    private ThreadPoolTaskExecutor threadPool;

    private List<Jedis> redisMasterList = new ArrayList<Jedis>();

    private void initJedisList(int retryTimes){
        LOGGER.error("initJedisList retry time:"+retryTimes);
        redisMasterList.clear();
        if(retryTimes > 1){
            LOGGER.error("initJedisList retry 2 times, all is failed!");
            return;
        }
        retryTimes++;
        boolean retry = false;
            Jedis jedis = null;
            try{
                jedis = jedisPool.getResource();
                if (isMaster(jedis)) {
                    redisMasterList.add(jedis);
                }else{
                    //关闭slave连接
                    jedis.close();
                }
            }catch (Exception e){
                if(jedis != null){
                    jedis.close();
                }
                LOGGER.error("get resource error:", e);
                retry = true;
            }

        if(retry){
            //重试之前要关闭所有连接
            closeRedis();
            initJedisList(retryTimes);
        }else{
            if(redisMasterList.size() == 0){
                LOGGER.error("No master node is founded in redis cluster.");
            }
        }

    }
    //关闭redis
    private void closeRedis(){
        if(!redisMasterList.isEmpty()){
            for (Jedis jedis: redisMasterList){
                try {
                    jedis.close();
                }catch (Exception e){
                }
            }
            redisMasterList.clear();
        }
    }

    /** * 批量删除key，例：key* * [@param](https://my.oschina.net/u/2303379) keyPattern */
    public synchronized long delKeys(String keyPattern){
        Long startTime = System.currentTimeMillis();
        try{
            initJedisList(0);
        }catch (Exception e){
            LOGGER.error("init jedis list error: ", e);
        }
        long resSum = 0;
        if(null != redisMasterList && redisMasterList.size() > 0 && StringUtils.isNotEmpty(keyPattern)) {
            int redisNum = redisMasterList.size();
            CountDownLatch countDownLatch = new CountDownLatch(redisNum);
            Future[] futureArray = new Future[redisNum] ;
            ScanParams scanParams = new ScanParams().count(1500);
            scanParams.match(keyPattern);
            for (int i=0; i<redisNum; i++){
                futureArray[i] = threadPool.submit(new RedisDeleteThread(redisMasterList.get(i), scanParams,countDownLatch));
            }
            try {
                //等待线程汇总
                countDownLatch.await();
                try {
                    if(null!=futureArray&&futureArray.length>0){
                        for (int i = 0; i < futureArray.length; i++) {
                            resSum += (long)futureArray[i].get();
                        }
                    }
                } catch (InterruptedException e) {
                    LOGGER.error(e.getMessage());
                } catch (ExecutionException e) {
                    LOGGER.error(e.getMessage());
                }
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }finally {
                closeRedis();
            }
            long endTime = System.currentTimeMillis();
            LOGGER.error("clear cache,lenth of list: "+resSum+", consume time:" + (endTime - startTime)+"ms");

        }

        return resSum;
    }

    /** * 查看server是否为主库 * [@param](https://my.oschina.net/u/2303379) jedis * [@return](https://my.oschina.net/u/556800) */
    public boolean isMaster(Jedis jedis){
        String role =jedis.info("replication");
        if(role.indexOf("role:master")!=-1){
            return true;
        }
        return false;
    }

    class RedisDeleteThread implements Callable<Long> {
        private Jedis jedis;
        ScanParams params;
        CountDownLatch countDownLatch;

        public RedisDeleteThread(Jedis jedis, ScanParams params, CountDownLatch downLatch){
            this.jedis = jedis;
            this.params = params;
            this.countDownLatch = downLatch;
        }
        @Override
        public Long call() throws Exception {
            long sum = 0;
            try{
                String cursor = ScanParams.SCAN_POINTER_START;// 起始游标
                ScanResult<String> scanResult = null;
                List<String> result = null;
                Pipeline pipeline = jedis.pipelined();
                do{
                    scanResult = jedis.scan(cursor, params);
                    result = scanResult.getResult();
                    if(result != null && result.size() > 0){
                        sum += result.size();
                        int num = 0;
                        for (String key : result){
                            pipeline.del(key);
                            num++;
                            if(num > 500){
                                num = 0;
                                pipeline.sync();// 执行
                            }
                        }
                        if(num > 0){
                            pipeline.sync();// 执行
                        }
                    }
                    cursor = scanResult.getStringCursor();
                }
                while (!cursor.equals("0"));
                if(pipeline != null){
                    pipeline.close();
                }
            }catch (Exception e){
                LOGGER.error("delete redis error:", e);
            }finally {
                countDownLatch.countDown();
                jedis.close();
            }
            return sum;
        }
    }
}
