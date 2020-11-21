package org.project.common.redis.operator;

import org.project.common.redis.constant.RedisType;
import org.project.common.redis.entity.CacheEntity;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public List<CacheEntity> getCacheList() {
        return Objects.requireNonNull(redisTemplate.keys("*"))
                .stream()
                .map(s -> new CacheEntity(s, Objects.requireNonNull(redisTemplate.type(s)).code(),
                        Optional.ofNullable(redisTemplate.getExpire(s, TimeUnit.SECONDS)).orElse(-1L)))
                .collect(Collectors.toList());
    }

    public Object getKey(String key, String type) {
        Object ans;
        switch (type) {
            case "string":
                ans = redisTemplate.opsForValue().get(key);
                break;
            case "list":
                long size = Optional.ofNullable(redisTemplate.opsForList().size(key)).orElse(-1L);
                ans = redisTemplate.opsForList().range(key, 0, size);
                break;
            case "set":
                ans = redisTemplate.opsForSet().members(key);
                break;
            case "hash":
                ans = redisTemplate.opsForHash().entries(key);
                break;
            default:
                throw new RuntimeException("Wrong types for specified key");
        }
        return ans;
    }

    public boolean deleteAll() {
        redisTemplate.delete(Objects.requireNonNull(redisTemplate.keys("*")));
        return true;
    }

    public boolean deleteKey(String key) {
        return Optional.ofNullable(redisTemplate.delete(key)).orElse(false);
    }

    @SuppressWarnings("unchecked")
    public boolean updateByKey(String key, Object value, RedisType types) {
        switch (types) {
            case SET:
                redisTemplate.delete(key);
                SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
                opsForSet.add(key, ((Long[]) value));
                break;
            case HASH:
                redisTemplate.delete(key);
                HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
                opsForHash.putAll(key, ((Map<String, Object>) value));
                break;
            case LIST:
                redisTemplate.delete(key);
                ListOperations<String, Object> opsForList = redisTemplate.opsForList();
                opsForList.rightPushAll(key, ((Long[]) value));
                break;
            case STRING:
                redisTemplate.opsForValue().set(key, value);
                break;
            default:
                throw new RuntimeException(String.format("Wrong type %s...", types.name()));
        }
        return true;
    }

}
