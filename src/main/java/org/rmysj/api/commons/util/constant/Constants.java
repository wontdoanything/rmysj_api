package org.rmysj.api.commons.util.constant;


/**
 * 通用常量信息
 *
 * @author bxgms
 */
public class Constants
{
    /**
     * store财务
     */
    public static final String STORE_LEVEL_CW = "1";

    /**
     * store店长
     */
    public static final String STORE_LEVEL_DZ = "2";

    /**
     * 门店店员ID
     */
    public static final String STAFF_ID = "STAFF_ID";

    /**
     * 门店ID
     */
    public static final String STORE_ID = "STORE_ID";

    /**
     * 登陆折门店权限
     */
    public static final String SCOPE_STAFF_STORES = "_2storeIdList";

    /**
     *  银行账单路径地址前缀
     */
    public static final String RZ_PATH_PERFIX = "/Users/fangyechao/Downloads/";

    /**
     *  银行账单对账结果OK
     */
    public static final String RECEIPT_STATE_OK = "2";

    /**
     *  银行账单对账结果ERR
     */
    public static final String RECEIPT_STATE_ERR = "3";

    /**
     * 删除标志
     */
    public static final String DEL_FLAG_YES = "1";

    public static final String DEL_FLAG_NO = "0";

    /**
     * 删除标志
     */
    public static final String YES = "1";

    public static final String NO = "0";

    public static final String[] IGNORE_COPY_PROS = new String[]{"createDate","createBy","UPDATE_TIME","CREATE_TIME"};

    public static final String[] IGNORE_CRITERIA_PROS = new String[]{STAFF_ID,SCOPE_STAFF_STORES};

    public static final double TEN_THD = 10*10*10*10D;

    public static final String GOODSINV_TYPE_COMMON = "1"; //普通分类

    public static final String GOODSINV_TYPE_DIS = "2";//折扣分类

}
