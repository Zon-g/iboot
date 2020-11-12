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
@ApiModel(value = "菜单权限实体类", description = "该实体类主要映射数据库中菜单权限表的各字段的值")
public class MenuEntity {

    public static final String[][] fieldsAlias = {{"id", "菜单/按钮id"}, {"parentId", "上级菜单id"},
            {"menuName", "菜单/按钮名称"}, {"url", "菜单url"}, {"perms", "菜单权限标识"},
            {"icon", "菜单/按钮图标"}, {"type", "菜单/按钮类型"}, {"orderNum", "菜单/按钮排序数字"},
            {"createTime", "菜单记录创建时间"}, {"modifyTime", "菜单记录修改时间"}, {"status", "当前菜单记录状态"}};

    @ApiModelProperty(value = "菜单/按钮Id", required = true, dataType = "int", example = "1")
    private int id;

    @ApiModelProperty(value = "上级菜单id", required = true, dataType = "int", example = "0")
    private int parentId;

    @ApiModelProperty(value = "菜单/按钮名称", required = true, dataType = "String", example = "系统管理")
    private String menuName;

    @ApiModelProperty(value = "菜单URL", dataType = "String", example = "/user")
    private String url;

    @ApiModelProperty(value = "菜单权限标识", dataType = "String", example = "user")
    private String perms;

    @ApiModelProperty(value = "菜单/按钮图标", required = true, dataType = "String", example = "el-icon-coin")
    private String icon;

    @ApiModelProperty(value = "菜单/按钮类型", required = true, dataType = "int", example = "0")
    private int type;

    @ApiModelProperty(value = "菜单/按钮排序数字", required = true, dataType = "int", example = "1")
    private int orderNum;

    @ApiModelProperty(value = "菜单记录创建时间", required = true, dataType = "Date", example = "2020-01-01 01:02:03")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "菜单记录修改时间", required = true, dataType = "Date", example = "2020-02-02 04:05:06")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;

    @ApiModelProperty(value = "当前菜单记录状态", required = true, dataType = "int", example = "1")
    private int status;

}
