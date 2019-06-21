package com.selene.merchants.model.enums;

/**
 * Enumeration of database status
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum EOrgStatus {
	Petrified("锁定"), Normal("正常"), Stop("停止"), Repairing("修复");

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