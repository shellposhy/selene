package com.selene.templating.model.constants;

/**
 * Enumeration of page model type
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum EModelType {

	Home("首页模板"), Subject("专题模板"), List("列表模板"), Detail("详情模板");

	private final String title;

	EModelType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}
}
