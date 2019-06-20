package com.selene.merchants.model.enums;

/**
 * Enumeration of user action status
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum EActionUserType {
	Admin("后台/运营权限"), Client("前端/商户权限");

	private String title;

	private EActionUserType(String title) {
		this.title = title;
	}

	public static EActionUserType valueOf(int index) {
		return EActionUserType.values()[index];
	}

	public String getTitle() {
		return title;
	}
}
