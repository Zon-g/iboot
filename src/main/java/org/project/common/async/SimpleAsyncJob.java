package org.project.common.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@EnableAsync
@Service
public class SimpleAsyncJob {

    /**
     * This is a simple demonstration for <code>Asynchronized Job</code>. <code>@Async</code>
     * should be used for each asynchronized job as below unless <code>@EnableAsync</code> is
     * used to enable function of <code>Spring</code>.
     */
    @Async
    public void job() {
        try {
            System.out.println(LocalDateTime.now() + " -> " + Thread.currentThread().getName() + " 正在执行异步任务...");
            Thread.sleep(5000);
            System.out.println(LocalDateTime.now() + " -> " + Thread.currentThread().getName() + " 异步任务完成执行...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
