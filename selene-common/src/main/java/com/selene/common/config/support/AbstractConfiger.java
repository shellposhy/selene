package com.selene.common.config.support;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import com.selene.common.config.Configer;

import cn.com.lemon.base.Strings;
import jodd.props.Props;
import jodd.props.PropsEntry;

/**
 * Basic implementation for {@code Configer}.
 * 
 * @author shaobo shih
 * @version 1.0
 */
public abstract class AbstractConfiger implements Configer {
	private Props props = null;
	private final static String CHARSET = "UTF-8";

	public AbstractConfiger(String path) {
		if (!Strings.isNullOrEmpty(path)) {
			File file = new File(path);
			if (file.exists() && file.isFile() && file.canRead()) {
				try {
					props = new Props();
					props.load(file, CHARSET);
				} catch (IOException e) {
					props = null;
				}
			}
		}
	}

	/**
	 * Returns all keys of property, using active profiles.
	 * 
	 * @return
	 */
	@Override
	public String[] keys() {
		if (props != null) {
			List<String> list = new ArrayList<String>();
			Iterator<PropsEntry> it = props.entries().iterator();
			while (it.hasNext()) {
				list.add(it.next().getKey());
			}
			return list.size() > 0 ? list.toArray(new String[list.size()]) : null;
		}
		return null;
	}

	/**
	 * Returns value of property, using active profiles.
	 * 
	 * @param key
	 *            key
	 * @return {@code String} the value of key
	 */
	@Override
	public String value(String key) {
		return props.getValue(key);
	}

	/**
	 * Regular expression:
	 * <p>
	 * matches all the key attributes that begin with specified {@link divisor}
	 * string.
	 * 
	 * @param divisor
	 *            the specified string
	 * @param keys
	 *            all properties keys
	 * @return
	 */
	@Override
	public String[] match(String divisor, String... keys) {
		if (keys != null && keys.length > 0) {
			List<String> list = new ArrayList<String>();
			for (String key : keys) {
				if (Pattern.matches("^" + divisor + ".*", key)) {
					list.add(key);
				}
			}
			return list.size() > 0 ? list.toArray(new String[list.size()]) : null;
		}
		return null;
	}
}
