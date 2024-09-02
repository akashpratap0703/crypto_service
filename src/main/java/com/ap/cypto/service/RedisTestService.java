package com.ap.cypto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ap.redis.service.CacheService;

@RestController
public class RedisTestService {
	
	CacheService cacheService;

	public CacheService getCacheService() {
		return cacheService;
	}

	@Autowired
	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}

	@GetMapping("/redis")
	public String createAesKey() {
		try {
			getCacheService().putData("hello","world");
			return (String) getCacheService().getData("hello");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Failed to create";

	}
	
	

}
