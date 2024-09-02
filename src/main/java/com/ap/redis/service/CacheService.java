package com.ap.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
	private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public CacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void putData(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }
   

    public Object getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}
