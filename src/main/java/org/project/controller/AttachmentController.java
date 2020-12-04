package org.project.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.project.common.attachment.FileHelper;
import org.project.common.attachment.Folders;
import org.project.common.logger.Logger;
import org.project.common.logger.Operation;
import org.project.common.response.Res;
import org.project.entity.AttachmentEntity;
import org.project.entity.condition.AttachmentCondition;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping(value = "/attachment")
@Api(value = "附件管理相关", tags = {"附件管理相关模块"})
public class AttachmentController {

    @ApiOperation(value = "查询附件类型列表", notes = "查询附件类型并以列表的形式返回",
            tags = {"附件管理相关模块"}, response = Res.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getTypes", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res getFileTypes() {
        List<String> types = Arrays.stream(Folders.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return Res.ok()
                .data("list", types);
    }

    @ApiOperation(value = "获取附件信息列表", notes = "根据条件在后台查询满足条件的附件信息并返回",
            tags = {"附件管理相关模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getPage", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res getAttachmentList(@RequestParam(value = "cur") int cur,
                                 @RequestParam(value = "size") int size,
                                 @RequestBody AttachmentCondition condition) {
        List<AttachmentEntity> list = FileHelper.searchFiles()
                .stream()
                .filter(condition.getType() != null && !condition.getType().equals("") ?
                        entity -> entity.getType().equals(condition.getType()) : entity -> true)
                .collect(Collectors.toList());
        int start = Math.min((cur - 1) * size, list.size()),
                end = Math.min(cur * size, list.size());
        return Res.ok()
                .data("total", list.size())
                .data("list", list.subList(start, end));
    }

    @Logger(value = "删除附件文件", operation = Operation.DELETE)
    @ApiOperation(value = "删除附件文件", notes = "删除参数指定的一个附件文件",
            tags = {"附件管理相关模块"}, response = Res.class, httpMethod = "DELETE",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res deleteFile(@RequestParam(value = "file") String file) {
        return FileHelper.deleteFile(file) ? Res.ok() : Res.error();
    }

    @Logger(value = "批量删除附件文件", operation = Operation.DELETE)
    @ApiOperation(value = "批量删除附件文件", notes = "删除参数数组指定的一组文件",
            tags = {"附件管理相关模块"}, response = Res.class, httpMethod = "DELETE",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res deleteBatch(@RequestBody String[] files) {
        int cnt = 0;
        for (int i = 0; i < files.length; i++) {
            cnt += FileHelper.deleteFile(files[i]) ? 1 : 0;
        }
        return cnt == files.length ? Res.ok() : Res.error();
    }

    @Logger(value = "下载附件文件", operation = Operation.DOWNLOAD)
    @ApiOperation(value = "下载附件文件", notes = "下载参数指定的一个附件文件",
            tags = {"附件管理相关模块"}, response = void.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @RequestMapping(value = "/download", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadFile(@RequestParam(value = "file") String file, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-disposition", "attachment;filename=" + new File(file).getName());
        try (OutputStream outputStream = response.getOutputStream();
             BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
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

    @Logger(value = "批量下载附件文件", operation = Operation.DOWNLOAD)
    @ApiOperation(value = "批量下载附件文件", notes = "根据参数将指定的附件文件打包下载成一个压缩文件",
            tags = {"附件管理相关模块"}, response = void.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @RequestMapping(value = "/downloadBatch", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void download(@RequestBody String[] files, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-disposition", "attachment;filename=attachments.zip");
        try (ZipOutputStream outputStream = new ZipOutputStream(response.getOutputStream())) {
            for (int i = 0; i < files.length; i++) {
                File file = new File(files[i]);
                outputStream.putNextEntry(new ZipEntry(file.getName()));
                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
                byte[] bytes = new byte[1024];
                int len;
                while ((len = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, len);
                }
                inputStream.close();
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
