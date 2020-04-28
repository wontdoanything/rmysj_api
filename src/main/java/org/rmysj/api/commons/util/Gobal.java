package org.rmysj.api.commons.util;

import com.google.common.collect.Maps;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by rmysj on 2017/7/3 下午2:11.
 */
@Component
@ConfigurationProperties(prefix = "target")
public class Gobal {

    public static class properties{

    }



    private Map<String, String> xiaoi = Maps.newHashMap();

    private Map<String, String> base = Maps.newHashMap();

    public Map<String, String> getXiaoi() {
        return xiaoi;
    }

    public void setXiaoi(Map<String, String> xiaoi) {
        this.xiaoi = xiaoi;
    }

    public Map<String, String> getBase() {
        return base;
    }

    public void setBase(Map<String, String> base) {
        this.base = base;
    }

    public Gobal() {
    }


}
