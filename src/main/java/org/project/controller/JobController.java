package org.project.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.project.common.logger.Logger;
import org.project.common.logger.Operation;
import org.project.common.quartz.entity.QuartzJob;
import org.project.common.quartz.service.JobService;
import org.project.common.response.Res;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/job")
@Api(value = "定时任务相关", tags = {"定时任务管理相关模块"})
public class JobController {

    @Resource
    private JobService jobService;

    @Logger(value = "添加定时任务", operation = Operation.CREATE)
    @ApiOperation(value = "添加定时任务", notes = "添加一个定时任务并将任务相关数据保存至数据库中, 且任务立即启动",
            tags = {"定时任务管理相关模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/add", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res save(@RequestBody QuartzJob job) {
        return jobService.saveJob(job) ? Res.ok() : Res.error();
    }

    @ApiOperation(value = "获取内置定时任务", notes = "查询系统自带的所有可用定时任务并返回至前端",
            tags = {"定时任务管理相关模块"}, response = Res.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/listJobs", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res listScheduledJobs() {
        return Res.ok()
                .data("jobs", jobService.listAllJobs());
    }

    @ApiOperation(value = "获取定时任务列表", notes = "根据前台的参数查询定时任务列表并返回",
            tags = {"定时任务管理相关模块"}, response = Res.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/list", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res list(@RequestParam(value = "jobName") String jobName,
                    @RequestParam(value = "cur") int cur,
                    @RequestParam(value = "size") int size) {
        PageInfo<QuartzJob> pageInfo = jobService.list(jobName, cur, size);
        return Res.ok()
                .data("total", pageInfo.getTotal())
                .data("list", pageInfo.getList());
    }

    @Logger(value = "启动定时任务", operation = Operation.NULL)
    @ApiOperation(value = "启动定时任务", notes = "启动参数指定的定时任务",
            tags = {"定时任务管理相关模块"}, response = Res.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/trigger", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res trigger(@RequestParam(value = "jobName") String jobName,
                       @RequestParam(value = "jobGroup") String jobGroup) {
        return jobService.triggerJob(jobName, jobGroup) ? Res.ok() : Res.error();
    }

    @Logger(value = "暂定定时任务", operation = Operation.NULL)
    @ApiOperation(value = "暂停定时任务", notes = "暂停参数指定的定时任务",
            tags = {"定时任务管理相关模块"}, response = Res.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/pause", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res pause(@RequestParam(value = "jobName") String jobName,
                     @RequestParam(value = "jobGroup") String jobGroup) {
        return jobService.pauseJob(jobName, jobGroup) ? Res.ok() : Res.error();
    }

    @Logger(value = "继续执行定时任务", operation = Operation.NULL)
    @ApiOperation(value = "继续指定定时任务", notes = "继续执行参数指定的定时任务",
            tags = {"定时任务管理相关模块"}, response = Res.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/resume", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res resume(@RequestParam(value = "jobName") String jobName,
                      @RequestParam(value = "jobGroup") String jobGroup) {
        return jobService.resumeJob(jobName, jobGroup) ? Res.ok() : Res.error();
    }

    @Logger(value = "移除定时任务", operation = Operation.DELETE)
    @ApiOperation(value = "移除定时任务", notes = "移除参数指定的定时任务",
            tags = {"定时任务管理相关模块"}, response = Res.class, httpMethod = "DELETE",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/remove", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res remove(@RequestParam(value = "jobName") String jobName,
                      @RequestParam(value = "jobGroup") String jobGroup) {
        return jobService.removeJob(jobName, jobGroup) ? Res.ok() : Res.error();
    }

}
