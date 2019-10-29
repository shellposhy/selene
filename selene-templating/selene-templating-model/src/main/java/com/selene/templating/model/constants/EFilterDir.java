package com.selene.templating.model.constants;

/**
 * Enumeration of search index filter directory
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum EFilterDir {
	Basic(1, "基础搜索"), Combine(2, "组合搜索");

	private int index;
	private String value;

	public static EFilterDir valueOfName(String name) {
		try {
			EFilterDir filterDir = EFilterDir.valueOf(name);
			return filterDir;
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	private EFilterDir(int index, String value) {
		this.index = index;
		this.value = value;
	}

	public int getIndex() {
		return index;
	}

	public String getValue() {
		return value;
	}
}
