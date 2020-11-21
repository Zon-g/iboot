package org.project.common.redis.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "缓存信息实体类", description = "缓存信息实体类")
public class CacheEntity {

    @ApiModelProperty(value = "缓存的键", dataType = "String", example = "key")
    private String key;

    @ApiModelProperty(value = "缓存的类型", dataType = "String", example = "string")
    private String type;

    @ApiModelProperty(value = "缓存过期时间", dataType = "long", example = "123")
    private long expire;

    public CacheEntity() {
    }

    public CacheEntity(String key, String type, long expire) {
        this.key = key;
        this.type = type;
        this.expire = expire;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

}
