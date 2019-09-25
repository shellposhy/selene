package com.selene.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import static cn.com.lemon.base.Strings.isNullOrEmpty;

/**
 * The utilities for chinese pinyin process
 * 
 * @author shaobo shih
 * @version 1.0
 */
public final class Hanyus {
	private static HanyuPinyinOutputFormat format = null;

	static {
		format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
	}

	public static String upper(String content) {
		return parse(content, "", Type.UPPER);
	}

	public static String upper(String content, String separator) {
		return parse(content, separator, Type.UPPER);
	}

	public static String lower(String content) {
		return parse(content, "", Type.LOWER);
	}

	public static String lower(String content, String separator) {
		return parse(content, separator, Type.LOWER);
	}

	public static String first(String content, Type type) {
		String result = null;
		String py = ((type == Type.UPPER) ? upper(content) : (type == Type.LOWER) ? lower(content) : upper(content));
		if (py.length() > 1) {
			result = py.substring(0, 1);
		}
		if (py.length() <= 1) {
			result = py;
		}
		return result.trim();
	}

	private static String parse(String content, String separator, Type type) {
		if (isNullOrEmpty(content)) {
			return "";
		}
		if (type == Type.UPPER) {
			format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		} else {
			format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		}
		String py = "";
		String temp = "";
		String[] t;
		try {
			for (int i = 0; i < content.length(); i++) {
				char c = content.charAt(i);
				if ((int) c <= 128) {
					py += c;
				} else {
					t = PinyinHelper.toHanyuPinyinStringArray(c, format);
					if (t == null) {
						py += c;
					} else {
						temp = t[0];
						if (type == Type.FIRSTUPPER) {
							temp = t[0].toUpperCase().charAt(0) + temp.substring(1);
						}
						if (temp.length() >= 1) {
							temp = temp.substring(0, 1);
						}
						py += temp + (i == content.length() - 1 ? "" : separator);
					}
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			return null;
		}
		return py.trim();
	}

	public static enum Type {
		UPPER, // upper case
		LOWER, // lower case
		FIRSTUPPER // first upper
	}

	private Hanyus() {
	}
}
