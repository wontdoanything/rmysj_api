package org.rmysj.api.commons.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * BaseAspect
 *
 * @author bxgms
 * @email fyc8729@163.com
 * @date 2020/8/18
 */
public abstract class BaseAspect {

    /**
     * 是否存在注解，如果存在就获取
     * @return
     */
    protected <T extends Annotation> T getAnnotation(JoinPoint joinPoint, Class<T> clz) throws Exception
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(clz);
        }
        return null;
    }

}
