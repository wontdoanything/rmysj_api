package org.rmysj.api.sync.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解1：用于方法上
 * 类说明
 * @author
 * @version 创建时间:2017年10月27日上午10:10:47
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//@Component
public @interface P4jSyn {
    /**
     * 锁的key<br/>
     * 如果想增加坑的个数添加非固定锁，可以在参数上添加@P4jSynKey注解，但是本参数是必写选项<br/>
     * redis key的拼写规则为 "RedisSyn+" + synKey + @P4jSynKey<br/>
     *
     */
    String synKey();

    /**
     * 持锁时间，超时时间，持锁超过此时间自动丢弃锁<br/>
     * 单位毫秒,默认2秒<br/>
     * 如果为0表示永远不释放锁，在设置为0的情况下toWait为true是没有意义的<br/>
     * 但是没有比较强的业务要求下，不建议设置为0
     */
    long keepMills() default 2 * 1000;

    /**
     * 当获取锁失败，是继续等待还是放弃<br/>
     * 默认为继续等待
     */
    boolean toWait() default true;

    /**
     * 没有获取到锁的情况下且toWait()为继续等待，睡眠指定毫秒数继续获取锁，也就是轮训获取锁的时间<br/>
     * 默认为10毫秒
     *
     * @return
     */
    long sleepMills() default 10;

    /**
     * 锁获取超时时间：<br/>
     * 没有获取到锁的情况下且toWait()为true继续等待，最大等待时间，如果超时抛出
     * {@link java.util.concurrent.TimeoutException}
     * ，可捕获此异常做相应业务处理；<br/>
     * 单位毫秒,默认一分钟，如果设置为0即为没有超时时间，一直获取下去；
     *
     * @return
     */
    long maxSleepMills() default 6 * 1000;
}
