package com.selene.templating.model.constants;

/**
 * Enumeration of ftl ecode attribute
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum ECodeAttribute {
	Line("行数"), Length("长度"), Width("宽度"), Height("高度"), Symbol("符号"), Place("符号位置"), Pattern("符号类型");

	/**
	 * Find {@code ECodeAttribute} on {@code String} name
	 * 
	 * @param code
	 * @return {@code ECodeAttribute}
	 */
	public static ECodeAttribute nameOf(String name) {
		for (ECodeAttribute attribute : ECodeAttribute.values()) {
			if (attribute.getName().equals(name)) {
				return attribute;
			}
		}
		return null;
	}

	private ECodeAttribute(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return name;
	}
}
