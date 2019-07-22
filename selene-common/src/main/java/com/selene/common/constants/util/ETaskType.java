package com.selene.common.constants.util;

/**
 * Enumeration of task type
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum ETaskType {
	Model("数据库模型"), Database("数据库"), Data("数据");

	private ETaskType(String title) {
		this.title = title;
	}

	private String title;

	public String getTitle() {
		return title;
	}
}
