package org.project.entity.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "角色信息条件查询辅助实体类", description = "该实体类用户辅助角色信息条件查询")
public class RoleCondition {

    @ApiModelProperty(value = "角色姓名", required = true, dataType = "String", example = "ZhangSan")
    private String name;

}
