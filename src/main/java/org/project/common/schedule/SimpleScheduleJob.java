package org.project.common.schedule;

import org.project.common.async.SimpleAsyncJob;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@EnableScheduling
@Service
public class SimpleScheduleJob {

    @Resource
    private SimpleAsyncJob simpleAsyncJob;

    /**
     * A simple demonstration to invoke scheduled job using default scheme in <code>Spring</code>.
     * <code>@Scheduled</code> is used to each scheduled job unless <code>@EnableScheduling</code>
     * is used to enable <code>Spring Scheduling Job</code>.
     */
    @Scheduled(cron = "23 23 * * * ?")
    public void job() {
        System.out.println(LocalDateTime.now() + " -> " + Thread.currentThread().getName() + " 即将调用异步任务");
        simpleAsyncJob.job();
        System.out.println(LocalDateTime.now() + " -> " + Thread.currentThread().getName() + " 调用异步任务完成");
    }

}
