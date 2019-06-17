package com.selene.dataing.model.enums;

/**
 * Enumeration of mysql field types
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum EDataFieldType {
	Required("必须字段"), Normal("普通字段");

	private String title;

	EDataFieldType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}
}
