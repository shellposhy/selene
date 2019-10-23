package com.selene.templating.model.constants;

/**
 * Enumeration of ftl symbol position
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum ESymbolPosition {
	Prefix("前缀"), Suffix("后缀");

	/**
	 * Find {@code ESymbolPosition} on {@code String} name
	 * 
	 * @param code
	 * @return {@code ESymbolPosition}
	 */
	public static ESymbolPosition nameOf(String name) {
		for (ESymbolPosition position : ESymbolPosition.values()) {
			if (position.getName().equals(name)) {
				return position;
			}
		}
		return null;
	}

	private String name;

	private ESymbolPosition(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
