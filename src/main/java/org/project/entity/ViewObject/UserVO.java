package org.project.entity.ViewObject;

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
@ApiModel(value = "用户信息类", description = "该类用于将查询的用户信息返回值前端展示.")
public class UserVO {

    public static final String[][] fieldsAlias = {{"id", "用户id"}, {"username", "用户名称"},
            {"nickname", "用户昵称"}, {"department", "用户所属部门名称"}, {"email", "用户邮箱"},
            {"phone", "用户联系电话"}, {"gender", "性别"}, {"status", "用户当前状态"},
            {"birth", "出生日期"}, {"createTime", "创建时间"}, {"modifyTime", "修改时间"}};

    @ApiModelProperty(value = "用户id", required = true, dataType = "int", example = "1")
    private int id;

    @ApiModelProperty(value = "用户名称", required = true, dataType = "String", example = "ZhangSan")
    private String username;

    @ApiModelProperty(value = "用户昵称", required = true, dataType = "String", example = "张三")
    private String nickname;

    @ApiModelProperty(value = "用户所属部门名称", required = true, dataType = "String", example = "新零售事业部")
    private String department;

    @ApiModelProperty(value = "用户邮箱", required = true, dataType = "String", example = "ZhangSan@126.com")
    private String email;

    @ApiModelProperty(value = "用户联系电话", required = true, dataType = "String", example = "13110912123")
    private String phone;

    @ApiModelProperty(value = "用户性别, -1表示保密, 0表示女, 1表示男", required = true, dataType = "int", example = "1")
    private int gender;

    @ApiModelProperty(value = "用户状态, 0表示禁用, 1表示可用", required = true, dataType = "int", example = "1")
    private int status;

    @ApiModelProperty(value = "用户出生日期", required = true, dataType = "Date", example = "2020-02-02")
    @JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
    private Date birth;

    @ApiModelProperty(value = "当前用户信息创建时间", required = true, dataType = "Date", example = "2020-02-02 02:02:02")
    @JsonFormat(pattern = "yyyy年MM月dd日 HH时mm分ss秒", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "当前用户信息修改时间", required = true, dataType = "Date", example = "2020-02-02 02:02:02")
    @JsonFormat(pattern = "yyyy年MM月dd日 HH时mm分ss秒", timezone = "GMT+8")
    private Date modifyTime;

}
