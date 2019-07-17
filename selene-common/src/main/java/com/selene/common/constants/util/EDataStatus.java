package com.selene.common.constants.util;

/**
 * Enumeration of database status
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum EDataStatus {
	Petrified("锁定"), Normal("正常"), Stop("停止"), Repairing("修复");

	private final String title;

	EDataStatus(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public int getValue() {
		return this.ordinal();
	}

	public static EDataStatus valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length) {
			throw new IndexOutOfBoundsException("Invalid ordinal");
		}
		return values()[ordinal];
	}
}
