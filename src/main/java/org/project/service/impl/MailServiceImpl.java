package org.project.service.impl;

import com.github.Generator;
import org.project.common.email.MailUtils;
import org.project.entity.MailEntity;
import org.project.entity.UserEntity;
import org.project.entity.condition.MailCondition;
import org.project.mapper.MailMapper;
import org.project.service.MailService;
import org.project.service.UserService;
import org.quartz.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class MailServiceImpl implements MailService {

    @Resource
    private MailMapper mailMapper;

    @Resource
    private Generator generator;

    @Resource
    private UserService userService;

    @Resource
    private MailUtils mailUtils;

    @Resource
    private Scheduler scheduler;

    @Override
    public List<MailEntity> findPage(MailCondition condition) {
        return mailMapper.findPage(condition);
    }

    @SuppressWarnings("all")
    @Override
    public int add(MailEntity entity, String... attachments) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.getUserByName(Optional.of(((String) authentication.getPrincipal())).orElse(null));
        entity.setFromName(user.getNickname());
        entity.setFrom(user.getEmail());
        String to = entity.getTo(), toName = entity.getToName();
        String[] tos = to.split(","), toNames = toName.split(",");
        int cnt = 0;
        for (int i = 0; i < tos.length; i++) {
            entity.setId(generator.nextId());
            entity.setTo(tos[i]);
            entity.setToName(toNames[i]);
            cnt += mailMapper.add(entity);
            if (entity.getScheduled() == 0) {
                mailUtils.send(entity, attachments);
            } else {
                try {
                    Class clazz = Class.forName("org.project.common.quartz.task.EmailJob");
                    JobDetail jobDetail = JobBuilder
                            .newJob(clazz)
                            .withIdentity("定时邮件", "定时任务")
                            .withDescription("一封定时发送的邮件")
                            .build();

                    jobDetail.getJobDataMap().put("entity", entity);
                    jobDetail.getJobDataMap().put("attachments", attachments);

                    CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
                            .cronSchedule(entity.getScheduleTime());
                    Trigger trigger = TriggerBuilder
                            .newTrigger()
                            .withIdentity("定时邮件触发器", "定时任务触发器")
                            .withSchedule(cronScheduleBuilder)
                            .build();
                    scheduler.scheduleJob(jobDetail, trigger);
                } catch (ClassNotFoundException | SchedulerException e) {
                    e.printStackTrace();
                }
            }
        }
        return cnt;
    }

    @Override
    public int delete(long id) {
        return mailMapper.delete(id);
    }

}
