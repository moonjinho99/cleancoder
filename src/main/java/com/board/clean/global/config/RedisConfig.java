package com.board.clean.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory){
		
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(connectionFactory);
		
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		
		return template;
	}
	
	
	//RedisTemplate : 데이터 조작을 추상화하고 자동화해 주는 역할
	//데이터를 읽고 쓰는 모든 작업을 처리
	//String, List, Set, Sorted Set, Hash 등의 Redis 구조를 쉽게 다룰 수 있다

	//RedisConnecionFactory : Redis와의 연결을 위한 Connection을 생성하고 관리
}
