package com.selene.viewing.api.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.selene.common.config.service.ServiceConfiger;
import com.selene.common.util.RedisClient;

@Service
public class DataService {
	@Resource
	private ServiceConfiger serviceConfiger;
	@Resource
	private RedisClient redisClient;

	
}
