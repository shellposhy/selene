package com.selene.viewing.api.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import com.selene.viewing.api.filter.warpper.XssHttpServletRequestWrapper;

import java.io.IOException;

/**
 * XSS filter
 * 
 * @author shaobo shih
 */
public class XssFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
		chain.doFilter(xssRequest, response);
	}

	@Override
	public void destroy() {
	}

}
