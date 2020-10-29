package org.rmysj.api.commons.util;

import com.alibaba.fastjson.JSONObject;
import org.rmysj.api.commons.aspect.annotation.CriteriaCondition;
import org.rmysj.api.commons.util.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * CriteriaUtils
 * 用于处理Criteria条件的工具类
 *
 * @author bxgms
 * @email fyc8729@163.com
 * @date 2020/9/30
 */
public class CriteriaUtils {

    private static   Logger logger = LoggerFactory.getLogger(CriteriaUtils.class);

    public static void invokeWhereParams (Object c, JSONObject param){
        String[] dateParam = new String[]{"queryDate"};
        invokeWhereParams(c,param,dateParam,null, Constants.IGNORE_CRITERIA_PROS);
    }

    /**
     *
     * @param c
     * @param param
     * @param dateParam
     */
    public static  void invokeWhereParams (Object c, JSONObject param,String[] dateParam){
        invokeWhereParams(c,param,dateParam,null, Constants.IGNORE_CRITERIA_PROS);
    }

    /**
     *
     * @param c
     * @param param
     * @param dateParam
     */
    public static <T> void invokeWhereParams (Object c, JSONObject param,String[] dateParam,Class<T> cls){
        invokeWhereParams(c,param,dateParam,cls,Constants.IGNORE_CRITERIA_PROS);
    }

    /**
     *
     * @param c
     * @param param
     * @param dateParam
     */
    public static <T> void invokeWhereParams (Object c, JSONObject param,String[] dateParam,Class<T> cls,String... ignores){
        try {
            T object = null;
            if(cls != null) {
                object = FastJSONUtils.JSONObj2Obj(param,cls);
            }
            Iterator<String> iterator =  param.getInnerMap().keySet().iterator();
            while(iterator.hasNext()) {
                String key = iterator.next();
                Object value = param.getOrDefault(key,null);
                if(StringUtils.isNotBlank(value)
                        && (ignores == null || (ignores != null && !Arrays.asList(ignores).contains(key)))) {
                    if("DATETIME".equalsIgnoreCase(key)) {
                        if(StringUtils.isNotBlank(param.getString(key))) {
                            Date[] pubDates = DateUtils.getDate2(param.getJSONArray(key));
                            for(int i = 0 ; i < dateParam.length;i++) {
                                System.out.println(param.getString(dateParam[i]));
                                String method = "and" + StringUtils.capitalize(dateParam[i]) + "Between";
                                Reflections.invokeMethod(c,method,new Class[]{pubDates[0].getClass(),pubDates[1].getClass()},new Object[]{pubDates[0],pubDates[1]});
                            }
                        }
                    }else {
                        String method = "and" + StringUtils.capitalize(key) + "EqualTo";
                        if(cls != null) {
                            CriteriaCondition aspect = Reflections.getAccessibleFieldAspect(object,key, CriteriaCondition.class);
                            if(aspect!= null && aspect.isLike()) {
                                method = "and" + StringUtils.capitalize(key) + "Like";
                                value = "%" + value + "%";
                            }
                        }
                        Reflections.invokeMethod(c,method,new Class[]{value.getClass()},new Object[]{value});
                    }
                }
            }
        }catch (Exception e) {
            logger.error(e.getMessage(),e.getCause());
        }
    }

    /**
     * 拷贝查询条件；仅将原Criteria中的condition复制到目标Criteria，不会覆盖目标Criteria已有condition
     *
     * @param source 原Criteria
     * @param target 目标Criteria
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> void copyCondition(T source, T target)
    {
        if (null != source && null != target)
        {
            try
            {
                Field field = source.getClass().getSuperclass().getDeclaredField("criteria");
                field.setAccessible(true);

                List sourceCriteria = (List) field.get(source);
                List targetCriteria = (List) field.get(target);

                targetCriteria.addAll(sourceCriteria);

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
