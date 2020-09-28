package org.rmysj.api.config;

import com.google.common.collect.Maps;
import org.rmysj.api.commons.util.PropertiesLoader;
import org.rmysj.api.commons.util.StringUtils;

import java.util.Map;

/** Created by rmysj on 2018/2/24 下午3:34. */
public class Glob {

  private static PropertiesLoader fileLoader = new PropertiesLoader("application.properties");

  //    private static PropertiesLoader loader = new PropertiesLoader("application.properties");

  private static PropertiesLoader loader;

  /** 上传路径 */
  private static String profile;

  private static String resourceUrlPerfix;

  static {
    String file = "application-" + fileLoader.getProperty("spring.profiles.active") + ".properties";
    loader = new PropertiesLoader(file);
  }

  /** 保存全局属性值 */
  private static Map<String, String> map = Maps.newHashMap();

  public static String getConfig(String key) {
    String value = map.get(key);
    if (value == null) {
      value = loader.getProperty(key);
      map.put(key, value != null ? value : StringUtils.EMPTY);
    }
    return value;
  }

  /**
   * 获取文件上传物理路径
   * @return
   */
  public static String getProfile()
  {
    if(profile == null) {
      profile = getConfig("upload.baseDir");
    }
    return profile;
  }

  public void setProfile(String profile)
  {
    Glob.profile = profile;
  }



  /**
   * 获取商品图片上传物理路径
   */
  public static String getGoodSinvPath(String storeId)
  {
    return getProfile() + "/goodSinv/" + storeId;
  }


  /**
   * 获取文件资源URL前缀
   */
  public static String getResourceUrlPerfix() {
    if(resourceUrlPerfix == null) {
      resourceUrlPerfix = getConfig("upload.baseUrl");
    }
    return resourceUrlPerfix;
  }

  public void setResourceUrlPerfix(String resourceUrlPerfix){
    Glob.resourceUrlPerfix = resourceUrlPerfix;
  }

}
