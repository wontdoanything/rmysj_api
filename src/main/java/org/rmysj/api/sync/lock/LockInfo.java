package org.rmysj.api.sync.lock;


import org.rmysj.api.sync.component.P4jSyn;
import org.rmysj.api.sync.component.P4jSynKey;
import org.springframework.stereotype.Component;

@Component
public class LockInfo {

        private int i = 0;

        @P4jSyn(synKey="getTrackno")
        public void addSycLock(@P4jSynKey(index=1)String flag, @P4jSynKey(index=2) String channelCode){
            i++;

            System.out.println("i =====================" + i);
        }
    }
