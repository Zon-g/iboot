package org.project.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.project.common.attachment.FileHelper;
import org.project.common.logger.Logger;
import org.project.common.logger.Operation;
import org.project.common.response.Res;
import org.project.common.utils.Backups;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping(value = "/backup")
@Api(value = "数据库备份相关", tags = {"MySQL数据库备份模块"})
public class BackupController {

    @Resource
    private Backups backups;

    @Resource
    private JdbcTemplate template;

    @Logger(value = "备份数据库", operation = Operation.BACKUP)
    @ApiOperation(value = "备份数据库", notes = "备份数据库中所有表结构以及相应的数据",
            tags = {"MySQL数据库备份模块"}, response = Res.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/backupAll", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res backupAll() {
        return backups.backup() == 0 ? Res.ok() : Res.error();
    }

    @ApiOperation(value = "备份数据库", notes = "备份数据库中被选中的表结构以及相应的数据",
            tags = {"MySQL数据库备份模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/backupTables", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res backupTables(@RequestBody String[] tables) {
        return backups.backup(tables) == 0 ? Res.ok() : Res.error();
    }

    @ApiOperation(value = "获取数据库表基本信息", notes = "查询数据库中的所有表名称并返回",
            tags = {"MySQL数据库备份模块"}, response = Res.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getTables", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res getTables() {
        List<String> tables = template.queryForList("show tables", String.class);
        return Res.ok()
                .data("list", tables);
    }

    @ApiOperation(value = "获取数据库备份文件", notes = "获取数据库备份文件夹下的所有数据库备份文件的名字",
            tags = {"MySQL数据库备份模块"}, response = Res.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getBackupFiles", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res getBackupFiles() {
        List<String> files = backups.getBackupFiles();
        return Res.ok()
                .data("list", files);
    }

    @Logger(value = "删除备份文件", operation = Operation.DELETE)
    @ApiOperation(value = "删除备份文件", notes = "删除指定的数据库备份文件", tags = {"MySQL数据库备份模块"},
            response = Res.class, httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/deleteBackupFile", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res deleteBackupFile(@RequestParam(value = "filename") String filename) {
        return backups.deleteBackupFile(filename) ? Res.ok() : Res.error();
    }

    @Logger(value = "下载备份文件", operation = Operation.DOWNLOAD)
    @ApiOperation(value = "下载备份文件", notes = "下载指定的数据库备份文件", tags = {"MySQL数据库备份模块"},
            response = void.class, httpMethod = "GET", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @RequestMapping(value = "/download", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void download(@RequestParam(value = "filename") String filename, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-Disposition", "attachment;filename=backup.sql");
        try (OutputStream outputStream = response.getOutputStream();
             BufferedInputStream inputStream = new BufferedInputStream(
                     new FileInputStream(FileHelper.getBackupRoot() + filename))) {
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Logger(value = "混滚数据库", operation = Operation.ROLLBACK)
    @ApiOperation(value = "回滚数据库", notes = "根据指定的数据库备份文件回滚数据库",
            tags = {"MySQL数据库备份模块"}, response = Res.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/rollback", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res rollback(@RequestParam(value = "filename") String filename) {
        return backups.rollback(filename) == 0 ? Res.ok() : Res.error();
    }

}
