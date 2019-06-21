package com.selene.merchants.model.enums;

/**
 * Enumeration of database status
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum EOrgStatus {
	Stop("通用"), Normal("正常");

	private final String title;

	EOrgStatus(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public int getValue() {
		return this.ordinal();
	}

	public static EOrgStatus valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length) {
			throw new IndexOutOfBoundsException("Invalid ordinal");
		}
		return values()[ordinal];
	}
}
