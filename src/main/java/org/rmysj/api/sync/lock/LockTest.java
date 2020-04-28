package org.rmysj.api.sync.lock;


import org.rmysj.api.commons.service.redis.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

//@Component("lockTest")
public class LockTest {
    @Autowired
    private LockInfo lockInfo ;

    @Autowired
    private IRedisService redisService;

    private String key = "rmysj:test:users:" + "testadmin";

    @Scheduled(fixedRate=3600000,initialDelay = 10000)
    public void run(){
        redisService.del(key);
        for (int i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                public void run() {
                    /*try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    lockInfo .addSycLock("11111", "222222");
                }
            }).start();
        }
    }

}

