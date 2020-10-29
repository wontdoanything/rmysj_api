package org.rmysj.api.commons.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.rmysj.api.commons.aspect.annotation.JSONValidate;
import org.rmysj.api.commons.exception.CustomException;
import org.rmysj.api.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * serviceAspect
 * 店员权限查询，目前支持店长和财务
 *
 * @author bxgms
 * @email fyc8729@163.com
 * @date 2020/8/18
 */
@Aspect
@Component
public class BeanValidateAspect extends BaseAspect {

    private static final Logger log = LoggerFactory.getLogger(BeanValidateAspect.class);

    @Resource
    protected Validator validator;

    @Pointcut("@annotation(org.rmysj.api.commons.aspect.annotation.JSONValidate)")
    public void jSONValidatePointCut(){

    }

    /**
     * 校驗方法
     * @param joinPoint
     */

    @Before("jSONValidatePointCut()")
    public void before(JoinPoint joinPoint) throws CustomException,Exception{
        String name=joinPoint.getSignature().getName();
        JSONValidate jsonValidate =  getAnnotation(joinPoint, JSONValidate.class);
        if(jsonValidate == null) {
            return;
        }
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        JSONObject param = (JSONObject)joinPoint.getArgs()[ArrayUtils.indexOf(argNames, "param")];
        Class cls = jsonValidate.value();
        String validateStr = validateFormObject(JSON.toJavaObject(param,cls));
        if(StringUtils.isNotBlank(validateStr)) {
            throw new CustomException(validateStr);
        }
    }

    @AfterReturning("jSONValidatePointCut()")
    public void afterMethod(){
//        System.out.println("后置通知!");
    }

    @AfterThrowing("jSONValidatePointCut()")
    public void afterThrowing()throws Throwable{
//        System.out.println("异常通知");
    }

    @After("jSONValidatePointCut()")
    public void finalMethod(){
//        System.out.println("最终通知");
    }

    protected <T> String validateFormObject(T t){
        StringBuffer validateBuff = new StringBuffer();
        Set<ConstraintViolation<T>> validate = validator.validate(t);
        for (ConstraintViolation<T> dem : validate) {
            log.debug(dem.getMessage());
            validateBuff.append(dem.getMessage()).append("\n");
        }
        return validateBuff.toString();
    }



}
