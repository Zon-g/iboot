package org.project.common.quartz.tast;

import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;

/**
 * A simple demonstration for quartz. Anyone who wants to define a job, just extends
 * class <code>QuartzJobBean</code>.
 */
public class MyJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 执行了定时任务...");
    }

}
