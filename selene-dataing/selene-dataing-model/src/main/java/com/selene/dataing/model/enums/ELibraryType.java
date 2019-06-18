package com.selene.dataing.model.enums;

/**
 * Enumeration of database data type
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum ELibraryType {
	Default("默认数据库", "default"), Image("图片数据库", "image"), File("文件数据库", "file"), Video("视频数据库", "video");

	private String title;
	private String code;

	ELibraryType(String title, String code) {
		this.title = title;
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public String getCode() {
		return code;
	}

	public int getValue() {
		return this.ordinal();
	}

	public static ELibraryType valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length) {
			throw new IndexOutOfBoundsException("Invalid ordinal");
		}
		return values()[ordinal];
	}
}
