package org.project.entity.ViewObject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.common.jwt.entity.JwtUser;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户基本信息实体类", description = "该类用于在用户登录时将用户对应的相关权限返回给前端")
public class UserInfo {

    @ApiModelProperty(value = "用户id", required = true, dataType = "int", example = "1")
    private int id;

    @ApiModelProperty(value = "用户名称", required = true, dataType = "String", example = "ZhangSan")
    private String username;

    @ApiModelProperty(value = "当前用户是否是超级管理员", required = true, dataType = "boolean", example = "false")
    private boolean isAdmin;

    @ApiModelProperty(value = "当前用户头像地址", required = true, dataType = "String", example = "http://www.abc.com/112.jpg")
    private String avatar;

    public static UserInfo of(JwtUser user) {
        UserInfo userInfo = new UserInfo(user.getId(), user.getUsername(), user.isAdmin(), user.getAvatar());
        return userInfo;
    }

}
