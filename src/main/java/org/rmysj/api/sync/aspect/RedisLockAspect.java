package org.rmysj.api.sync.aspect;

import org.rmysj.api.sync.component.P4jSyn;
import org.rmysj.api.sync.component.P4jSynKey;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 类说明
 * @author
 * @version 创建时间:2017年10月27日上午10:14:06
 */
@Order(3)
@Aspect
@Component("redisLockAspect")
public class RedisLockAspect {
    private static final Logger logger = LoggerFactory.getLogger(RedisLockAspect.class);

    @Resource
    @Qualifier("redisTemplate")
    private RedisTemplate<String, Long> redisTemplate;


    @Around("execution(* org.rmysj..*SycLock(..))")
    public Object lock(ProceedingJoinPoint pjp) throws Throwable {
        //获取P4jSyn注解
        P4jSyn lockInfo = getLockInfo(pjp);
        if (lockInfo == null) {
            logger.error("分布式锁配置参数错误");
            throw new IllegalArgumentException("配置参数错误");
        }
        String synKey = getSynKey(pjp, lockInfo.synKey());
        if (synKey == null || "".equals(synKey)) {
            logger.error("分布式锁配置参数synKey错误");
            throw new IllegalArgumentException("配置参数synKey错误");
        }
        boolean lock = false;  //标志物，true表示获取了到了该锁
        Object obj = null;
        int checkLockIdx = 0;
        try {
            //超时时间 (6秒)，系统当前时间再往后加6秒
            long maxSleepMills = System.currentTimeMillis() + lockInfo.maxSleepMills();
            while (!lock) {
                if(checkLockIdx >= 2) {
                    logger.error("分布锁检测次数超过3次，获取资源超时");
                    throw new TimeoutException("分布锁检测次数超过3次，获取资源超时");
                }
                //持锁时间，系统当前时间再往后加20秒
                long keepMills = System.currentTimeMillis() + lockInfo.keepMills();
                //为key“synKey”设置值keepMills，如果设置成功，则返回true
                lock = setIfAbsent(synKey, keepMills);
                //lock为true表示得到了锁，没有人加过相同的锁
                if(lock){
                    //如果获得了该锁，则调用目标方法，执行业务逻辑任务
                    obj = pjp.proceed();
                }
                // 锁设置了没有超时时间
                /**如果没有通过setIfAbsent拿到数据，然后判断是否对锁设置了超时机制
                 ，没有设置则判断是否需要继续等待*/
                else if(lockInfo.keepMills() <= 0){
                    // 继续等待获取锁
                    if (lockInfo.toWait()) {
                        // 如果超过最大等待时间抛出异常
                        if(lockInfo.maxSleepMills()>0&&System.currentTimeMillis()> maxSleepMills){
                            logger.debug("获取锁资源等待超时");
                            throw new TimeoutException("获取锁资源等待超时");
                        }
                        //只要当前时间没有大于超时时间，则继续等待10毫秒，以便继续尝试去获取锁
                        TimeUnit.MILLISECONDS.sleep(lockInfo.sleepMills());
                    }else{
                        //如果注解上的“toWait()”为false,表示如果当前没有获取到锁，则放弃获取该锁，
                        //即放弃执行此任务
                        break;
                    }
                }
                // 已过期，并且getAndSet后旧的时间戳依然是过期的，可以认为获取到了锁
                /**
                 * 1.如果当前线程2进入的时候，
                 * 系统时间已经大于了上个任务的持锁时间(由于上次任务大导致其执行时间过长)，
                 * 则表示需要强制让上个任务释放锁，让本任务获得锁，以执行本次任务；
                 * 2.如果线程1释放了锁，刚好线程2过了 if(lock){ //to do something}的判断，
                 * 而进入了此处判断，需要对线程2任务加锁，保证事务不冲突
                 */
                else if(System.currentTimeMillis()>getLock(synKey)&&(System.currentTimeMillis()> getSet(synKey, keepMills))) {
                    lock = true;             //lock一定要设置成true,不然释放不了锁
                    obj = pjp.proceed();
                }
                // 没有得到任何锁
                else {
                    // 继续等待获取锁
                    if (lockInfo.toWait()) {
                        // 如果超过最大等待时间抛出异常
                        if (lockInfo.maxSleepMills() > 0 && System.currentTimeMillis() > maxSleepMills) {
                            logger.debug("获取锁资源等待超时");
                            throw new TimeoutException("获取锁资源等待超时");
                        }
                        //只要当前时间没有大于超时时间，则继续等待10毫秒，以便继续尝试去获取锁
                        TimeUnit.MILLISECONDS.sleep(lockInfo.sleepMills());
                    }else {
                        // 放弃等待，放弃获取锁(放弃本任务的执行)
                        break;
                    }
                }
                checkLockIdx ++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            // 如果获取到了锁，释放锁
            if (lock) {
                releaseLock(synKey);
            }
        }
        return obj;
    }

    /**
     * 获取包括方法参数上的key<br/>
     * redis key的拼写规则为 "RedisSyn+" + synKey + @P4jSynKey
     *
     */
    private String getSynKey(JoinPoint pjp, String synKey) {
        try {
            synKey = "RedisSyn+" + synKey;  //指定synKey的值固定为RedisSyn+synKey
            Object[] args = pjp.getArgs();  //获取切点上的所有参数
            if (args != null && args.length > 0) {
                MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
                Annotation[][] paramAnnotationArrays = methodSignature.getMethod().getParameterAnnotations();
                SortedMap<Integer, String> keys = new TreeMap<>();
                for (int ix = 0; ix < paramAnnotationArrays.length; ix++) {
                    P4jSynKey p4jSynKey = getAnnotation(P4jSynKey.class, paramAnnotationArrays[ix]);
                    if (p4jSynKey != null) {
                        Object arg = args[ix];
                        if (arg != null) {
                            keys.put(p4jSynKey.index(), arg.toString());
                        }
                    }
                }
                if (keys != null && keys.size() > 0) {
                    for (String key : keys.values()) {
                        synKey = synKey + key;
                    }
                }
            }
            return synKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Annotation> T getAnnotation(final Class<T> annotationClass, final Annotation[] annotations) {
        if (annotations != null && annotations.length > 0) {
            for (final Annotation annotation : annotations) {
                if (annotationClass.equals(annotation.annotationType())) {
                    return (T) annotation;
                }
            }
        }
        return null;
    }

    /**
     * 获取RedisLock注解信息
     */
    private P4jSyn getLockInfo(ProceedingJoinPoint  pjp) {
        try {
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            Method method = methodSignature.getMethod();
            P4jSyn lockInfo = method.getAnnotation(P4jSyn.class);
            return lockInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BoundValueOperations<String, Long> getOperations(String key) {
        return redisTemplate.boundValueOps(key);
    }

    /**
     * Set {@code value} for {@code key}, only if {@code key} does not exist.
     * <p>
     * See http://redis.io/commands/setnx
     *
     * @param key
     *            must not be {@literal null}.
     * @param value
     *            must not be {@literal null}.
     * @return
     */
    /**
     * 如果key不存在，则为key设置值value，并且返回true，否则返回false
     * @param key
     * @param value
     * @return
     */
    public boolean setIfAbsent(String key, Long value) {
        return getOperations(key).setIfAbsent(value);
    }

    /**
     * 获取key上的值
     * @param key
     * @return
     */
    public long getLock(String key) {
        Long time = getOperations(key).get();
        if (time == null) {
            return 0;
        }
        return time;
    }

    /**
     * 获取key上的旧值，并且为该key设置新值value,如果旧值不存在则返回0
     * @param key
     * @param value
     * @return
     */
    public long getSet(String key, Long value) {
        Long time = getOperations(key).getAndSet(value);
        if (time == null) {
            return 0;
        }
        return time;
    }

    /**
     * 删除key
     * @param key
     */
    public void releaseLock(String key) {
        redisTemplate.delete(key);
    }
}
