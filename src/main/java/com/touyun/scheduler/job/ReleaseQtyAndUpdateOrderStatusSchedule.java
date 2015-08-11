/**    
 * <p>Copyright (c) Shanghai TY Technology Co., Ltd. All Rights Reserved.</p>
 *
 * @FileName: 	ReleaseQtyAndUpdateOrderStatusSchedule.java    
 * @Description:ReleaseQtyAndUpdateOrderStatusSchedule  
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
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ReleaseQtyAndUpdateOrderStatusSchedule
 */
@Component
public class ReleaseQtyAndUpdateOrderStatusSchedule extends QuartzJobBean {

    private Log log = LogFactory.getLog(this.getClass());

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        log.info("dennis test execute schedule start ");
        try {
            // TODO
        } catch (Exception e) {
            e.printStackTrace();
            log.error("execute ReleaseQtyAndUpdateOrderStatus schedule error:" + e.getMessage());
        }
        log.info("dennis test exxcute schedule end");
    }
}
