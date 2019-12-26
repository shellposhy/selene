package com.selene.viewing.api.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.selene.common.config.service.ServiceConfiger;
import com.selene.common.util.RedisClient;
import com.selene.logging.model.Logger;
import com.selene.logging.model.LoggerFactory;

@Component
public class AccessInterceptor implements HandlerInterceptor {
	private static final Logger LOG = LoggerFactory.getLogger(AccessInterceptor.class.getName());
	@Resource
	private ServiceConfiger serviceConfiger;
	@Resource
	private RedisClient redisClient;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		LOG.info("Access Interceptor");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
