package com.example.redis.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisValueCache {
    RedisTemplate<String, Object> redisTemplate;
    ValueOperations<String, Object> valueOperations;

    public RedisValueCache(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        valueOperations = redisTemplate.opsForValue();
    }

    public void cacheValue(final String key, final Object value) {
        //valueOperations.set(key, value);
        valueOperations.set(key, value, 10, TimeUnit.SECONDS);
    }

    public Object retrieveValue(final String key) {
        return valueOperations.get(key);
    }

    public void deleteCachedValue(final String key) {
        valueOperations.getOperations().delete(key);
    }
}
