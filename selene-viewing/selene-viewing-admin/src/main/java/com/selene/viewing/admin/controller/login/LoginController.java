package com.selene.viewing.admin.controller.login;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Strings;
import com.selene.common.constants.CommonConstants;
import com.selene.merchants.model.MerchantsUser;
import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.service.merchants.UserService;

import static cn.com.lemon.base.Strings.MD5;

@Controller
@RequestMapping("/admin")
public class LoginController extends BaseController {
	private final static Logger LOG = LoggerFactory.getLogger(LoginController.class.getName());
	@Resource
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String admin(HttpServletRequest request) {
		if (null == request.getSession().getAttribute(CommonConstants.LOGIN_SESSION_USER)) {
			return "/admin/login";
		}
		return "/admin/index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, Model model) {
		LOG.debug("Selene view admin logining");
		if (!Strings.isNullOrEmpty(request.getParameter("from"))) {
			model.addAttribute("from", request.getParameter("from"));
		}
		return "/admin/login";
	}

	@RequestMapping(value = "/security/check", method = RequestMethod.POST)
	public String excute(HttpServletRequest request) {
		LOG.debug("Selene view admin security check");
		String name = request.getParameter("username");
		String password = request.getParameter("password");
		MerchantsUser merchantsUser = userService.findByNameAndPass(name, MD5(password));
		if (null == merchantsUser) {
			return "/admin/login";
		}
		request.getSession().setAttribute("jsonActionTree", userService.findMenuTreeByUser(merchantsUser));
		request.getSession().setAttribute(CommonConstants.LOGIN_SESSION_USER, merchantsUser);
		if (!Strings.isNullOrEmpty(request.getParameter("from"))) {
			if (!Strings.isNullOrEmpty(request.getParameter("from"))) {
				return "redirect:" + request.getParameter("from");
			}
			return "redirect:" + request.getParameter("from");
		}
		return "redirect:/admin/index";
	}
}
