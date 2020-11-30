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
@ApiModel(value = "邮件记录条件查询辅助实体类", description = "该实体类可辅助前端邮件信息模块完成条件查询")
public class MailCondition {

    @ApiModelProperty(value = "发件人姓名", dataType = "String", example = "张三")
    private String fromName;

    @ApiModelProperty(value = "收件人姓名", dataType = "String", example = "李四")
    private String toName;

    @ApiModelProperty(value = "开始日期", dataType = "Date", example = "2020-03-04")
    private Date startTime;

    @ApiModelProperty(value = "结束日期", dataType = "Date", example = "2020-04-05")
    private Date endTime;

}
