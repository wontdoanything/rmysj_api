package org.rmysj.api.commons.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * FastJSONUtils
 *
 * @author bxgms
 * @email fyc8729@163.com
 * @date 2020/10/13
 */
public class FastJSONUtils {

    /**
     * javabean转JSONObject
     * @param obj
     * @return
     */
    public static JSONObject obj2JSONObj(Object obj){
        return JSONObject.parseObject(JSON.toJSONString(obj));
    }

    /**
     * List或数组转JSONArray
     * @param obj
     * @return
     */
    public static JSONArray listarr2JSONArr(Object obj){
        return JSONArray.parseArray(JSON.toJSONString(obj));
    }

    /**
     * JSONObject转JAVAbean对象
     * @param obj JSONObject
     * @param cls CLASS类型
     * @param <T>
     * @return
     */
    public static <T> T JSONObj2Obj(JSONObject obj,Class<T> cls){
        return JSONObject.parseObject(obj.toJSONString(),cls);
    }

    /**
     * JSONArray转List
     * @param obj JSONArray
     * @param cls CLASS类型
     * @param <T>
     * @return
     */
    public static <T> T JSONArr2list(JSONArray obj,Class<T> cls){
        return JSONArray.parseObject(obj.toJSONString(),cls);
    }
}
