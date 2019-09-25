package com.selene.common.util;

import java.util.List;

import com.selene.common.constants.FieldsConstants;
import com.selene.common.constants.util.EFieldSearchType;

import cn.com.lemon.base.Strings;

/**
 * The utilities for search engine highlight class
 * 
 * @author shaobo shih
 * @version 1.0
 */
public final class Lucenes {
	private final static String[] ALL_HIGH_LIGHT = { FieldsConstants.TITLE, FieldsConstants.CONTENT,
			FieldsConstants.AUTHORS, FieldsConstants.SUMMARY, FieldsConstants.KEYWORDS };
	private final static String[] DEFAULT_HIGH_LIGHT = { FieldsConstants.TITLE };
	private final static String[] CONETNT_HIGH_LIGHT = { FieldsConstants.CONTENT };
	private final static String[] AUTHORS_HIGH_LIGHT = { FieldsConstants.AUTHORS };
	private final static String[] SUMMARY_HIGH_LIGHT = { FieldsConstants.SUMMARY };
	private final static String[] KEYWORDS_HIGH_LIGHT = { FieldsConstants.KEYWORDS };

	private Lucenes() {
	}

	public static String[] light(EFieldSearchType searchType) {
		String[] result = DEFAULT_HIGH_LIGHT;
		if (searchType != null) {
			switch (searchType) {
			case All:
				result = ALL_HIGH_LIGHT;
				break;
			case Title:
				result = DEFAULT_HIGH_LIGHT;
				break;
			case Content:
				result = CONETNT_HIGH_LIGHT;
				break;
			case Keywords:
				result = KEYWORDS_HIGH_LIGHT;
				break;
			case Authors:
				result = AUTHORS_HIGH_LIGHT;
				break;
			case Summary:
				result = SUMMARY_HIGH_LIGHT;
				break;
			default:
				result = DEFAULT_HIGH_LIGHT;
				break;
			}
		}
		return result;
	}

	/**
	 * Jion the query string according to the query condition
	 * 
	 * @param andList
	 * @param orList
	 * @param notList
	 * @return {@code String} Query string
	 */
	public static String jion(List<String> andList, List<String> orList, List<String> notList) {
		if (/* All is null */andList == null && orList == null && notList == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.setLength(0);
		if (/* AND list */andList.size() > 0) {
			/** And pre condition */
			sb.append("(");
			/** And condition process */
			for (int i = 0; i < andList.size(); i++) {
				if (i == 0) {
					sb.append(andList.get(i));
				} else {
					sb.append(" AND ").append(andList.get(i));
				}
			}
			sb.append(")");
		}
		if (/* OR list */orList.size() > 0) {
			/** Or pre condition */
			if (Strings.isNullOrEmpty(sb.toString().trim())) {
				sb.append("(");
			} else {
				sb.append(" OR (");
			}
			/** Or condition process */
			for (int i = 0; i < orList.size(); i++) {
				if (i == 0) {
					sb.append(orList.get(i));
				} else {
					sb.append(" OR ").append(orList.get(i));
				}
			}
			sb.append(")");
		}
		if (/* NOT list */notList.size() > 0) {
			/** NOT pre condition */
			if (Strings.isNullOrEmpty(sb.toString().trim())) {
				sb.append("(");
			} else {
				sb.append(" NOT (");
			}
			/** NOT condition process */
			for (int i = 0; i < notList.size(); i++) {
				if (i == 0) {
					sb.append(notList.get(i));
				} else {
					sb.append(" AND ").append(notList.get(i));
				}
			}
			sb.append(")");
		}
		return sb.toString();
	}
}
