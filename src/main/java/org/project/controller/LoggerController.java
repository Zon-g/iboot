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
import org.project.entity.LoggerEntity;
import org.project.entity.condition.LoggerCondition;
import org.project.service.LoggerService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/log")
@Api(value = "操作日志相关", tags = {"操作日志相关模块"})
public class LoggerController {

    @Resource
    private Excels excels;

    @Resource
    private LoggerService loggerService;

    @ApiOperation(value = "查询操作日志", notes = "根据前端的查询条件查询操作日志并返回",
            tags = {"操作日志相关模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getPage", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res getPage(@RequestParam(value = "cur") int cur,
                       @RequestParam(value = "size") int size,
                       @RequestBody LoggerCondition condition) {
        PageHelper.startPage(cur, size);
        List<LoggerEntity> loggers = loggerService.getPage(condition);
        PageInfo<LoggerEntity> pageInfo = new PageInfo<>(loggers);
        return Res.ok()
                .data("list", loggers)
                .data("total", pageInfo.getTotal());
    }

    @ApiOperation(value = "查询系统操作日志类型", notes = "查询系统操作日志所有类型并返回",
            tags = {"操作日志相关模块"}, response = Res.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getOperations", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res getOperations() {
        List<String> list = Arrays.stream(Operation.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return Res.ok()
                .data("list", list);
    }

    @Logger(value = "清空所有日志", operation = Operation.DELETE)
    @ApiOperation(value = "清空所有操作日志", notes = "无条件清空所有操作日志",
            tags = {"操作日志相关模块"}, response = Res.class, httpMethod = "DELETE",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/clearAll", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res DeleteAll() {
        loggerService.deleteAll();
        return Res.ok();
    }

    @Logger(value = "导出操作日志", operation = Operation.DOWNLOAD)
    @ApiOperation(value = "导出操作日志", notes = "根据查询条件导出满足当前条件的所有操作日志并保存为.xls文件",
            tags = {"操作日志相关模块"}, response = void.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @RequestMapping(value = "/exportAsXLS", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void exportAsXls(@RequestBody LoggerCondition condition, HttpServletResponse response) {
        List<LoggerEntity> loggers = loggerService.getPage(condition);
        String[][] fieldsAlias = LoggerEntity.fieldsAlias;
        int rows = 0;

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("操作日志记录表");

        HSSFRow head = sheet.createRow(rows++);
        for (int i = 0; i < fieldsAlias.length; i++) {
            head.createCell(i).setCellValue(new HSSFRichTextString(fieldsAlias[i][1]));
        }

        for (LoggerEntity logger : loggers) {
            HSSFRow row = sheet.createRow(rows++);
            excels.flush(row, logger, fieldsAlias);
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-disposition", "attachment;filename=logger.xls");
            response.flushBuffer();

            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Logger(value = "导出操作日志", operation = Operation.DOWNLOAD)
    @ApiOperation(value = "导出操作日志", notes = "根据查询条件导出满足当前条件的所有操作日志并保存为.xlsx文件",
            tags = {"操作日志相关模块"}, response = void.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @RequestMapping(value = "/exportAsXLSX", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void exportAsXlsx(@RequestBody LoggerCondition condition, HttpServletResponse response) {
        List<LoggerEntity> loggers = loggerService.getPage(condition);
        String[][] fieldsAlias = LoggerEntity.fieldsAlias;
        int rows = 0;

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("操作日志记录表");

        XSSFRow head = sheet.createRow(rows++);
        for (int i = 0; i < fieldsAlias.length; i++) {
            head.createCell(i).setCellValue(new XSSFRichTextString(fieldsAlias[i][1]));
        }

        for (LoggerEntity logger : loggers) {
            XSSFRow row = sheet.createRow(rows++);
            excels.flush(row, logger, fieldsAlias);
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-disposition", "attachment;filename=logger.xlsx");
            response.flushBuffer();

            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
