package com.daja.waa_server_lab.service.impl;

import com.daja.waa_server_lab.service.spec.IRedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisServiceImpl implements IRedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    private final ValueOperations<String, Object> valueOperations;

    public RedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public void add(String key, Object value, Duration expirationTime) {
        valueOperations.set(key, value, expirationTime);
    }

    @Override
    public Object get(String key) {
        return valueOperations.get(key);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
