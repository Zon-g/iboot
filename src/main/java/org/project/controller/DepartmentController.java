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
import org.project.entity.DepartmentEntity;
import org.project.entity.condition.DepartmentCondition;
import org.project.service.DepartmentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/department")
@Api(value = "用户部门管理模块相关", tags = {"部门信息管理模块"})
public class DepartmentController {

    @Resource
    private Excels excels;

    @Resource
    private DepartmentService departmentService;

    @ApiOperation(value = "获取各部门人数信息", tags = {"部门信息管理模块"}, notes = "获取各部门人数信息",
            httpMethod = "GET", response = Res.class, produces = "application/json")
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    public Res getList() {
        return Res.ok()
                .data("list", departmentService.getDeptList());
    }

    @ApiOperation(value = "获取部门信息列表", notes = "根据条件查询部门信息并返回值前端",
            tags = {"部门信息管理模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getPage", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res getPage(@RequestParam("cur") int cur, @RequestParam("size") int size,
                       @RequestBody DepartmentCondition condition) {
        PageHelper.startPage(cur, size);
        List<DepartmentEntity> list = departmentService.getPage(condition);
        PageInfo<DepartmentEntity> pageInfo = new PageInfo<>(list);
        return Res.ok()
                .data("total", pageInfo.getTotal())
                .data("list", list);
    }

    @Logger(value = "删除部门信息", operation = Operation.DELETE)
    @ApiOperation(value = "删除部门信息", notes = "根据前端传递的id删除对应的记录",
            tags = {"部门信息管理模块"}, response = Res.class, httpMethod = "DELETE",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res deleteById(@RequestParam("id") int id) {
        return departmentService.deleteById(id) == 1 ? Res.ok() : Res.error();
    }

    @Logger(value = "添加部门信息", operation = Operation.CREATE)
    @ApiOperation(value = "添加部门信息", notes = "接受前端传递的JSON对象并存储在数据库对应的表中",
            tags = {"部门信息管理模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/add", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res add(@RequestBody DepartmentEntity department) {
        return departmentService.insert(department) == 1 ? Res.ok() : Res.error();
    }

    @Logger(value = "修改部门信息", operation = Operation.UPDATE)
    @ApiOperation(value = "修改部门信息", notes = "根据部门id修改指定的部门记录信息并将修改结果保存在数据库中",
            tags = {"部门信息管理模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/update", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res update(@RequestBody DepartmentEntity department) {
        return departmentService.update(department) == 1 ? Res.ok() : Res.error();
    }

    @Logger(value = "导出部门信息", operation = Operation.DOWNLOAD)
    @ApiOperation(value = "导出部门信息", notes = "根据查询条件导出部门信息并返回至前端下载成xls文件",
            tags = {"部门信息管理模块"}, response = void.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @RequestMapping(value = "/exportAsXls", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadAsXls(@RequestBody DepartmentCondition condition, HttpServletResponse response) {
        List<DepartmentEntity> list = departmentService.getPage(condition);
        String[][] fieldsAlias = DepartmentEntity.fieldsAlias;
        int rowNum = 0;

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("部门信息表");

        HSSFRow header = sheet.createRow(rowNum++);
        for (int i = 0; i < fieldsAlias.length; i++) {
            header.createCell(i).setCellValue(new HSSFRichTextString(fieldsAlias[i][1]));
        }

        for (DepartmentEntity department : list) {
            HSSFRow row = sheet.createRow(rowNum++);
            excels.flush(row, department, fieldsAlias);
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-disposition", "attachment;filename=departments.xls");
            response.flushBuffer();

            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Logger(value = "导出部门信息", operation = Operation.DOWNLOAD)
    @ApiOperation(value = "导出部门信息", notes = "根据查询条件导出部门信息并返回至前端下载成xlsx文件",
            tags = {"部门信息管理模块"}, response = void.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @RequestMapping(value = "/exportAsXlsx", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadAsXlsx(@RequestBody DepartmentCondition condition, HttpServletResponse response) {
        List<DepartmentEntity> list = departmentService.getPage(condition);
        String[][] fieldsAlias = DepartmentEntity.fieldsAlias;
        int rowNum = 0;

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("部门信息表");

        XSSFRow header = sheet.createRow(rowNum++);
        for (int i = 0; i < fieldsAlias.length; i++) {
            header.createCell(i).setCellValue(new XSSFRichTextString(fieldsAlias[i][1]));
        }

        for (DepartmentEntity department : list) {
            XSSFRow row = sheet.createRow(rowNum++);
            excels.flush(row, department, fieldsAlias);
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-disposition", "attachment;filename=departments.xlsx");
            response.flushBuffer();

            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
