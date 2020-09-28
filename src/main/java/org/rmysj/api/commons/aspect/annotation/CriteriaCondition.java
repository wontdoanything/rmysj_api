package org.rmysj.api.commons.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CriteriaCondition
 * 自定义用于组装mybatis SimpleExample Criteria条件的标签
 *
 * @author bxgms
 * @email fyc8729@163.com
 * @date 2020/9/24
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CriteriaCondition {

    /**
     * 是否模糊查询
     * @return
     */
    public boolean isLike() default false;
}
