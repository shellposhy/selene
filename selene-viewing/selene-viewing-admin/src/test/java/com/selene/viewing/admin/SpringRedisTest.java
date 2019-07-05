package com.selene.viewing.admin;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import com.selene.common.util.RedisClient;

public class SpringRedisTest {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		AbstractApplicationContext ac = new ClassPathXmlApplicationContext("redis.xml");
		RedisTemplate<String, Object> template = (RedisTemplate<String, Object>) ac.getBean("redisTemplate");
		
		System.out.println(template);

		template.opsForValue().set("wanglili", "wanglili");
		
		RedisClient client = new RedisClient();
		client.setRedisTemplate(template);

		client.set("wanglili1", "wanglili1");
		
		ac.close();

	}
}
