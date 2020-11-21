package org.project.common.quartz.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "定时任务实体类", description = "定时任务信息实体类")
public class QuartzJob {

    @ApiModelProperty(value = "定时任务名称", dataType = "String", example = "test")
    private String jobName;

    @ApiModelProperty(value = "定时任务分组", dataType = "String", example = "test")
    private String jobGroup;

    @ApiModelProperty(value = "定时任务描述", dataType = "String", example = "这是一个测试定时任务")
    private String description;

    @ApiModelProperty(value = "定时任务类全限定名", dataType = "String", example = "org.project.MyJob")
    private String jobClassName;

    @ApiModelProperty(value = "定时任务执行时间的cron表达式", dataType = "String", example = "*/2 * * * * ?")
    private String cronExpression;

    @ApiModelProperty(value = "定时任务触发器名称", dataType = "String", example = "")
    private String triggerName;

    @ApiModelProperty(value = "定时任务触发器状态", dataType = "String", example = "ACQUIRED")
    private String triggerState;

    @ApiModelProperty(value = "定时任务原始名称", dataType = "String", example = "test")
    private String oldJobName;

    @ApiModelProperty(value = "定时任务原始分组", dataType = "String", example = "test")
    private String oldJobGroup;

    @ApiModelProperty(value = "定时任务参数", dataType = "List", example = "")
    private List<Map<String, Object>> jobDataParam;

}
