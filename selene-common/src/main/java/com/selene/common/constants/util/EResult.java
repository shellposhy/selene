package com.selene.common.constants.util;

/**
 * Enumeration of result type
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum EResult {
	No(404, "Not Find"), Success(200, "Success"), Fail(100, "Fail"), Null(500, "Null");

	private EResult(int code, String value) {
		this.code = code;
		this.value = value;
	}

	private int code;
	private String value;

	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

}
