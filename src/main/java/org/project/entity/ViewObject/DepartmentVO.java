package org.project.entity.ViewObject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户所属部门基本信息实体类", description = "该实体类主要用户在用户模块查询条件中给出每个部门的具体人数.")
public class DepartmentVO {

    @ApiModelProperty(value = "部门id", required = true, dataType = "int", example = "10000")
    private int id;

    @ApiModelProperty(value = "部门名称", required = true, dataType = "String", example = "总部")
    private String name;

    @ApiModelProperty(value = "每个部门的总人数", required = true, dataType = "int", example = "10")
    private int total;

}
