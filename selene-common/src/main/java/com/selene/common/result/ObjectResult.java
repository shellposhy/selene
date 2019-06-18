package com.selene.common.result;

import com.selene.common.Result;

/**
 * ObjectResult {@code ObjectResult} information for response
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class ObjectResult<T> extends Result {
	private static final long serialVersionUID = -8094664441302314936L;
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
