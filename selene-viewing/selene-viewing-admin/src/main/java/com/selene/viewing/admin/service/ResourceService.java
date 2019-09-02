package com.selene.viewing.admin.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.selene.common.config.service.BaseConfiger;
import com.selene.common.constants.ResourceConstants;

@Service
public class ResourceService {
	private BaseConfiger configer;

	@PostConstruct
	public void init() {
		configer = /* Properties profile */new BaseConfiger(
				ResourceService.class.getResource("/").getPath() + "config.properties");
	}

	/**
	 * Get value by key
	 * 
	 * @param key
	 * @return {@code String}
	 */
	public String path(String key) {
		return configer.value(key);
	}

	/**
	 * Get resource root path
	 * 
	 * @return {@code String}
	 */
	public String root() {
		return path(ResourceConstants.RESOURCE_BASE_PATH_KEY);
	}
}
