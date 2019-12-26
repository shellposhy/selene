package com.selene.viewing.api.filter.warpper;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.selene.viewing.api.filter.xss.HTMLFilter;
import com.selene.viewing.api.filter.xss.SQLFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.LinkedHashMap;
import java.util.Map;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	HttpServletRequest orgRequest;
	// html filter
	private final static HTMLFilter htmlFilter = new HTMLFilter();

	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		orgRequest = request;
	}

	@Override
	public String getParameter(String name) {
		String value = super.getParameter(xssEncode(name));
		if (StringUtils.isNotBlank(value)) {
			value = xssEncode(value);
		}
		// SQL check
		value = SQLFilter.sqlInject(value);
		return StringEscapeUtils.unescapeHtml(value);
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] parameters = super.getParameterValues(name);
		if (parameters == null || parameters.length == 0) {
			return null;
		}
		for (int i = 0; i < parameters.length; i++) {
			parameters[i] = xssEncode(parameters[i]);
			// SQL check
			// parameters[i] = SQLFilter.sqlInject(parameters[i]);
			parameters[i] = SQLFilter.sqlInject(parameters[i]);
			parameters[i] = StringEscapeUtils.unescapeHtml(parameters[i]);
		}
		return parameters;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> map = new LinkedHashMap<>();
		Map<String, String[]> parameters = super.getParameterMap();
		for (String key : parameters.keySet()) {
			String[] values = parameters.get(key);
			for (int i = 0; i < values.length; i++) {
				values[i] = xssEncode(values[i]);
				// SQL check
				// values[i] = SQLFilter.sqlInject(values[i]);
				values[i] = SQLFilter.sqlInject(values[i]);
				values[i] = StringEscapeUtils.unescapeHtml(values[i]);
			}
			map.put(key, values);
		}
		return map;
	}

	@Override
	public String getHeader(String name) {
		String value = super.getHeader(xssEncode(name));
		if (StringUtils.isNotBlank(value)) {
			value = xssEncode(value);
		}
		// SQL check
		// value = SQLFilter.sqlInject(value);
		value = SQLFilter.sqlInject(value);
		return StringEscapeUtils.unescapeHtml(value);
	}

	private String xssEncode(String input) {
		return htmlFilter.filter(input);
	}

	public HttpServletRequest getOrgRequest() {
		return orgRequest;
	}

	public static HttpServletRequest getOrgRequest(HttpServletRequest request) {
		if (request instanceof XssHttpServletRequestWrapper) {
			return ((XssHttpServletRequestWrapper) request).getOrgRequest();
		}
		return request;
	}

}