package com.touyun.scheduler.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.touyun.scheduler.job.SchedulerMonitorJob;

/**    
 * <p>Copyright (c) Shanghai TY Technology Co., Ltd. All Rights Reserved.</p>
 *
 * @FileName: 	ContextListener.java    
 * @Description:ContextListener  
 * @author: 	Hoctor
 * @Creat: 		2015年8月10日  
 *
 * Modification History:
 * Data			Author		Version		   Description
 * -------------------------------------------------------------
 * 2015年8月10日		Hoctor		
 */

/**
 * @ClassName: ContextListener
 */
@WebListener
public class ContextListener implements ServletContextListener {

    private Scheduler localScheduler = null;

    /**
     * @Title: contextInitialized
     * @param sce
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            localScheduler = new StdSchedulerFactory("quartz.properties").getScheduler();
            JobDetail jobDetail = JobBuilder.newJob(SchedulerMonitorJob.class).withIdentity("SchedulerMonitor", "DefaultGroup")
                    .requestRecovery(true).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("SchedulerMonitor", "DefaultGroup")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?").withMisfireHandlingInstructionDoNothing())
                    .startNow().build();

            localScheduler.scheduleJob(jobDetail, trigger);
            if (!localScheduler.isStarted()) {
                localScheduler.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @Title: contextDestroyed
     * @param sce
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            localScheduler.clear();
            localScheduler.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
