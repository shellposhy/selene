package com.selene.viewing.api.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.selene.viewing.api.constants.Contants;
import com.selene.viewing.api.filter.warpper.HeaderRequestWrapper;
import com.selene.viewing.api.filter.warpper.ParameterRequestWrapper;

import cn.com.lemon.base.Strings;

public class BaseFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		// Initialization parameter
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HeaderRequestWrapper header = new HeaderRequestWrapper(request);
		ParameterRequestWrapper parameter = new ParameterRequestWrapper(request, request.getParameterMap());
		// Parameter checking
		if (!Strings.isNullOrEmpty(parameter.getParameter(Contants.TOKEN))) {
			header.addHeader(Contants.TOKEN, parameter.getParameter(Contants.TOKEN));
		}
		if (!Strings.isNullOrEmpty(parameter.getParameter(Contants.REFRESH_TOKEN))) {
			header.addHeader(Contants.REFRESH_TOKEN, parameter.getParameter(Contants.REFRESH_TOKEN));
		}
		// Cross-origin resource sharing
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		chain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {

	}
}
