package org.project.common.quartz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.project.common.quartz.entity.QuartzJob;
import org.project.mapper.JobMapper;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobService {

    private static final String TRIGGER_IDENTITY = "trigger";

    private static final String PARAM_NAME = "paramName";

    private static final String PARAM_VALUE = "paramValue";

    private static final String SCHEDULER_INSTANCE_NAME = "schedulerInstanceName";

    @Value("${spring.quartz.properties.org.quartz.scheduler.instanceName}")
    private String schedulerInstanceName;

    @Resource
    private Scheduler scheduler;

    @Resource
    private JobMapper jobMapper;

    public PageInfo<QuartzJob> list(String jobName, int cur, int size) {
        PageHelper.startPage(cur, size);
        List<QuartzJob> list = jobMapper.list(jobName);
        fillJobData(list);
        return new PageInfo<>(list);
    }

    private void fillJobData(List<QuartzJob> list) {
        list.forEach(job -> {
            JobKey key = new JobKey(job.getJobName(), job.getJobGroup());
            try {
                JobDataMap jobDataMap = scheduler.getJobDetail(key).getJobDataMap();
                List<Map<String, Object>> jobDataParam = new ArrayList<>();
                jobDataMap.forEach((k, v) -> {
                    Map<String, Object> jobData = new LinkedHashMap<>(2);
                    jobData.put(PARAM_NAME, k);
                    jobData.put(PARAM_VALUE, v);
                    jobDataParam.add(jobData);
                });
                job.setJobDataParam(jobDataParam);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        });
    }

    @SuppressWarnings("all")
    public boolean saveJob(QuartzJob quartzJob) {
        try {
            if (quartzJob.getOldJobGroup() != null && !"".equals(quartzJob.getOldJobGroup())) {
                JobKey key = new JobKey(quartzJob.getOldJobName(), quartzJob.getOldJobGroup());
                scheduler.deleteJob(key);
            }
            Class clazz = Class.forName(quartzJob.getJobClassName());
            clazz.newInstance();
            JobDetail job = JobBuilder
                    .newJob(clazz)
                    .withIdentity(quartzJob.getJobName(), quartzJob.getJobGroup())
                    .withDescription(quartzJob.getDescription())
                    .build();
            putDataMap(job, quartzJob);

            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
                    .cronSchedule(quartzJob.getCronExpression().trim());
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(TRIGGER_IDENTITY + quartzJob.getJobName(), quartzJob.getJobGroup())
//                    .startNow()  // 此处注释掉, 避免定时任务创建即启动
                    .withSchedule(cronScheduleBuilder)
                    .build();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException | ClassNotFoundException |
                IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean triggerJob(String jobName, String jobGroup) {
        JobKey key = new JobKey(jobName, jobGroup);
        try {
            scheduler.triggerJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean pauseJob(String jobName, String jobGroup) {
        JobKey key = new JobKey(jobName, jobGroup);
        try {
            scheduler.pauseJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean resumeJob(String jobName, String jobGroup) {
        JobKey key = new JobKey(jobName, jobGroup);
        try {
            scheduler.resumeJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removeJob(String jobName, String jobGroup) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(TRIGGER_IDENTITY + jobName, jobGroup);
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void putDataMap(JobDetail jobDetail, QuartzJob quartzJob) {
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        jobDataMap.put(SCHEDULER_INSTANCE_NAME, schedulerInstanceName);

        List<Map<String, Object>> jobDataParam = quartzJob.getJobDataParam();
        if (jobDataParam == null || jobDataParam.isEmpty()) return;
        jobDataParam.forEach(map -> jobDataMap.put(String.valueOf(map.get(PARAM_NAME)), map.get(PARAM_VALUE)));
    }

}
