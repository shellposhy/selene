package com.selene.common.util;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTML.Attribute;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.parser.ParserDelegator;

import com.selene.common.constants.CommonConstants;
import com.selene.common.constants.util.EDataType;

import static cn.com.lemon.base.DateUtil.parse;
import static cn.com.lemon.base.Strings.isNullOrEmpty;

/**
 * The utilities for data transfer process
 * 
 * @author shaobo shih
 * @version 1.0
 */
public final class DataUtil {

	private DataUtil() {
	}

	public static Object defaultValue(EDataType dataType, boolean isNotNull) {
		switch (dataType) {
		case Date:
		case DateTime:
		case Time:
			return isNotNull ? new Date() : null;
		case Short:
			return (short) 0;
		case Int:
			return 0;
		case Long:
			return (long) 0;
		case Float:
			return (float) 0;
		case Double:
		case Numeric:
			return (double) 0;
		case Bool:
			return false;
		case Blob:
		case MediumBlob:
		case Char:
		case Varchar:
		case UUID:
		default:
			return "";
		}
	}

	/**
	 * Get data{@code Object} objects based on values and data types
	 * 
	 * @param value
	 * @param dataType
	 * @return {@code Object}
	 */
	public static Object dataTypeObject(String value, EDataType dataType) {
		switch (dataType) {
		case Date:
		case DateTime:
		case Time:
			if (isNullOrEmpty(value)) {
				return null;
			} else {
				return value.trim().length() > 10 ? parse(value, "yyyy-MM-dd HH:mm:ss") : parse(value, "yyyy-MM-dd");
			}
		case Short:
			return isNullOrEmpty(value) ? null : Short.valueOf(value);
		case Int:
			return isNullOrEmpty(value) ? 0 : Integer.valueOf(value);
		case Long:
			return isNullOrEmpty(value) ? null : Long.valueOf(value);
		case Float:
			return isNullOrEmpty(value) ? null : Float.valueOf(value);
		case Double:
		case Numeric:
			return isNullOrEmpty(value) ? null : Double.valueOf(value);
		case Bool:
			return isNullOrEmpty(value) ? null : Boolean.valueOf(value);
		case Blob:
		case MediumBlob:
			// return new SerialBlob(value.getBytes("utf-8"));
		case Char:
		case Varchar:
		case UUID:
		default:
			return value;
		}
	}

	/**
	 * Get {@code List} list files name
	 * 
	 * @param files
	 * @return {@code String}
	 */
	public static String names(List<File> files) {
		if (files != null && files.size() > 0) {
			StringBuffer result = new StringBuffer(200);
			for (File file : files) {
				result.append(file.getName()).append(CommonConstants.COMMA_SEPARATOR);
			}
			result.deleteCharAt(result.length() - 1);
			return result.toString();
		}
		return "";
	}

	/**
	 * Get all image paths for rich text.
	 * 
	 * @param content
	 * @return
	 */
	public static List<String> imgs(String content) {
		final List<String> imgs = new LinkedList<String>();
		if (null == content || content.isEmpty()) {
			return null;
		}
		final Reader reader = new StringReader(content);
		ParserDelegator pd = new ParserDelegator();
		try {
			pd.parse(reader, new HTMLEditorKit.ParserCallback() {
				@Override
				public void handleSimpleTag(Tag t, MutableAttributeSet a, int pos) {
					if (Tag.IMG.equals(t)) {
						imgs.add((String) a.getAttribute(Attribute.SRC));
					}
				}
			}, false);
		} catch (IOException e) {
			return null;
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return imgs.size() > 0 ? imgs : null;
	}
}
