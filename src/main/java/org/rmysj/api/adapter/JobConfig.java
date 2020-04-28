package org.rmysj.api.adapter;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.rmysj.api.schedule.ApplicationJob;
import org.rmysj.api.schedule.AutowiringSpringBeanJobFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;


/**
 *
 * spring管理的bean
 */

@Configuration
@EnableScheduling
@ContextConfiguration
@WebAppConfiguration
@EnableTransactionManagement
@EnableAutoConfiguration
public class JobConfig extends WebAppConfig {
    /**
     * 配置拦截器
     *
     */
//  public void addInterceptors(InterceptorRegistry registry) {
//      registry.addInterceptor(new UserSecurityInterceptor()).addPathPatterns(
//              "/**");
//  }

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }



    /**创建任务工厂BEAN
     * @param jobClass
     * @author
     * @return
     */
    private static JobDetailFactoryBean createJobDetail(Class jobClass) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        factoryBean.setDurability(true);
        return factoryBean;
    }


    /**创建定时触发器工厂bean
     * @param jobDetail
     * @param pollFrequencyMs
     * @author
     * @return
     */
    private static CronTriggerFactoryBean createTrigger(JobDetail jobDetail, String pollFrequencyMs) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(0L);
        factoryBean.setCronExpression (pollFrequencyMs);//每5秒执行一次
        return factoryBean;
    }

    /**
     * 创建调度工厂bean
     *  @param jobFactory
     * @param cronJobTrigger
     */
    private static SchedulerFactoryBean createSchedulerFactoryBean(JobFactory jobFactory,
                                                                   Trigger cronJobTrigger){
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        // this allows to update triggers in DB when updating settings in config file:
        //用于quartz集群,QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        factory.setOverwriteExistingJobs(true);
        //用于quartz集群,加载quartz数据源
//          factory.setDataSource(dataSource);
        factory.setJobFactory(jobFactory);
        //QuartzScheduler 延时启动，应用启动完20秒后 QuartzScheduler 再启动
        factory.setStartupDelay(2);
        //用于quartz集群,加载quartz数据源配置
//          factory.setQuartzProperties(quartzProperties());
        //注册触发器
        factory.setTriggers(cronJobTrigger);

        return factory;
    }

    /**加载quartz数据源配置,quartz集群时用到
     * @return
     * @author
     * @throws IOException
     */
//      @Bean
//      public Properties quartzProperties() throws IOException {
//          PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
//          propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
//          propertiesFactoryBean.afterPropertiesSet();
//          return propertiesFactoryBean.getObject();
//      }

    /***************START 简单用例******************/

    /**调度工厂bean
     * @param jobFactory
     * @param cronJobTrigger
     * @return
     * @author
     * @throws IOException
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory,
                                                     @Qualifier("cronJobTrigger") Trigger cronJobTrigger) throws IOException {
        return createSchedulerFactoryBean(jobFactory,cronJobTrigger);
    }


    /**加载触发器
     * @author
     * @return
     */
    @Bean
    public JobDetailFactoryBean sampleJobDetail() {
        return createJobDetail(ApplicationJob.class);
    }



    /**加载定时器
     * @param jobDetail
     * @param frequency
     * @author
     * @return
     */
    @Bean(name = "cronJobTrigger")
    public CronTriggerFactoryBean sampleJobTrigger(@Qualifier("sampleJobDetail") JobDetail jobDetail,
                                                   @Value("${samplejob.frequency}") String frequency) {
        return createTrigger(jobDetail, frequency);
    }



    /***************END 简单用例******************/

}
