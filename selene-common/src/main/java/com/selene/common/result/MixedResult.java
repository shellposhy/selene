package com.selene.common.result;

import com.selene.common.Result;

/**
 * MixResult {@code MixResult} information for response
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class MixedResult<K, V> extends Result {
	private static final long serialVersionUID = 6664215491805326644L;
	private K key;
	private V value;

	public MixedResult() {
	}

	public MixedResult(int code, String msg) {
		super(code, msg);
	}

	public MixedResult(int code, String msg, K k, V v) {
		super(code, msg);
		this.key = k;
		this.value = v;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

}
