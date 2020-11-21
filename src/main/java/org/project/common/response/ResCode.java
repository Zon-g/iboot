package org.project.common.response;

public enum ResCode implements CustomizeResCode {
    SUCCESS(200, "成功"),
    ERROR(201, "失败"),
    USER_NEED_AUTHORITIES(201, "用户尚未登录"),
    USER_LOGIN_FAILED(202, "用户账号或密码错误"),
    USER_NO_ACCESS(203, "用户无权访问"),
    TOKEN_IN_BLACKLIST(204, "此token为黑名单"),
    LOGIN_IS_OVERDUE(205, "登录已失效");

    private Integer code;

    private String message;

    ResCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
