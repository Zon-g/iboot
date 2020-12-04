package org.project.common.quartz.task;

import org.project.common.email.MailUtils;
import org.project.common.quartz.anotation.ScheduledJob;
import org.project.entity.MailEntity;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;

@ScheduledJob(value = "定时邮件", ignoreClass = true)
public class EmailJob extends QuartzJobBean {

    @Resource
    private MailUtils mailUtils;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        MailEntity entity = ((MailEntity) jobDataMap.get("entity"));
        String[] attachments = ((String[]) jobDataMap.get("attachments"));
        mailUtils.send(entity, attachments);
    }

}
