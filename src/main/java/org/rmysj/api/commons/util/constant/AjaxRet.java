package org.rmysj.api.commons.util.constant;

import com.alibaba.fastjson.JSONObject;
import org.rmysj.api.commons.util.StringUtils;

/**
 * 返回对象
 *
 * @author bxgms
 */
public class AjaxRet extends JSONObject
{
    private static final long serialVersionUID = 1L;

    /** 状态码 */
    public static final String CODE_TAG = "code";

    /** 状态描述 */
    public static final String MSG_TAG = "msg";

    /** 数据对象 */
    public static final String DATA_TAG = "data";

    public static final String CODE_TAG_SUCCESS = "0";

    public static final String CODE_TAG_ERR = "1";

    public static final String MSG_TAG_SUCCESS = "success";

    /**
     * 初始化一个新创建的 AjaxRet 对象，使其表示一个空消息。
     */
    public AjaxRet()
    {

    }

    /**
     * 初始化一个新创建的 AjaxRet 对象
     *
     * @param code 状态码
     */
    public AjaxRet(String code)
    {
        super.put(CODE_TAG, code);
    }

    /**
     * 初始化一个新创建的 AjaxRet 对象
     *
     * @param code 状态码
     */
    public AjaxRet(String code, String msg)
    {
        super.put(CODE_TAG, code);
        if(StringUtils.isNotBlank(msg)) {
            super.put(MSG_TAG, msg);
        }
    }

    /**
     * 初始化一个新创建的 AjaxRet 对象
     *
     * @param code 状态码
     * @param data 数据对象
     */
    public AjaxRet(String code, String msg, Object data)
    {
        super.put(CODE_TAG, code);
        if(StringUtils.isNotBlank(msg)) {
            super.put(MSG_TAG, msg);
        }
        if (StringUtils.isNotBlank(data))
        {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static AjaxRet success()
    {
        return AjaxRet.success(null);
    }

    /**
     * 返回成功数据
     * @param data 数据对象
     * @return 成功消息
     */
    public static AjaxRet success(Object data)
    {
        return new AjaxRet(CODE_TAG_SUCCESS,MSG_TAG_SUCCESS,data);
    }

    /**
     * 返回错误数据
     *
     * @return 成功消息
     */
    public static AjaxRet err(String code)
    {
        return AjaxRet.err(code,null);
    }

    /**
     * 返回错误数据
     *
     * @return 成功消息
     */
    public static AjaxRet err(String code,String msg)
    {
        return new AjaxRet(code,msg,null);
    }

    /**
     * 返回错误数据
     *
     * @return 成功消息
     */
    public static AjaxRet err()
    {
        return new AjaxRet(CODE_TAG_ERR);
    }
}
