package com.selene.templating.model.constants;

/**
 * Enumeration of page status
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum EPageStatus {
	Unpublish("未发布"), Publishing("发布中"), Published("已发布");

	private final String title;

	EPageStatus(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}
}
