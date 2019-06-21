package com.selene.common.util;

import java.io.File;
import java.io.IOException;

import jodd.props.Props;

import static cn.com.lemon.base.Preasserts.checkArgument;

/**
 * Properties utilities.
 *
 * @author shaobo shih
 * @version 1.0
 */
public class Properties {
	private Props props = new Props();

	// Properties based on flunt style
	/**
	 * Instantiate the class
	 * 
	 * @return this
	 */
	public static Properties build() {
		return new Properties();
	}

	public Properties file(File file) {
		checkArgument(file != null && file.exists() && file.isFile());
		try {
			getProps().load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public String value(String key) {
		return getProps().getValue(key);
	}

	public Props getProps() {
		return props;
	}

}
