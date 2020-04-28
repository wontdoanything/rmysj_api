package org.rmysj.api.config;

import com.google.common.collect.Maps;
import org.rmysj.api.commons.util.PropertiesLoader;
import org.rmysj.api.commons.util.StringUtils;

import java.util.Map;

/**
 * Created by rmysj on 2018/2/24 下午3:34.
 */
public class Glob {

    private static PropertiesLoader fileLoader = new PropertiesLoader("application.properties");

//    private static PropertiesLoader loader = new PropertiesLoader("application.properties");

    private static PropertiesLoader loader;

    public static String YS7_LAPP_DEVICE_INFO_URL = "https://open.ys7.com/api/lapp/device/info";

    public static String CMS_URL = "/rmysj_cms";

    public static String HTTP = "http://";

    public static String ACTIVE_DEFENDER_URL = "/f/warn/rmysjWarnHis/activeDefenderPush?warnId=";

    public static String QS_WARN_STR1 = "预警";

    public static String QS_WARN_STR2 = "报警";

    public static String QS_WARN_STR3 = "持续";

    static{
        String file = "application-" + fileLoader.getProperty("spring.profiles.active") + ".properties";
        loader =  new PropertiesLoader(file);
    }

    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = Maps.newHashMap();

    public static String getConfig(String key) {
        String value = map.get(key);
        if (value == null){
            value = loader.getProperty(key);
            map.put(key, value != null ? value : StringUtils.EMPTY);
        }
        return value;
    }

    public static String testQSxml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
            "<notifycation>\n" +
            "    <deviceid>50:9A:4C:54:96:52:0001</deviceid>\n" +
            "    <site>龙港枪机001</site>\n" +
//            "    <deviceid>50:9A:4C:54:8C:DF:0001</deviceid>\n" +
//            "    <site>广深铁路摄像机-03</site>\n" +
            "    <channel>1</channel>\n" +
            "    <alert_info>\n" +
            "        <type>0</type>\n" +
            "        <targetid>1</targetid>\n" +
            "        <name>预警1</name>\n" +
            "        <time>2018-06-29 09:25:39.796</time>\n" +
            "        <value>0</value>\n" +
            "        <rule_info>\n" +
            "            <aoi_point>\n" +
            "                <x>0.37380</x>\n" +
            "                <y>0.19250</y>\n" +
            "            </aoi_point>\n" +
            "            <aoi_point>\n" +
            "                <x>0.14110</x>\n" +
            "                <y>0.78870</y>\n" +
            "            </aoi_point>\n" +
            "            <aoi_point>\n" +
            "                <x>0.66090</x>\n" +
            "                <y>0.79250</y>\n" +
            "            </aoi_point>\n" +
            "            <aoi_point>\n" +
            "                <x>0.82920</x>\n" +
            "                <y>0.20000</y>\n" +
            "            </aoi_point>\n" +
            "        </rule_info>\n" +
            "        <picture_count>1</picture_count>\n" +
            "        <picture_info>\n" +
            "            <width>352</width>\n" +
            "            <height>288</height>\n" +
            "            <length>24834</length>\n" +
            "            <byte>***</byte>\n" +
            "        </picture_info>\n" +
            "    </alert_info>\n" +
            "</notifycation>";
}
