package org.project.common.redis.Entity;

public class CacheEntity {

    private String key;

    private String type;

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
