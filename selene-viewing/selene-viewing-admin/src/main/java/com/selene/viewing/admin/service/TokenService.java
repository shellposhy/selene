package com.selene.viewing.admin.service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.selene.common.constants.CommonConstants;
import com.selene.common.util.RedisClient;
import com.selene.viewing.admin.framework.vo.Customer;

import static cn.com.lemon.base.Strings.isNullOrEmpty;

@Service
public class TokenService {
	private static final Logger LOG = LoggerFactory.getLogger(TokenService.class.getName());
	@Resource
	private RedisClient redisClient;

	public Customer user(HttpServletRequest request) {
		String token = request.getParameter("token");
		if (isNullOrEmpty(token)) {
			token = request.getHeader("token");
		}
		if (!isNullOrEmpty(token)) {
			LOG.info("token=" + token);
			return (Customer) redisClient.get(token);
		}
		return (Customer) request.getSession().getAttribute(CommonConstants.LOGIN_SESSION_USER);
	}
}
