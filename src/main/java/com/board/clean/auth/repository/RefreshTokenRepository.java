package com.board.clean.auth.repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {

	private final RedisTemplate<String, String> redisTemplate;
	private static final String PREFIX = "RT:";
	
	public void save(long userId, String refreshToken, long expirationTimeInMills) {
		String key = PREFIX+userId;
		redisTemplate.opsForValue().set(key, refreshToken, expirationTimeInMills, TimeUnit.MILLISECONDS);
	}
	
	public Optional<String> findByUserId(long userId){
		String key = PREFIX + userId;
		String token = redisTemplate.opsForValue().get(key);
		return Optional.ofNullable(token);
	}
	
	public void deleteByUserId(long userId) {
		String key = PREFIX + userId;
		redisTemplate.delete(key);
	}
}
