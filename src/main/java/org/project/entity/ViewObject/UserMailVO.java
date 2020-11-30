package org.project.entity.ViewObject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户信息视图实体类", description = "该实体类用于邮件管理模块发送邮件时向用户提供用户以及邮件列表")
public class UserMailVO {

    @ApiModelProperty(value = "用户昵称", required = true, dataType = "String", example = "张三")
    private String nickname;

    @ApiModelProperty(value = "用户邮箱", required = true, dataType = "String", example = "zs@163.com")
    private String email;

    @ApiModelProperty(value = "用户所在部门名称", required = true, dataType = "String", example = "事业部")
    private String deptName;

}
