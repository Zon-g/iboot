package org.project.entity.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户信息条件查询辅助实体类", description = "该类可辅助前端用户信息模块进行用户信息按照一个或者多个条件查询.")
public class UserCondition {

    @ApiModelProperty(value = "用户名", dataType = "String", example = "ZhangSan")
    private String username;

    @ApiModelProperty(value = "用户昵称", dataType = "String", example = "张山")
    private String nickname;

    @ApiModelProperty(value = "用户所在部门id", dataType = "int", example = "10000")
    private int department;

    @ApiModelProperty(value = "邮箱", dataType = "String", example = "10000@163.com")
    private String email;

    @ApiModelProperty(value = "性别", dataType = "int", example = "1")
    private int gender;

}
