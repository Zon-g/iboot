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
import org.project.common.attachment.Folders;
import org.project.common.logger.Logger;
import org.project.common.logger.Operation;
import org.project.common.response.Res;
import org.project.common.utils.Excels;
import org.project.common.utils.FileUploads;
import org.project.entity.MailEntity;
import org.project.entity.condition.MailCondition;
import org.project.service.MailService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/mail")
@Api(value = "邮件管理", tags = {"邮件管理相关模块"})
public class MailController {

    @Resource
    private MailService mailService;

    @Resource
    private FileUploads fileUploads;

    @Resource
    private Excels excels;

    @Logger(value = "上传邮件图片", operation = Operation.UPLOAD)
    @ApiOperation(value = "上传邮件图片", notes = "将用户要添加在邮件正文中的图片上传至服务器并返回图片地址",
            tags = {"邮件管理相关模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/imageUpload", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res addImage(@RequestParam(value = "file") MultipartFile file) {
        return Res.ok()
                .data("path", fileUploads.upload(Folders.Mail, file));
    }

    @Logger(value = "上传邮件附件", operation = Operation.UPLOAD)
    @ApiOperation(value = "上传邮件附件", notes = "将用户添加在邮件的附件文件上传至服务器并发挥附件地址",
            tags = {"邮件管理相关模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/attachmentUpload", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res addAttachment(@RequestParam(value = "files") MultipartFile[] files) {
        String[] paths = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            paths[i] = fileUploads.upload(Folders.Mail, files[i], true);
        }
        return Res.ok()
                .data("paths", paths);
    }

    @ApiOperation(value = "查询邮件信息", notes = "根据条件分页查询邮件信息并返回",
            tags = {"邮件管理相关模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getPage", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res getPage(@RequestParam(value = "cur", required = false, defaultValue = "1") int cur,
                       @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                       @RequestBody MailCondition condition) {
        PageHelper.startPage(cur, size);
        List<MailEntity> list = mailService.findPage(condition);
        PageInfo<MailEntity> pageInfo = new PageInfo<>(list);
        return Res.ok()
                .data("total", pageInfo.getTotal())
                .data("list", pageInfo.getList());
    }

    @Logger(value = "添加邮件发送信息", operation = Operation.CREATE)
    @ApiOperation(value = "添加邮件发送信息", notes = "将待发送的邮件信息添加至数据库中存储",
            tags = {"邮件管理相关模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/add", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res addRecord(@RequestBody MailEntity entity,
                         @RequestParam(value = "attachments", required = false) String attachments) {
        String[] attaches = new String[0];
        if (attachments.length() > 0) attaches = attachments.split(",");
        return mailService.add(entity, attaches) > 0 ? Res.ok() : Res.error();
    }

    @Logger(value = "删除邮件发送信息", operation = Operation.DELETE)
    @ApiOperation(value = "删除邮件发送信息", notes = "根据id删除指定的邮件发送信息",
            tags = {"邮件管理相关模块"}, response = Res.class, httpMethod = "DELETE",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res deleteRecord(@RequestParam(value = "id") long id) {
        return mailService.delete(id) == 1 ? Res.ok() : Res.error();
    }

    @Logger(value = "导出邮件发送信息", operation = Operation.DOWNLOAD)
    @ApiOperation(value = "导出邮件发送信息", notes = "根据条件查询满足当前条件的所有记录并保存为xls文件",
            tags = {"邮件管理相关模块"}, response = void.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @RequestMapping(value = "/exportAsXls", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadAsXls(@RequestBody MailCondition condition, HttpServletResponse response) {
        List<MailEntity> list = mailService.findPage(condition);
        String[][] fieldsAlias = MailEntity.fieldsAlias;
        int rows = 0;

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("邮件信息记录表");

        HSSFRow head = sheet.createRow(rows++);
        for (int i = 0; i < fieldsAlias.length; i++) {
            head.createCell(i).setCellValue(new HSSFRichTextString(fieldsAlias[i][1]));
        }

        for (MailEntity entity : list) {
            HSSFRow row = sheet.createRow(rows++);
            excels.flush(row, entity, fieldsAlias);
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-disposition", "attachment;filename=emails.xls");
            response.flushBuffer();

            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Logger(value = "导出邮件发送信息", operation = Operation.DOWNLOAD)
    @ApiOperation(value = "导出邮件发送信息", notes = "根据条件查询满足当前条件的所有记录并保存为xlsx文件",
            tags = {"邮件管理相关模块"}, response = void.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @RequestMapping(value = "/exportAsXlsx", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadAsXlsx(@RequestBody MailCondition condition, HttpServletResponse response) {
        List<MailEntity> list = mailService.findPage(condition);
        String[][] fieldsAlias = MailEntity.fieldsAlias;
        int rows = 0;

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("邮件信息记录表");

        XSSFRow head = sheet.createRow(rows++);
        for (int i = 0; i < fieldsAlias.length; i++) {
            head.createCell(i).setCellValue(new XSSFRichTextString(fieldsAlias[i][1]));
        }

        for (MailEntity entity : list) {
            XSSFRow row = sheet.createRow(rows++);
            excels.flush(row, entity, fieldsAlias);
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-disposition", "attachment;filename=emails.xlsx");
            response.flushBuffer();

            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
