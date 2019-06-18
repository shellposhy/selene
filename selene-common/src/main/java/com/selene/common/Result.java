package com.selene.common;

import java.io.Serializable;

/**
 * Result {@code Result} information for response
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class Result implements Serializable {
	private static final long serialVersionUID = -4571438264061689961L;
	private String code;
	private String msg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
