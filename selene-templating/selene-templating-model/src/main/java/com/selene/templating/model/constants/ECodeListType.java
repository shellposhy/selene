package com.selene.templating.model.constants;

/**
 * Enumeration of ftl list type
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum ECodeListType {
	Default("defaultList", "普通列表"), Image("imageList", "图片列表"), Mixed("mixList", "图文列表"), Catalog("catalogList",
			"导航列表"), Banner("bannerList", "引导列表"), Ad("adList", "广告列表"), Video("videoList", "视频列表");

	/**
	 * Find {@code ECodeListType} on {@code String} code
	 * 
	 * @param code
	 * @return {@code ECodeListType}
	 */
	public static ECodeListType codeOf(String code) {
		for (ECodeListType type : ECodeListType.values()) {
			if (type.getCode().equals(code)) {
				return type;
			}
		}
		return null;
	}

	/**
	 * Find {@code ECodeListType} on {@code String} name
	 * 
	 * @param code
	 * @return {@code ECodeListType}
	 */
	public static ECodeListType nameOf(String name) {
		for (ECodeListType type : ECodeListType.values()) {
			if (type.getName().equals(name)) {
				return type;
			}
		}
		return null;
	}

	private ECodeListType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	private String code;
	private String name;

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

}
