package test;

import com.google.common.collect.Maps;
import org.rmysj.api.Application;
import org.rmysj.api.commons.service.redis.IRedisService;
import org.rmysj.api.commons.util.constant.HttpCodeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * Created by rmysj on 2017/6/28 上午11:15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class redistest {

    @Autowired
    private IRedisService redisService;


    @Test
    public void test(){
        String value = "xxxx";
        redisService.set("name", value);
        System.out.println(redisService.get("name"));
    }

    @Test
    public void testMap2Str(){
        Map<String,Object> map = Maps.newHashMap();
        map.put("1", HttpCodeEnum.DISPER_UN_ONLINE.getCode());
        map.put("2", HttpCodeEnum.DISPER_UN_ONLINE.getDesc());
        System.out.println(map);
    }

    @Test
    public  void testFuzzyDel(){
        String key = "mp:pt";
        redisService.fuzzyDel(key);
    }
}
