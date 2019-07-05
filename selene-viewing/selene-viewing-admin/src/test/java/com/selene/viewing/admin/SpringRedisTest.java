package com.selene.viewing.admin;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import com.selene.common.util.RedisClient;
import com.selene.viewing.admin.vo.merchants.MerchantsUserVO;

public class SpringRedisTest {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		AbstractApplicationContext ac = new ClassPathXmlApplicationContext("redis.xml");
		RedisTemplate<String, Object> template = (RedisTemplate<String, Object>) ac.getBean("redisTemplate");
		RedisClient client = new RedisClient();
		client.setRedisTemplate(template);

		MerchantsUserVO vo=(MerchantsUserVO)client.get("818ffaf64b830099b914929094951b6c");
		
		System.out.println(vo.getActionTree());
		System.out.println(vo.getLicense());
		
		ac.close();
	}
}
