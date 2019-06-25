package com.selene.viewing.admin.framework.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.web.util.HtmlUtils;

/**
 * This is a support class to help build html property editors.
 */
public class HtmlEscapeEditor extends PropertyEditorSupport {

	public HtmlEscapeEditor() {
		super();
	}

	public void setAsText(String text) {
		if (text == null) {
			setValue(null);
		} else {
			setValue(HtmlUtils.htmlEscape(text));
		}
	}

	public String getAsText() {
		Object value = getValue();
		return value != null ? HtmlUtils.htmlUnescape(value.toString()) : "";
	}
}
