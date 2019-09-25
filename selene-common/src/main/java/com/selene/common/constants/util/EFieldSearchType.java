package com.selene.common.constants.util;

import cn.com.lemon.base.Strings;

public enum EFieldSearchType {
	All("All"), Title("Title"), Content("Content"), Keywords("Keywords"), Summary("Summary"), Authors("Authors");

	private String code;

	private EFieldSearchType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static EFieldSearchType codeOf(String code) {
		if (!Strings.isNullOrEmpty(code)) {
			for (EFieldSearchType searchType : EFieldSearchType.values()) {
				if (code.equals(searchType.getCode())) {
					return searchType;
				}
			}
		}
		return null;
	}

}
