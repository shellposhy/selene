package com.selene.viewing.admin.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.selene.common.constants.CommonConstants;

/**
 * 拦截用户初次请求，检查用户请求，以及非法请求等
 * 
 * @author shishb
 * @version 1.0
 */
public class BaseFilter implements Filter {

	FilterConfig filterConfig;

	private static final String[] staticResourceType = { ".css", ".js", ".ico", ".jpg", ".jpeg", ".gif", ".html",
			".png", ".doc", ".pdf", ".docx", ".ppt", ".pptx", ".xls", ".xlsx" };
	private static final String[] staticResourcePath = { "/static", "/default", "/pic", "/tmp", "/doc" };
	private static final String[] excludePath = { "/admin/security/check", "/admin/logout", "/admin/login",
			"/admin/install", "/admin/install/save", "/admin/task/progress" };

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		servletRequest.setAttribute("appName", "内容管理平台");
		String uri = servletRequest.getRequestURI();
		if ("/".equals(uri) || matchExcludePath(uri) || matchStaticPath(uri) || matchStaticResource(uri)) {
			chain.doFilter(request, response);
		} else {
			if (null != servletRequest.getSession().getAttribute(CommonConstants.LOGIN_SESSION_USER)) {
				chain.doFilter(request, response);
			} else {
				servletRequest.getRequestDispatcher("/admin/login?from=" + uri).forward(request, response);
			}
		}
	}

	private boolean matchStaticResource(String uri) {
		boolean result = false;
		for (String str : staticResourceType) {
			if (uri.contains(str)) {
				result = true;
				break;
			}
		}
		return result;
	}

	private boolean matchStaticPath(String uri) {
		boolean result = false;
		for (String str : staticResourcePath) {
			if (uri.contains(str)) {
				result = true;
				break;
			}
		}
		return result;
	}

	private boolean matchExcludePath(String uri) {
		boolean result = false;
		for (String str : excludePath) {
			if (uri.contains(str)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;
	}

	public void destroy() {
		this.filterConfig = null;
	}

}
