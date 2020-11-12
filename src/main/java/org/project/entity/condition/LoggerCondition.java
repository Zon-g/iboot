package org.project.entity.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "操作日志条件查询辅助实体类", description = "该实体类可辅助前端操作日志模块便于用于根据条件查询相关操作日志")
public class LoggerCondition {

    @ApiModelProperty(value = "用户名", dataType = "String", example = "ZhangSan")
    private String username;

    @ApiModelProperty(value = "操作类型", dataType = "String", example = "CREATE")
    private String operation;

    @ApiModelProperty(value = "开始日期", dataType = "Date", example = "2020-02-03")
    private Date startTime;

    @ApiModelProperty(value = "结束日期", dataType = "Date", example = "2020-02-02")
    private Date endTime;

}
