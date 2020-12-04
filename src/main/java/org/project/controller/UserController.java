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
import org.project.entity.UserEntity;
import org.project.entity.ViewObject.UserVO;
import org.project.entity.condition.UserCondition;
import org.project.service.RoleService;
import org.project.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user")
@Api(value = "用户管理模块", tags = {"用户信息管理模块"})
public class UserController {

    @Resource
    private Excels excels;

    @Resource
    private FileUploads fileUploads;

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @ApiOperation(value = "查询用户信息", notes = "根据条件分页查询用户信息",
            tags = {"用户信息管理模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getPage", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res getPage(@RequestParam(required = false, defaultValue = "1") int cur,
                       @RequestParam(required = false, defaultValue = "5") int size,
                       @RequestBody UserCondition condition) {
        PageHelper.startPage(cur, size);
        List<UserVO> list = userService.getPage(condition);
        PageInfo<UserVO> pageInfo = new PageInfo<>(list);
        return Res.ok()
                .data("total", pageInfo.getTotal())
                .data("list", list);
    }

    @ApiOperation(value = "验证用户密码", notes = "验证用户输入的密码是否为原来的旧密码",
            tags = {"用户信息管理模块"}, response = Res.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/verifyPwd", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res verifyPwd(@RequestParam(value = "id") int id,
                         @RequestParam(value = "password") String password) {
        UserEntity userEntity = userService.getUserById(id);
        return Res.ok().data("equals", passwordEncoder.matches(password, userEntity.getPassword()));
    }

    @Logger(value = "修改用户密码", operation = Operation.UPDATE)
    @ApiOperation(value = "修改用户密码", notes = "根据用户id修改指定用户的密码并保存在数据库中",
            tags = {"用户信息管理模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res resetPassword(@RequestBody UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userService.updatePwd(userEntity) == 1 ? Res.ok() : Res.error();
    }

    @Logger(value = "修改用户信息", operation = Operation.UPDATE)
    @ApiOperation(value = "修改用户角色信息", notes = "根据前端传递的参数用户id以及新的角色身份数组来更新指定用户的角色信息",
            tags = {"用户信息管理模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/configUserRole", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res configUserRole(@RequestParam(value = "id") int id,
                              @RequestBody int[] roles) {
        List<Integer> oldRoles = userService.getRoleListById(id),
                newRoles = Arrays.stream(roles)
                        .boxed()
                        .collect(Collectors.toList()),
                oldDiffNew = oldRoles.stream()
                        .filter(num -> !newRoles.contains(num))
                        .collect(Collectors.toList()),
                newDiffOld = newRoles.stream()
                        .filter(num -> !oldRoles.contains(num))
                        .collect(Collectors.toList());
        if (oldDiffNew.size() > 0) roleService.deleteOldRoles(id, oldDiffNew);
        if (newDiffOld.size() > 0) roleService.addNewRoles(id, newDiffOld);
        return Res.ok();
    }

    @ApiOperation(value = "查询用户角色信息", notes = "根据当前用户id查询其角色对应情况以及全部角色列表并返回至前端",
            tags = {"用户信息管理模块"}, response = Res.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getUserRoleList", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res getUserRoleList(@RequestParam(value = "id") int id) {
        return Res.ok()
                .data("list", roleService.getRoleList())
                .data("select", userService.getRoleListById(id));
    }

    @Logger(value = "添加用户信息", operation = Operation.CREATE)
    @ApiOperation(value = "添加用户信息", notes = "将新增用户信息添加至数据库中并保存起来",
            tags = {"用户信息管理模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/add", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res addUser(@RequestBody UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userService.addUser(userEntity);
        return Res.ok();
    }

    @ApiOperation(value = "查询用户信息", notes = "根据前端传递的用户id查询对应用户的全部信息",
            tags = {"用户信息管理模块"}, response = Res.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getBy", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res getUserById(@RequestParam("id") int id) {
        UserEntity userEntity = userService.getUserById(id);
        return Res.ok().data("data", userEntity);
    }

    @Logger(value = "修改用户信息", operation = Operation.UPDATE)
    @ApiOperation(value = "修改用户信息", notes = "根据用户id修改用户信息并保存在数据库中",
            tags = {"用户信息管理模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/update", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res updateUser(@RequestBody UserEntity userEntity) {
        return userService.update(userEntity) == 1 ? Res.ok() : Res.error();
    }

    @Logger(value = "修改用户个人信息", operation = Operation.UPDATE)
    @ApiOperation(value = "修改用户个人信息", notes = "用户修改自己的个人信息并保存在数据库中",
            tags = {"用户信息管理模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res updateUserInfo(@RequestBody UserEntity userEntity) {
        return userService.updateUserInfo(userEntity) == 1 ? Res.ok() : Res.error();
    }

    @Logger(value = "删除用户信息", operation = Operation.DELETE)
    @ApiOperation(value = "删除用户信息", notes = "根据用户id\"删除\"数据库中对应的用户信息",
            tags = {"用户信息管理模块"}, response = Res.class, httpMethod = "DELETE",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res deleteUser(@RequestParam("id") int id) {
        return userService.deleteUser(id) == 1 ? Res.ok() : Res.error();
    }

//    @Logger(value = "修改用户状态", operation = Operation.UPDATE)
//    @ApiOperation(value = "修改用户状态信息", notes = "根据用户id修改当前用户的状态信息",
//            tags = {"用户信息管理模块"}, response = Res.class, httpMethod = "GET",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "/updateStatus", method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public Res updateUserStatus(@RequestParam(value = "id") int id,
//                                @RequestParam(value = "status") int status) {
//        return userService.updateStatus(id, status ^ 1) == 1 ? Res.ok() : Res.error();
//    }

    @Logger(value = "导出用户信息", operation = Operation.DOWNLOAD)
    @ApiOperation(value = "导出用户信息", notes = "根据查询条件导出满足当前条件的所有信息并保存为.xls文件",
            tags = {"用户信息管理模块"}, response = void.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @RequestMapping(value = "/exportAsXLS", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void exportUserInfoAsXLS(@RequestBody UserCondition condition, HttpServletResponse response) {
        List<UserVO> list = userService.getPage(condition);
        String[][] fieldsAlias = UserVO.fieldsAlias;
        int rowNum = 0;

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("用户信息表");

        HSSFRow header = sheet.createRow(rowNum++);
        for (int i = 0; i < fieldsAlias.length; i++) {
            header.createCell(i).setCellValue(new HSSFRichTextString(fieldsAlias[i][1]));
        }

        for (UserVO object : list) {
            HSSFRow row = sheet.createRow(rowNum++);
            excels.flush(row, object, fieldsAlias);
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-disposition", "attachment;filename=user.xls");
            response.flushBuffer();

            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Logger(value = "导出用户信息", operation = Operation.DOWNLOAD)
    @ApiOperation(value = "导出用户信息", notes = "根据查询条件导出满足当前条件的所有信息并保存为.xlsx文件",
            tags = {"用户信息管理模块"}, response = void.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @RequestMapping(value = "exportAsXLSX", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void exportUserInfoAsXLSX(@RequestBody UserCondition condition, HttpServletResponse response) {
        List<UserVO> list = userService.getPage(condition);
        String[][] fieldsAlias = UserVO.fieldsAlias;
        int rowNum = 0;

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("用户信息表");

        XSSFRow header = sheet.createRow(rowNum++);
        for (int i = 0; i < fieldsAlias.length; i++) {
            header.createCell(i).setCellValue(new XSSFRichTextString(fieldsAlias[i][1]));
        }

        for (UserVO object : list) {
            XSSFRow row = sheet.createRow(rowNum++);
            excels.flush(row, object, fieldsAlias);
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-disposition", "attachment;filename=user.xlsx");
            response.flushBuffer();

            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Logger(value = "上传用户头像", operation = Operation.UPLOAD)
    @ApiOperation(value = "上传用户头像", notes = "将用户上传的头像保存至本地并返回图片地址",
            tags = {"用户信息管理模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/avatarUpload", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res updateAvatar(@RequestParam("file") MultipartFile file) {
        return Res.ok()
                .data("path", fileUploads.upload(Folders.Avatar, file));
    }

    @ApiOperation(value = "获取用户昵称邮箱列表", notes = "获取用户昵称和邮箱列表并返回值前端",
            tags = {"用户信息管理模块"}, response = Res.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/userMailList", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res getUserMailList() {
        return Res.ok()
                .data("list", userService.getUserMailList());
    }

}
