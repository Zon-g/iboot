package org.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

@Builder(setterPrefix = "set", access = AccessLevel.PUBLIC)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "系统日志实体类", description = "该实体类对应于数据库中操作日志表的字段")
public class LoggerEntity {

    public static final String[][] fieldsAlias = {{"id", "操作日志id"}, {"user", "当前操作用户"},
            {"method", "当前操作方法"}, {"params", "当前操作方法参数"}, {"operation", "当前操作类型"},
            {"description", "当前操作描述"}, {"createTime", "当前记录创建时间"}};

    @ApiModelProperty(value = "操作日志id", required = true, dataType = "int", example = "1")
    private int id;

    @ApiModelProperty(value = "当前操作用户", required = true, dataType = "String", example = "Rose")
    private String user;

    @ApiModelProperty(value = "当前操作方法", required = true, dataType = "String", example = "GetPage")
    private String method;

    @ApiModelProperty(value = "当前操作方法的形参与实参", required = true, dataType = "int size = 10", example = "null")
    private String params;

    @ApiModelProperty(value = "当前操作类型", required = true, dataType = "String", example = "CREATE")
    private String operation;

    @ApiModelProperty(value = "当前操作描述", required = true, dataType = "String", example = "查询信息")
    private String description;

    @ApiModelProperty(value = "当前记录创建时间", required = true, dataType = "Date", example = "2020-01-02 03:04:05")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
