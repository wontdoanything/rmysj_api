package lock;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("lockTest")
public class LockTest {
    @Autowired
    private LockInfo lockInfo ;

    @Scheduled(fixedRate=3600000,initialDelay = 10000)
    public void run(){
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lockInfo .addSycLock("11111", "222222");
                }
            }).start();
        }
    }

}

