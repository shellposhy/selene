package com.selene.templating.model.constants;

/**
 * Enumeration of search index filter directory
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum EFilterStatus {
	Stop("停用"), Normal("正常");

	private final String title;

	EFilterStatus(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
