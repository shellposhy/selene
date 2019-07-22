package com.selene.common.constants.util;

/**
 * Enumeration of task sub type
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum ETaskSubType {

	Add("新增"), Edit("编辑"), Delete("删除"), Copy("复制"), Move("迁移");

	private ETaskSubType(String title) {
		this.title = title;
	}

	private String title;

	public String getTitle() {
		return title;
	}
}
