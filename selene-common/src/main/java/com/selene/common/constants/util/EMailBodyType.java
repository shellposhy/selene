package com.selene.common.constants.util;

/**
 * Enumeration of email content types
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum EMailBodyType {
	Text("text"), Html("html");
	public String title;

	private EMailBodyType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
