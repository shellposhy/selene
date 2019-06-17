package com.selene.dataing.model.enums;

/**
 * Enumeration of mysql data field access types
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum EAccessType {
	System("系统"), Hidden("隐藏"), ReadOnly("只读"), Modifiable("可编辑");

	private String title;

	public String getTitle() {
		return title;
	}

	private EAccessType(String title) {
		this.title = title;
	}
}
