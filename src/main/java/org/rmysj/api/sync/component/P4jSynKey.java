package org.rmysj.api.sync.component;

import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 类说明
 * @author
 * @version 创建时间:2017年10月27日上午10:12:25
 */
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface P4jSynKey {
    /**
     * key的拼接顺序
     *
     * @return
     */
    int index() default 0;
}
