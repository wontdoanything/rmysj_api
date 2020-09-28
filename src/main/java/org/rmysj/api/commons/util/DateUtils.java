package org.rmysj.api.commons.util;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author ruoyi
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils
{
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate()
    {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate()
    {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime()
    {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow()
    {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format)
    {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date)
    {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date)
    {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts)
    {
        try
        {
            return new SimpleDateFormat(format).parse(ts);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str)
    {
        if (str == null)
        {
            return null;
        }
        try
        {
            return parseDate(str.toString(), parsePatterns);
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate()
    {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate)
    {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    public static String fmtDate(Object date,String fmt) {
        SimpleDateFormat formatter = new SimpleDateFormat(fmt);
        String dateString = formatter.format(date);
        return dateString;
    }

    public static Date fmtString2Date(String date,String fmt)  {
        SimpleDateFormat formatter = new SimpleDateFormat(fmt);
        try {
            return  formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取年份的开始和结束
     */
    public static Date[] getfirstLastDayByYear(String year)
    {
        Date[] dates = new Date[2];
        Calendar firstCalendar = Calendar.getInstance();
        firstCalendar.set(Calendar.YEAR,Integer.parseInt(year));
        firstCalendar.set(Calendar.MONTH,0);
        firstCalendar.set(Calendar.DAY_OF_MONTH,1);
        firstCalendar.set(Calendar.HOUR_OF_DAY,0);
        firstCalendar.set(Calendar.MINUTE,0);
        firstCalendar.set(Calendar.SECOND,0);
        firstCalendar.set(Calendar.MILLISECOND,0);
        dates[0] = firstCalendar.getTime();
        Calendar lastCalendar = Calendar.getInstance();
        lastCalendar.setTime(dates[0]);
        lastCalendar.add(Calendar.YEAR,1);
        lastCalendar.add(Calendar.MILLISECOND,-1);
        dates[1] = lastCalendar.getTime();
        return dates;
    }

    /**
     *
     * @param pubStr ["yyyy-MM-ddThh:mm:ss:SSSz","yyyy-MM-ddThh:mm:ss:SSSz"]
     * @return
     */
    public static Date[] getDate2(JSONArray pubStr)
    {
        Date[] res = new Date[2];
        try {
            if (StringUtils.isNotBlankAndEmpty(pubStr) && pubStr.size() == 2)
            {
                String fmt = "yyyy-MM-ddTHH:mm:ss:SSSz";
                String str1 = pubStr.getString(0);
                String str2 = pubStr.getString(1);
                Date d1 = DateUtils.dateTime(fmt,str1);
                Date d2 = DateUtils.dateTime(fmt,str2);
                res[0] = d1;
                res[1] = d2;
            }
        }catch (Exception e) {

        }finally{
            return res;
        }
    }
}
