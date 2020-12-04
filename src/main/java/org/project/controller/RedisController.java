package org.project.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.project.common.logger.Logger;
import org.project.common.logger.Operation;
import org.project.common.redis.constant.RedisType;
import org.project.common.redis.entity.BasicInfo;
import org.project.common.redis.operator.RedisService;
import org.project.common.response.Res;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RequestMapping(value = "/bee")
@RestController
@Api(value = "Redis监控模块", tags = {"Redis监控信息管理模块"})
public class RedisController {

    @Resource
    private BasicInfo basicInfo;

    @Resource
    private RedisProperties redisProperties;

    @Resource
    private RedisService redisService;

    @ApiOperation(value = "获取Redis Monitor首页信息", notes = "获取Redis Monitor首页信息并以JSON形式返回",
            tags = {"Redis监控信息管理模块"}, response = Res.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/index", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res geiIndex() {
        return Res.ok()
                .data("list", basicInfo);
    }

    @ApiOperation(value = "获取Redis连接参数", notes = "获取当前应用的Redis连接参数并以JSON形式返回",
            tags = {"Redis监控信息管理模块"}, response = Res.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/params", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res connectionParams() {
        return Res.ok()
                .data("properties", redisProperties);
    }

    @ApiOperation(value = "获取所有缓存的键", notes = "获取Redis中所有缓存的键的信息并返回",
            tags = "Redis监控信息管理模块", response = Res.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/keys", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res getKeys() {
        return Res.ok()
                .data("list", redisService.getCacheList());
    }

    @ApiOperation(value = "查询键对应的缓存内容", notes = "根据指定的键和类型查询缓存的具体内容并返回",
            tags = "Redis监控信息管理模块", response = Res.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/key", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res getKey(@RequestParam(value = "key") String key,
                      @RequestParam(value = "type") String type) {
        return Res.ok()
                .data("key", redisService.getKey(key, type));
    }

    @ApiOperation(value = "删除全部Redis缓存", notes = "删除当前Redis服务器中的所有缓存",
            tags = "Redis监控信息管理模块", response = Res.class, httpMethod = "DELETE",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Logger(value = "删除全部Redis缓存", operation = Operation.DELETE)
    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res deleteAll() {
        return redisService.deleteAll() ? Res.ok() : Res.error();
    }

    @ApiOperation(value = "删除Redis指定缓存", notes = "根据指定的键删除Redis服务器中的缓存",
            tags = "Redis监控信息管理模块", response = Res.class, httpMethod = "DELETE",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Logger(value = "删除Redis中指定key对应的缓存", operation = Operation.DELETE)
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res deleteKey(@RequestParam(value = "key") String key) {
        return redisService.deleteKey(key) ? Res.ok() : Res.error();
    }

    @ApiOperation(value = "修改Redis String缓存", notes = "接受一个键-值(String)对并存储在Redis服务器中",
            tags = "Redis监控信息管理模块", response = Res.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Logger(value = "修改Redis String缓存", operation = Operation.UPDATE)
    @RequestMapping(value = "/updateString", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res updateString(@RequestParam(value = "key") String key,
                            @RequestParam(value = "value") String value) {
        return redisService.updateByKey(key, value, RedisType.STRING) ? Res.ok() : Res.error();
    }

    @ApiOperation(value = "修改Redis Set和List缓存", notes = "接受一个键-值(Set 或 List)对并存储在Redis服务器中",
            tags = "Redis监控信息管理模块", response = Res.class, httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Logger(value = "修改Redis Set和List缓存", operation = Operation.UPDATE)
    @RequestMapping(value = "/updateSetAndList", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res updateSetAndList(@RequestParam(value = "key") String key,
                                @RequestParam(value = "type") String type,
                                @RequestParam(value = "value") Long[] value) {
        return redisService.updateByKey(key, value, Enum.valueOf(RedisType.class, type.toUpperCase())) ? Res.ok() : Res.error();
    }

    @ApiOperation(value = "修改Redis Hash缓存", notes = "接受一个键-值(Hash)对并存储在Redis服务器中",
            tags = {"Redis监控信息管理模块"}, response = Res.class, httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Logger(value = "修改Redis Hash缓存", operation = Operation.UPDATE)
    @RequestMapping(value = "/updateHash", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Res updateHash(@RequestParam(value = "key") String key,
                          @RequestBody Map<String, Object> mapping) {
        return redisService.updateByKey(key, mapping, RedisType.HASH) ? Res.ok() : Res.error();
    }

}
