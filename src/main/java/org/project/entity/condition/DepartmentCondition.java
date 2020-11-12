package org.project.entity.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "部门信息条件查询辅助实体类", description = "该类用于辅助前端部门信息模块对部门信息按照一个或多个条件查询")
public class DepartmentCondition {

    @ApiModelProperty(value = "待查询部门名称, 支持模糊匹配", dataType = "String", example = "事业部")
    private String name;

    @ApiModelProperty(value = "待查询部门电话", dataType = "String", example = "028-66666666")
    private String phone;

    @ApiModelProperty(value = "待查询部门地址", dataType = "String", example = "朝阳路1号1单元")
    private String address;


}
