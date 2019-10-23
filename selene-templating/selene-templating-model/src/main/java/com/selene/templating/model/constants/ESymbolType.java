package com.selene.templating.model.constants;

/**
 * Enumeration of ftl symbol type
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum ESymbolType {
	Number("数字"), Dot("点"), Bracket("中括号"), Dash("横杠"), Date("日期");

	/**
	 * Find {@code ESymbolType} on {@code String} name
	 * 
	 * @param code
	 * @return {@code ESymbolType}
	 */
	public static ESymbolType nameOf(String name) {
		for (ESymbolType type : ESymbolType.values()) {
			if (type.getName().equals(name)) {
				return type;
			}
		}
		return null;
	}

	private ESymbolType(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return name;
	}

}
