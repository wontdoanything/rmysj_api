package org.rmysj.api.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by rmysj on 16/5/10.
 * 任务调度触发器类
 */
public class ApplicationJob extends AbstractBaseJob{

   /* @Resource
    private ApplicationInfoService<ApplicationInfo> applicationInfoService;*/

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        try {
            //applicationInfoService.quartz_text();
            System.out.println("test 用例");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
