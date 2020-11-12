package org.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "部门信息实体类", description = "该实体类对应或映射数据库中的部门信息表的各字段并将映射结果返回至前端进项展示")
public class DepartmentEntity {

    public static final String[][] fieldsAlias = {{"id", "部门id"}, {"name", "部门名称"},
            {"phone", "部门电话"}, {"address", "部门地址"},
            {"createTime", "记录创建时间"}, {"modifyTime", "记录修改时间"}};

    @ApiModelProperty(value = "部门id", required = true, dataType = "int", example = "100")
    private int id;

    @ApiModelProperty(value = "部门名称", required = true, dataType = "String", example = "事业部")
    private String name;

    @ApiModelProperty(value = "部门电话", required = true, dataType = "String", example = "028-666666666")
    private String phone;

    @ApiModelProperty(value = "部门地址", required = true, dataType = "String", example = "朝阳南路1段1号")
    private String address;

    @ApiModelProperty(value = "记录创建时间", required = true, dataType = "Date", example = "2020-02-02 02:02:02")
    @JsonFormat(pattern = "yyyy年MM月dd日 HH时mm分ss秒", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "记录修改时间", required = true, dataType = "Date", example = "2020-02-02 02:02:02")
    @JsonFormat(pattern = "yyyy年MM月dd日 HH时mm分ss秒", timezone = "GMT+8")
    private Date modifyTime;

}
