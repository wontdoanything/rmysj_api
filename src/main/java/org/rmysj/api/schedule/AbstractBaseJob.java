package org.rmysj.api.schedule;

import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by rmysj on 2018/1/15 下午2:15.
 */
public abstract class AbstractBaseJob implements Job{
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());
}
