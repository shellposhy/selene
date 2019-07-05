package com.selene.viewing.admin;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

public class RedisTest {

	public static void main(String[] args) {
		JedisPoolConfig config=new JedisPoolConfig();
		config.setMaxIdle(300);
		config.setMaxTotal(6000);
		config.setTestOnBorrow(true);
		
		JedisConnectionFactory connectionFactory=new JedisConnectionFactory();
		connectionFactory.setPoolConfig(config);
		connectionFactory.setHostName("192.168.2.72");
		connectionFactory.setPassword("test1234");
		connectionFactory.setPort(6379);
		connectionFactory.setTimeout(2000);
		connectionFactory.afterPropertiesSet();
		
		StringRedisTemplate redisTemplate=new StringRedisTemplate(connectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		
		redisTemplate.afterPropertiesSet();
		
		redisTemplate.opsForValue().set("test", "123");
	}
}
