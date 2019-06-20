package com.selene.merchants.model.enums;

/**
 * Enumeration of user page type
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum EPageType {
	SysPage("系统首页"), UserPage("自定义");

	public static EPageType valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length) {
			throw new IndexOutOfBoundsException("Invalid ordinal");
		}
		return values()[ordinal];
	}

	private final String title;

	EPageType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}
}
