package org.rmysj.api.commons.aspect.annotation;

import java.lang.annotation.*;

/**
 * JSONValidate
 * 自定义hibernate.validator标签
 *
 * @author bxgms
 * @email fyc8729@163.com
 * @date 2020/8/18
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JSONValidate {

    /**
     * 用于验证的javabean模型
     * @return
     */
    public Class value();
}
