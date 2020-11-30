package org.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "邮件信息实体类", description = "该实体类对应于数据库中记录邮件发送的信息")
public class MailEntity implements Serializable {

    public static final String[][] fieldsAlias = {{"id", "邮件信息记录id"}, {"fromName", "邮件发件人姓名"},
            {"from", "邮件发件人邮箱"}, {"toName", "邮件收件人姓名"}, {"to", "邮件收件人邮箱"},
            {"subject", "邮件主题"}, {"text", "邮件内容"}, {"scheduled", "是否为定时任务"},
            {"scheduleTime", "定时任务时间"}, {"createTime", "邮件记录创建时间"}};

    private static final long serialVersionUID = 2323105162223276512L;

    @ApiModelProperty(value = "邮件信息记录id", required = true, dataType = "long", example = "1L")
    private long id;

    @ApiModelProperty(value = "邮件发件人姓名", required = true, dataType = "String", example = "张三")
    private String fromName;

    @ApiModelProperty(value = "邮件发件人邮箱", required = true, dataType = "String", example = "zs@163.com")
    private String from;

    @ApiModelProperty(value = "邮件收件人姓名", required = true, dataType = "String", example = "李四")
    private String toName;

    @ApiModelProperty(value = "邮件收件人邮箱", required = true, dataType = "String", example = "ls@163.com")
    private String to;

    @ApiModelProperty(value = "邮件主题", required = true, dataType = "String", example = "简单邮件")
    private String subject;

    @ApiModelProperty(value = "邮件内容", required = true, dataType = "String", example = "简单邮件内容")
    private String text;

    @ApiModelProperty(value = "是否为定时任务", dataType = "int", example = "0")
    private int scheduled;

    @ApiModelProperty(value = "定时任务时间", dataType = "String", example = "")
    private String scheduleTime;

    @ApiModelProperty(value = "邮件记录创建时间", required = true, dataType = "Date", example = "2020-01-02 03:04:05")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
