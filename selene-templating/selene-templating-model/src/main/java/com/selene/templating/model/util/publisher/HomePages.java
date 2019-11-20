package com.selene.templating.model.util.publisher;

import java.util.Map;

import cn.com.lemon.util.stabilize.Staticizing;

import static cn.com.lemon.base.Strings.isNullOrEmpty;

/**
 * Static utilities for home page publisher
 * 
 * @author shih shaobo
 * @version 1.0
 */
public final class HomePages {
	private HomePages() {
	}

	/**
	 * Home page publishing
	 * 
	 * @param ftlDirectory
	 * @param ftlName
	 * @param output
	 * @param data
	 * @return {@code Boolean}
	 */
	public static boolean publish(String ftlDirectory, String ftlName, String output, Map<String, Object> data) {
		if (!isNullOrEmpty(ftlDirectory) && !isNullOrEmpty(ftlName) && !isNullOrEmpty(output) && data != null) {
			return Staticizing.publish(ftlDirectory, ftlName, data, output);
		}
		return false;
	}
}
