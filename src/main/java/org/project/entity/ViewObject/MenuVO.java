package org.project.entity.ViewObject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.entity.MenuEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "菜单实体类", description = "该实体类主要用于将系统的所有菜单选项展示在前端")
public class MenuVO {

    @ApiModelProperty(value = "菜单/按钮Id", required = true, dataType = "int", example = "1")
    private int id;

    @ApiModelProperty(value = "上级菜单id", required = true, dataType = "int", example = "0")
    private int parentId;

    @ApiModelProperty(value = "菜单/按钮名称", required = true, dataType = "String", example = "系统管理")
    private String menuName;

    @ApiModelProperty(value = "菜单URL", dataType = "String", example = "/use")
    private String url;

    @ApiModelProperty(value = "菜单权限标识", dataType = "String", example = "user")
    private String perms;

    @ApiModelProperty(value = "菜单/按钮图表", required = true, dataType = "String", example = "el-icon-coin")
    private String icon;

    @ApiModelProperty(value = "菜单/按钮类型", required = true, dataType = "int", example = "0")
    private int type;

    @ApiModelProperty(value = "菜单/按钮排序数字", required = true, dataType = "int", example = "1")
    private int orderNum;

    @ApiModelProperty(value = "当前菜单是否可用", required = true, dataType = "boolean", example = "false")
    private boolean disabled;

    @ApiModelProperty(value = "当前菜单的直接子节点", dataType = "MenuViewObject[]", example = "[]")
    private MenuVO[] children;

    public static MenuVO of(MenuEntity menuEntity) {
        MenuVO vo = new MenuVO();
        vo.setId(menuEntity.getId());
        vo.setParentId(menuEntity.getParentId());
        vo.setMenuName(menuEntity.getMenuName());
        vo.setUrl(menuEntity.getUrl());
        vo.setIcon(menuEntity.getIcon());
        vo.setOrderNum(menuEntity.getOrderNum());
        vo.setDisabled(menuEntity.getStatus() == 0);
        vo.setPerms(menuEntity.getPerms());
        vo.setType(menuEntity.getType());
        return vo;
    }

}
