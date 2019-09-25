package com.selene.common.constants.util;

public enum EDateSearchType {
	Day("今日"), Month("本月"), Range("时间段");
	public String title;

	private EDateSearchType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
