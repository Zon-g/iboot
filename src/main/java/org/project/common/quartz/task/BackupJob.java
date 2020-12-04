package org.project.common.quartz.task;

import org.project.common.quartz.anotation.ScheduledJob;
import org.project.common.utils.Backups;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;

@ScheduledJob(value = "数据库定时备份")
public class BackupJob extends QuartzJobBean {

    @Resource
    private Backups backups;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        backups.backup();
    }

}
