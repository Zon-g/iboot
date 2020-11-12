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
@ApiModel(value = "角色实体类", description = "给实体类主要映射数据库中角色表的各字段")
public class RoleEntity {

    public static final String[][] fieldsAlias = {{"id", "角色id"}, {"name", "角色名称"}, {"remark", "角色描述"},
            {"createTime", "角色记录创建时间"}, {"modifyTime", "角色记录修改时间"}, {"status", "角色当前状态"}};

    @ApiModelProperty(value = "角色id", required = true, dataType = "int", example = "1")
    private int id;

    @ApiModelProperty(value = "角色名称", required = true, dataType = "String", example = "测试人员")
    private String name;

    @ApiModelProperty(value = "角色描述", required = true, dataType = "String", example = "测试人员")
    private String remark;

    @ApiModelProperty(value = "角色记录创建时间", required = true, dataType = "Date", example = "2020-02-02 02:03:04")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "角色记录修改时间", required = true, dataType = "Date", example = "2020-03-04 05:06:07")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;

    @ApiModelProperty(value = "角色记录当前状态", required = true, dataType = "int", example = "1")
    private int status;

}
