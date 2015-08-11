package com.touyun.scheduler.job;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.touyun.scheduler.model.SchedulerConfig;

public class SchedulerHelper {

    private static String matchPackagePath = SchedulerHelper.class.getClassLoader().getResource("").getPath();

    private static Log log = LogFactory.getLog(SchedulerHelper.class);

    private static String scanMatchingJobClass(File folder, String packageName, String matchJobName) {
        String clazzName = null;

        File[] files = folder.listFiles();
        for (int fileIndex = 0; fileIndex < files.length; fileIndex++) {
            File file = files[fileIndex];
            if (file.isDirectory()) {
                clazzName = scanMatchingJobClass(file, packageName + file.getName() + ".", matchJobName);
                if (clazzName != null) {
                    return clazzName;
                }
            } else {
                if (file.getName().endsWith(matchJobName + ".class")) {

                    clazzName = packageName + file.getName();
                    clazzName = clazzName.substring(clazzName.indexOf("com"), clazzName.lastIndexOf(".class"));
                    packageName = folder.getAbsolutePath();
                    return clazzName;
                }
            }
        }

        return clazzName;
    }

    public static void addJob(Scheduler scheduler, SchedulerConfig schedulerConfig) throws SchedulerException,
            ClassNotFoundException {
        File folder = new File(matchPackagePath);
        String clazzName = scanMatchingJobClass(folder, "", schedulerConfig.getJobClass());
        if (clazzName == null) {
            matchPackagePath = SchedulerHelper.class.getClassLoader().getResource("").getPath();
            folder = new File(matchPackagePath);
            clazzName = scanMatchingJobClass(folder, "", schedulerConfig.getJobClass());
        }

        if (StringUtils.isNotBlank(clazzName)) {
            Class<? extends Job> clazz = (Class<? extends Job>) Class.forName(clazzName);
            JobDetail jobDetail = JobBuilder.newJob(clazz)
                    .withIdentity(schedulerConfig.getJobName(), schedulerConfig.getGroupName()).requestRecovery(true).build();
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(schedulerConfig.getJobName(), schedulerConfig.getGroupName())
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule(schedulerConfig.getJobTrigger())
                                    .withMisfireHandlingInstructionDoNothing()).startNow().build();

            scheduler.scheduleJob(jobDetail, trigger);

        } else {
            log.error("can't find class with name " + schedulerConfig.getJobClass());
        }
    }
}
