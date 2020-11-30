package org.project.common.quartz.tast;

import org.project.common.email.MailUtils;
import org.project.entity.MailEntity;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;

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
