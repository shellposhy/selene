package com.selene.templating.model.constants;

/**
 * Enumeration of page put on/off shelf status
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum EPageTransfer {
	Off("下架"), On("上架");

	private EPageTransfer(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return name;
	}

}
