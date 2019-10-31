package com.selene.templating.model.constants;

import java.util.ArrayList;
import java.util.List;

import com.selene.common.constants.CommonConstants;
import com.selene.common.constants.FieldsConstants;

/**
 * Enumeration of search index filter type
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum EFilterType {
	Title(1, FieldsConstants.TITLE, "标题"), 
	Author(1, FieldsConstants.AUTHORS, "作者"), 
	Content(1, FieldsConstants.CONTENT,"内容"), 
	TitleAndAuthor(2, FieldsConstants.TITLE + CommonConstants.COMMA_SEPARATOR + FieldsConstants.AUTHORS, "标题+作者"), 
	TitleAndContent(2,FieldsConstants.TITLE + CommonConstants.COMMA_SEPARATOR + FieldsConstants.CONTENT, "标题+内容"), 
	AuthorAndContent(2,FieldsConstants.AUTHORS +CommonConstants.COMMA_SEPARATOR + FieldsConstants.CONTENT,"作者+内容"), 
	All(2, FieldsConstants.TITLE + CommonConstants.COMMA_SEPARATOR + FieldsConstants.AUTHORS +CommonConstants.COMMA_SEPARATOR
			+ FieldsConstants.CONTENT, "标题+作者+内容");

	/**
	 * Fild {@code EFilterType} on {@code Integer} key
	 * 
	 * @param key
	 * @return {@link List}
	 */
	public static List<EFilterType> valueOf(int key) {
		List<EFilterType> result = new ArrayList<EFilterType>();
		for (EFilterType filterType : EFilterType.values()) {
			if (filterType.getKey() == key) {
				result.add(filterType);
			}
		}
		return result;
	}

	private int key;
	private String field;
	private String name;

	private EFilterType(int key, String field, String name) {
		this.key = key;
		this.field = field;
		this.name = name;
	}

	public int getKey() {
		return key;
	}

	public String getField() {
		return field;
	}

	public String getName() {
		return name;
	}

}
