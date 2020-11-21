package org.project.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@ApiModel(value = "返回状态实体类", description = "返回状态实体类")
public class Res {

    @ApiModelProperty(value = "表示是否成功", dataType = "boolean", example = "true")
    private boolean success;

    @ApiModelProperty(value = "返回状态码", dataType = "Integer", example = "200")
    private Integer code;

    @ApiModelProperty(value = "返回消息", dataType = "String", example = "成功")
    private String msg;

    @ApiModelProperty(value = "返回数据", dataType = "Map", example = "{}")
    private Map<String, Object> data = new HashMap<>();

    private Res() {
    }

    public static Res ok() {
        Res res = new Res();
        ResCode code = ResCode.SUCCESS;
        res.setSuccess(true);
        res.setCode(code.getCode());
        res.setMsg(code.getMessage());
        return res;
    }

    public static Res error() {
        Res res = new Res();
        ResCode code = ResCode.ERROR;
        res.setSuccess(false);
        res.setCode(code.getCode());
        res.setMsg(code.getMessage());
        return res;
    }

    public static Res error(ResCode code) {
        Res res = new Res();
        res.setSuccess(false);
        res.setCode(code.getCode());
        res.setMsg(code.getMessage());
        return res;
    }

    public Res success(boolean success) {
        this.setSuccess(success);
        return this;
    }

    public Res code(int code) {
        this.setCode(code);
        return this;
    }

    public Res msg(String msg) {
        this.setMsg(msg);
        return this;
    }

    public Res data(String key, Object val) {
        this.getData().put(key, val);
        return this;
    }

    public Res data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

}
