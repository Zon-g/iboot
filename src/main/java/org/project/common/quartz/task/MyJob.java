package org.project.common.quartz.task;

import org.project.common.quartz.anotation.ScheduledJob;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;

/**
 * A simple demonstration for quartz. Anyone who wants to define a job, just extends
 * class <code>QuartzJobBean</code>.
 */
@ScheduledJob(value = "定时任务测试类")
public class MyJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 执行了定时任务...");
    }

}
