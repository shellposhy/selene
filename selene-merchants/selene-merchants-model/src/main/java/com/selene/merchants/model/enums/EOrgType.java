package com.selene.merchants.model.enums;

/**
 * Enumeration of organization type
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum EOrgType {
	Gov("国家机关/党群组织/事业单位"), Edu("教育/培训/科研/院校"), Media("媒体/出版/文化传播"), Fin("银行/投资/基金/证券/保险"), Com(
			"国有企业/外资企业/合资企业/民营企业"), Pro("房地产/建筑/建材/工程"), Other("其他");

	private final String title;

	EOrgType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public int getValue() {
		return this.ordinal();
	}

	public static EOrgType valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length) {
			throw new IndexOutOfBoundsException("Invalid ordinal");
		}
		return values()[ordinal];
	}
}
