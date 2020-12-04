package org.project.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.project.common.logger.Logger;
import org.project.common.logger.Operation;
import org.project.common.response.Res;
import org.project.common.utils.Excels;
import org.project.entity.MenuEntity;
import org.project.entity.ViewObject.UserInfo;
import org.project.service.MenuService;
import org.project.service.impl.MenuServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/menu")
@Api(value = "菜单权限管理模块", tags = {"菜单权限信息管理模块"})
public class MenuController {

    @Resource
    private Excels excels;

    @Resource
    private MenuService menuService;

    @ApiOperation(value = "获取菜单树", notes = "查询所有的菜单目录并转换成菜单树的形式返回给前端展示在列表中",
            tags = {"菜单权限信息管理模块"}, response = Res.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getMenuTree", method = RequestMethod.GET, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public Res getMenuTree() {
        Res res = Res.ok();
        List<MenuEntity> all = menuService.getAll();
        res.data("tree", MenuServiceImpl.getMenuTree(all));
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setId(0);
        menuEntity.setMenuName("根菜单");
        menuEntity.setType(0);
        all.add(menuEntity);
        res.data("list", all.stream().filter(m -> m.getType() == 0).collect(Collectors.toList()));
        return res;
    }

    @ApiOperation(value = "获取角色菜单", notes = "根据角色id查询对应的菜单树以及所有菜单树并全部返回至前端",
            tags = {"菜单权限信息管理模块"}, response = Res.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getMenusByRoleId", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res getMenusByRoleId(@RequestParam(value = "id") int id) {
        return Res.ok()
                .data("tree", MenuServiceImpl.getMenuTree(MenuServiceImpl.removeUselessMenu(menuService.getAll())))
                .data("list", MenuServiceImpl.getLeaves(menuService.getMenuByRoleId(id)));
    }

    @ApiOperation(value = "查询用户菜单树", notes = "根据当前已登录用户的信息查询对应的菜单权限信息并将菜单转换成菜单树同时返回用户的权限簇",
            tags = {"菜单权限信息管理模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getMenus", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res getMenus(@RequestBody UserInfo userInfo) {
        Res ok = Res.ok();
        if (userInfo.isAdmin()) {
            List<MenuEntity> menuEntities = menuService.getAll();
            ok.data("menus", MenuServiceImpl.getMenuTree(menuEntities, false));
        } else {
            List<MenuEntity> menuEntities = menuService.getMenuByUserId(userInfo.getId());
            ok.data("menus", MenuServiceImpl.getMenuTree(menuEntities, false))
                    .data("perms", MenuServiceImpl.getPerms(menuEntities));
        }
        return ok;
    }

    @Logger(value = "添加菜单/按钮信息", operation = Operation.CREATE)
    @ApiOperation(value = "添加菜单/按钮信息", notes = "接受前端传递的JSON对象并存储在数据库中",
            tags = {"菜单权限信息管理模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/add", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res addMenu(@RequestBody MenuEntity menuEntity) {
        return menuService.insert(menuEntity) == 1 ? Res.ok() : Res.error();
    }

    @Logger(value = "修改菜单/按钮信息", operation = Operation.UPDATE)
    @ApiOperation(value = "修改菜单/按钮信息", notes = "接受前端传递的JSON对象并根据给定的id修改指定菜单记录并将结果保存在数据库中",
            tags = {"菜单权限信息管理模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/update", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res updateMenu(@RequestBody MenuEntity menuEntity) {
        return menuService.update(menuEntity) == 1 ? Res.ok() : Res.error();
    }

    @Logger(value = "删除菜单权限信息", operation = Operation.DELETE)
    @ApiOperation(value = "删除菜单权限信息", notes = "根据id删除指定的菜单权限记录",
            tags = {"菜单权限信息管理模块"}, response = Res.class, httpMethod = "DELETE",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res deleteMenu(@RequestParam(value = "id") int id) {
        return menuService.delete(id) == 1 ? Res.ok() : Res.error();
    }

    @Logger(value = "导出菜单权限信息", operation = Operation.DOWNLOAD)
    @ApiOperation(value = "导出菜单权限信息", notes = "导出全部菜单信息内容并在前端下载成xls文件",
            tags = {"菜单权限信息管理模块"}, response = void.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @RequestMapping(value = "/exportAsXls", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void exportMenusAsXls(HttpServletResponse response) {
        List<MenuEntity> list = menuService.getAll();
        String[][] fieldsAlias = MenuEntity.fieldsAlias;
        int rows = 0;

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("权限信息表");

        HSSFRow head = sheet.createRow(rows++);
        for (int i = 0; i < fieldsAlias.length; i++) {
            head.createCell(i)
                    .setCellValue(new HSSFRichTextString(fieldsAlias[i][1]));
        }

        for (MenuEntity menuEntity : list) {
            HSSFRow row = sheet.createRow(rows++);
            excels.flush(row, menuEntity, fieldsAlias);
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-disposition", "attachment;filename=menus.xls");
            response.flushBuffer();

            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Logger(value = "导出菜单权限信息", operation = Operation.DOWNLOAD)
    @ApiOperation(value = "导出菜单权限信息", notes = "导出全部菜单信息内容并在前端下载成Xlsx文件",
            tags = {"菜单权限信息管理模块"}, response = void.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @RequestMapping(value = "/exportAsXlsx", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void exportMenusAsXlsx(HttpServletResponse response) {
        List<MenuEntity> list = menuService.getAll();
        String[][] fieldsAlias = MenuEntity.fieldsAlias;
        int rows = 0;

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("权限信息表");

        XSSFRow head = sheet.createRow(rows++);
        for (int i = 0; i < fieldsAlias.length; i++) {
            head.createCell(i)
                    .setCellValue(new XSSFRichTextString(fieldsAlias[i][1]));
        }

        for (MenuEntity menuEntity : list) {
            XSSFRow row = sheet.createRow(rows++);
            excels.flush(row, menuEntity, fieldsAlias);
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-disposition", "attachment;filename=menus.xlsx");
            response.flushBuffer();

            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
