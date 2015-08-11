/**    
 * <p>Copyright (c) Shanghai TY Technology Co., Ltd. All Rights Reserved.</p>
 *
 * @FileName: 	PersistableCronTriggerFactoryBean.java    
 * @Description:PersistableCronTriggerFactoryBean  
 * @author: 	Hoctor
 * @Creat: 		2015年8月7日  
 *
 * Modification History:
 * Data			Author		Version		   Description
 * -------------------------------------------------------------
 * 2015年8月7日		Hoctor		
 */
package com.touyun.scheduler.job;

import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailAwareTrigger;

/**
 * Needed to set Quartz useProperties=true when using Spring classes, because
 * Spring sets an object reference on JobDataMap that is not a String
 * 
 * @see http
 *      ://site.trimplement.com/using-spring-and-quartz-with-jobstore-properties
 *      /
 * @see http 
 *      ://forum.springsource.org/showthread.php?130984-Quartz-error-IOException
 */
public class PersistableCronTriggerFactoryBean extends CronTriggerFactoryBean {
    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        System.out.println("PersistableCronTriggerFactoryBean-------------------");
        // Remove the JobDetail element
        getJobDataMap().remove(JobDetailAwareTrigger.JOB_DETAIL_KEY);
    }
}
