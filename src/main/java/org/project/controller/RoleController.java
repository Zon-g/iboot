package org.project.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import org.project.entity.RoleEntity;
import org.project.entity.condition.RoleCondition;
import org.project.service.MenuService;
import org.project.service.RoleService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/role")
@Api(value = "角色管理模块", tags = {"角色信息管理模块"})
public class RoleController {

    @Resource
    private Excels excels;

    @Resource
    private RoleService roleService;

    @Resource
    private MenuService menuService;

    @ApiOperation(value = "查询角色信息", notes = "根据条件分页查询角色信息",
            tags = {"角色信息管理模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getPage", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res getList(@RequestParam(required = false, defaultValue = "1") int cur,
                       @RequestParam(required = false, defaultValue = "5") int size,
                       @RequestBody RoleCondition condition) {
        PageHelper.startPage(cur, size);
        List<RoleEntity> list = roleService.getRoles(condition);
        PageInfo<RoleEntity> pageInfo = new PageInfo<>(list);
        return Res.ok()
                .data("total", pageInfo.getTotal())
                .data("list", list);
    }

    @Logger(value = "修改角色权限", operation = Operation.UPDATE)
    @ApiOperation(value = "修改角色权限", notes = "根据角色id以及选中的菜单列表来更新当前指定角色的权限簇",
            tags = {"角色信息管理模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/updateMenusByRoleId", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res updateMenusByRoleId(@RequestParam(value = "id") int id,
                                   @RequestBody int[] menus) {
        List<Integer> oldMenus = menuService.getRoleMenu(id),
                newMenus = Arrays.stream(menus)
                        .boxed()
                        .collect(Collectors.toList()),
                oldDiffNew = oldMenus.stream()
                        .filter(num -> !newMenus.contains(num))
                        .collect(Collectors.toList()),
                newDiffOld = newMenus.stream()
                        .filter(num -> !oldMenus.contains(num))
                        .collect(Collectors.toList());
        if (oldDiffNew.size() > 0) menuService.deleteOldMenus(id, oldDiffNew);
        if (newDiffOld.size() > 0) menuService.addNewMenus(id, newDiffOld);
        return Res.ok();
    }

    @Logger(value = "新增角色信息", operation = Operation.CREATE)
    @ApiOperation(value = "新增角色信息", notes = "接受前端传递的JSON对象并存储在数据库对应的表中",
            tags = {"角色信息管理模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/add", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res add(@RequestBody RoleEntity roleEntity) {
        return roleService.insert(roleEntity) == 1 ? Res.ok() : Res.error();
    }

    @Logger(value = "修改角色信息", operation = Operation.UPDATE)
    @ApiOperation(value = "修改角色信息", notes = "根据角色id修改指定的角色记录信息并将修改结果保存在数据库中",
            tags = {"角色信息管理模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/update", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res update(@RequestBody RoleEntity roleEntity) {
        return roleService.update(roleEntity) == 1 ? Res.ok() : Res.error();
    }

    @Logger(value = "删除角色信息", operation = Operation.DELETE)
    @ApiOperation(value = "删除角色信息", notes = "根据前端传递的id删除对应的角色记录",
            tags = {"角色信息管理模块"}, response = Res.class, httpMethod = "DELETE",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "delete", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res delete(@RequestParam("id") int id) {
        return roleService.delete(id) == 1 ? Res.ok() : Res.error();
    }

    @Logger(value = "导出角色信息", operation = Operation.DOWNLOAD)
    @ApiOperation(value = "导出角色信息", notes = "根据查询条件导出角色信息并返回至前端下载成Xls文件",
            tags = {"角色信息管理模块"}, response = void.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @RequestMapping(value = "/exportAsXls", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadAsXls(@RequestBody RoleCondition condition, HttpServletResponse response) {
        List<RoleEntity> list = roleService.getRoles(condition);
        String[][] fieldsAlias = RoleEntity.fieldsAlias;
        int rows = 0;

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("角色信息表");

        HSSFRow header = sheet.createRow(rows++);
        for (int i = 0; i < fieldsAlias.length; i++) {
            header.createCell(i).setCellValue(new HSSFRichTextString(fieldsAlias[i][1]));
        }

        for (RoleEntity roleEntity : list) {
            HSSFRow row = sheet.createRow(rows++);
            excels.flush(row, roleEntity, fieldsAlias);
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-disposition", "attachment;filename=roles.xls");
            response.flushBuffer();

            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Logger(value = "导出角色信息", operation = Operation.DOWNLOAD)
    @ApiOperation(value = "导出角色信息", notes = "根据查询条件导出角色信息并返回至前端下载成Xlsx文件",
            tags = {"角色信息管理模块"}, response = void.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @RequestMapping(value = "/exportAsXlsx", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadAsXlsx(@RequestBody RoleCondition condition, HttpServletResponse response) {
        List<RoleEntity> list = roleService.getRoles(condition);
        String[][] fieldsAlias = RoleEntity.fieldsAlias;
        int rows = 0;

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("角色信息表");

        XSSFRow head = sheet.createRow(rows++);
        for (int i = 0; i < fieldsAlias.length; i++) {
            head.createCell(i).setCellValue(new XSSFRichTextString(fieldsAlias[i][1]));
        }

        for (RoleEntity roleEntity : list) {
            XSSFRow row = sheet.createRow(rows++);
            excels.flush(row, roleEntity, fieldsAlias);
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-disposition", "attachment;filename=roles.xlsx");
            response.flushBuffer();

            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
