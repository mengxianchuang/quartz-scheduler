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

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.Scheduler;

import com.touyun.scheduler.dao.SchedulerConfigDao;
import com.touyun.scheduler.model.SchedulerConfig;
import com.touyun.scheduler.util.SpringContextUtil;

/**
 * @ClassName: SchedulerMonitorJob
 */

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SchedulerMonitorJob implements Job {

    private static Scheduler quartzScheduler = (Scheduler) SpringContextUtil.getBean("quartzScheduler");

    private static SchedulerConfigDao schedulerConfigDao = (SchedulerConfigDao) SpringContextUtil.getBean("schedulerConfigDao");

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * @Title: execute
     * @param context
     * @throws JobExecutionException
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("SchedulerMonitorJob start ");
        try {
            // TODO 该Job应该是非cluster

            // 1.将SchedulerConfig放入Redis缓存
            // 1.查询 scheduler_config表,放入List内
            // 2.将查询到的每条数据与redis中相应的记录对比
            // 2.1 如果job是新增，根据job类型是否是cluster类型的，将新的job加入到对应的quartz scheduler内
            // 2.2 如果job有修改，根据job类型(Cluster)做相应的修改
            // 2.3 如果删除，根据job类型(Cluster)，删除job
            // 3 将新增，修改，删除的Job记录在Redis内做相应的调整
            List<SchedulerConfig> schedulerConfigs = schedulerConfigDao.findAll();
            for (SchedulerConfig schedulerConfig : schedulerConfigs) {
                if (schedulerConfig.getStatus() == 0) {
                    SchedulerHelper.addJob(quartzScheduler, schedulerConfig);
                    schedulerConfigDao.updateStatus(schedulerConfig);
                    log.info("add new job successful.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("SchedulerMonitorJob error:" + e.getMessage());
        }
        log.info("SchedulerMonitorJob end");

    }

}
