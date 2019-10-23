package com.selene.templating.model.constants;

/**
 * Enumeration of page content replace type
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum EReplaceType {
	IMG("img"), CSS("link"), JSCRIPT("script"), Ftl("ftl");

	private final String title;

	EReplaceType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

}
