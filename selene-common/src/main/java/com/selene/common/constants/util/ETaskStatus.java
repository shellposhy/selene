package com.selene.common.constants.util;

/**
 * Enumeration of task status
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum ETaskStatus {
	Waiting("等待执行"), Running("执行中"), Completed("执行完成"), Failed("执行失败");

	private ETaskStatus(String title) {
		this.title = title;
	}

	private String title;

	public String getTitle() {
		return title;
	}

}
