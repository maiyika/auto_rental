package com.coder.rental.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Barry
 * @project auto_rental
 * @date 16/6/2024
 */

/**
 * Redis工具类
 */
@Component
public class RedisUtils {
	@Resource
	private StringRedisTemplate stringRedisTemplate;

	public void set( String key, String value, Long timeout ) {
		stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
	}

	public String get( String key ) {
		return stringRedisTemplate.opsForValue().get(key);
	}

	public void del( String key ) {
		stringRedisTemplate.delete(key);
	}
}
