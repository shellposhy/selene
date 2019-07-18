package com.selene.common.config.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.selene.common.config.support.AbstractConfiger;
import com.selene.common.constants.ServiceConstants;

import cn.com.lemon.base.Strings;

public class ServiceConfiger extends AbstractConfiger {
	private AtomicInteger idx = new AtomicInteger(0);
	private Map<String, List<String>> SERVICES = new ConcurrentHashMap<String, List<String>>();
	private static final String DEFAULT_PATH = ServiceConfiger.class.getResource("/").getPath()
			+ "selene.sevice.properties";

	public ServiceConfiger() {
		super(DEFAULT_PATH);
		// init all service
		String[] keys = super.keys();
		for (String sevice : ServiceConstants.BASE_SERVICES) {
			SERVICES.put(sevice, java.util.Arrays.asList(super.match(sevice, keys)));
		}
	}

	public ServiceConfiger(String path) {
		super(path);
		// init all service
		String[] keys = super.keys();
		for (String sevice : ServiceConstants.BASE_SERVICES) {
			SERVICES.put(sevice, java.util.Arrays.asList(super.match(sevice, keys)));
		}
	}

	public List<String> keyList(String serviceName) {
		return SERVICES.get(serviceName);
	}

	public String key(String serviceName) {
		List<String> list = keyList(serviceName);
		if (list.size() > 0) {
			// Multi-service processing based on polling mechanism
			int index = (0x7fffffff & idx.incrementAndGet()) % list.size();
			return list.get(index);
		}
		return null;
	}

	public String value(String serviceName) {
		String key = key(serviceName);
		if (!Strings.isNullOrEmpty(key)) {
			return super.value(key);
		}
		return null;
	}
}
