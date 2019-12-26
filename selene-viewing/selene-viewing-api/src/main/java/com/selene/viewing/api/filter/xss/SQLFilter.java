package com.selene.viewing.api.filter.xss;

import org.apache.commons.lang.StringUtils;

/**
 * SQL filter
 * 
 * @author shaobo shih
 */
public class SQLFilter {

	public static String sqlInject(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		str = StringUtils.replace(str, "'", "");
		str = StringUtils.replace(str, "\"", "");
		str = StringUtils.replace(str, ";", "");
		str = StringUtils.replace(str, "\\", "");
		str = str.toLowerCase();
		String[] keywords = { "master", "truncate", "insert", "select", "delete", "update", "declare", "alert",
				"create", "drop" };
		for (String keyword : keywords) {
			if (str.equals(keyword)) {
				try {
					throw new Exception("包含非法字符");
				} catch (Exception e) {
				}
			}
		}

		return str;
	}
}