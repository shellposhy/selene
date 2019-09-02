package com.selene.common.config.service;

import com.selene.common.config.support.AbstractConfiger;

public class BaseConfiger extends AbstractConfiger {

	public BaseConfiger(String path) {
		super(path);
	}

	public String value(String key) {
		return super.value(key);
	}
}
