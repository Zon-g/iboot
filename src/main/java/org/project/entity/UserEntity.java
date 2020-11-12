package org.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户实体类", description = "该实体类对应数据库中用户信息表中的绝大多数字段, 不一定会用来返回至前端进行展示, 仅仅做一个映射或对应.")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -1333929004288321698L;

    @ApiModelProperty(value = "用户id", required = true, dataType = "int", example = "1")
    private int id;

    @ApiModelProperty(value = "用户名", required = true, dataType = "String", example = "zhang san")
    private String username;

    @ApiModelProperty(value = "密码", required = true, dataType = "String", example = "ZhangSan123")
    private String password;

    @ApiModelProperty(value = "加密盐", required = true, dataType = "String", example = "17236717823y81xad1")
    private String salt;

    @ApiModelProperty(value = "用户昵称", required = true, dataType = "String", example = "张三")
    private String nickname;

    @ApiModelProperty(value = "邮箱", required = true, dataType = "String", example = "ZSan@163.com")
    private String email;

    @ApiModelProperty(value = "头像", required = true, dataType = "String", example = "http://qiniu.com/123.png")
    private String avatar;

    @ApiModelProperty(value = "联系电话", required = true, dataType = "String", example = "13112134312")
    private String phone;

    @ApiModelProperty(value = "可用状态", required = true, dataType = "int", example = "1")
    private int status;

    @ApiModelProperty(value = "性别", required = true, dataType = "int", example = "1")
    private int gender;

    @ApiModelProperty(value = "用户类型", required = true, dataType = "int", example = "1")
    private int type;

    @ApiModelProperty(value = "出生日期", required = true, dataType = "Date", example = "2010-01-01")
    private Date birth;

    @ApiModelProperty(value = "用户信息创建时间", required = true, dataType = "Date", example = "2010-10-10 10:10:10")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "用户信息修改时间", required = true, dataType = "Date", example = "2010-10-10 10:10:10")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;

    @ApiModelProperty(value = "用户所在部门id", required = true, dataType = "int", example = "100")
    private int deptId;

}
