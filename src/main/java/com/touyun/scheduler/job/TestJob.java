/**    
 * <p>Copyright (c) Shanghai TY Technology Co., Ltd. All Rights Reserved.</p>
 *
 * @FileName: 	SchedulerMonitorJob.java    
 * @Description:SchedulerMonitorJob  
 * @author: 	Hoctor
 * @Creat: 		2015年8月7日  
 *
 * Modification History:
 * Data			Author		Version		   Description
 * -------------------------------------------------------------
 * 2015年8月7日		Hoctor		
 */
package com.touyun.scheduler.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @ClassName: SchedulerMonitorJob
 */

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class TestJob extends QuartzJobBean {

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * @Title: executeInternal
     * @param context
     * @throws JobExecutionException
     * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("hoctor test execute schedule start ");
        try {
            // TODO
            // JobDetail jobDetail = new
            // context.getScheduler().
        } catch (Exception e) {
            e.printStackTrace();
            log.error("execute ReleaseQtyAndUpdateOrderStatus schedule error:" + e.getMessage());
        }
        log.info("hoctor test exxcute schedule end");

    }

}
