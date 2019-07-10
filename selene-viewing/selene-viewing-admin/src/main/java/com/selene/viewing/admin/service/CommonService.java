package com.selene.viewing.admin.service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.selene.common.constants.CommonConstants;
import com.selene.common.util.RedisClient;
import com.selene.viewing.admin.vo.merchants.MerchantsUserVO;

import static cn.com.lemon.base.Strings.isNullOrEmpty;

@Service
public class CommonService {
	private static final Logger LOG = LoggerFactory.getLogger(CommonService.class.getName());
	@Resource
	private RedisClient redisClient;

	public MerchantsUserVO user(HttpServletRequest request) {
		String token = request.getParameter("token");
		if (isNullOrEmpty(token)) {
			token = request.getHeader("token");
		}
		if (!isNullOrEmpty(token)) {
			LOG.info("token=" + token);
			return (MerchantsUserVO) redisClient.get(token);
		}
		return (MerchantsUserVO) request.getSession().getAttribute(CommonConstants.LOGIN_SESSION_USER);
	}
}
