package j2cacheTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmysj.api.Application;
import org.rmysj.api.commons.service.J2cacheService;
import org.rmysj.api.commons.service.redis.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class J2cacheTest {

    @Autowired
    private J2cacheService j2cacheService;

    @Test
    public void addCache(){
        System.out.println(j2cacheService.addCache("5","double"));
    }

    @Test
    public void queryRedis(){
        System.out.println("ecache---->" + j2cacheService.queryCache("1"));
        System.out.println("redis---->" + j2cacheService.queryCache("2"));
    }

    @Test
    public void getKey(){
        System.out.println(j2cacheService.getKey("5"));
    }

    @Test
    public void deleteKey(){
        System.out.println(j2cacheService.deleteKey("5"));
    }
}
