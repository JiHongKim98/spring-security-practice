package com.example.jwt.global.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.RequiredArgsConstructor;

@EnableCaching
@Configuration
@RequiredArgsConstructor
public class CacheConfig {

	private final RedisConnectionFactory redisConnectionFactory;

	private static final Long ONE_HOURS = 60 * 60L;  // TTL 1 Hours
	private static final Long ONE_DAY = 60 * 60 * 5L;  // TTL 24 Hours

	@Bean
	public RedisCacheManager cacheManager() {
		Map<String, RedisCacheConfiguration> redisCacheMap = new HashMap<>();
		// "memberCacheStore" 라는 이름의 Cache 설정
		redisCacheMap.put("memberCacheStore", setRedisCacheConfiguration(ONE_HOURS));

		return RedisCacheManager.RedisCacheManagerBuilder
			.fromConnectionFactory(redisConnectionFactory)  // redis 설정 연결
			.cacheDefaults(setRedisCacheConfiguration(ONE_DAY))  // redis 캐시 기본 설정(전역)
			.withInitialCacheConfigurations(redisCacheMap)  // 초기 캐시 설정
			.build();
	}

	// Redis Cache 설정
	private RedisCacheConfiguration setRedisCacheConfiguration(Long Ttl) {
		return RedisCacheConfiguration.defaultCacheConfig()
			.disableCachingNullValues()  // null 값 비허용
			.entryTtl(Duration.ofSeconds(Ttl))  // TTL (Time To Live 설정)
			.serializeKeysWith(
				RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
			)
			.serializeValuesWith(
				RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())
			);
	}
}
