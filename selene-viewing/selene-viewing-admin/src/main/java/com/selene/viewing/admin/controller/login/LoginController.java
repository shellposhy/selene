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
import com.selene.common.token.login.LoginToken;
import com.selene.common.util.RedisClients;
import com.selene.merchants.model.MerchantsUser;
import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.service.merchants.UserService;
import com.selene.viewing.admin.vo.merchants.MerchantsUserVO;

import static cn.com.lemon.base.Strings.MD5;

@Controller
@RequestMapping("/admin")
public class LoginController extends BaseController {
	private final static Logger LOG = LoggerFactory.getLogger(LoginController.class.getName());
	@Resource
	private UserService userService;
	@Resource
	private RedisClients redisClient;

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
		// login process
		String name = request.getParameter("username");
		String password = request.getParameter("password");
		MerchantsUser merchantsUser = userService.findByNameAndPass(name, MD5(password));
		if (null == merchantsUser) {
			return "/admin/login";
		}
		MerchantsUserVO vo = new MerchantsUserVO(merchantsUser);
		// action process
		userService.findMenuTreeByUser(vo);
		vo.setLicense(userService.findLicenseByUserId(merchantsUser.getId()));
		// token process
		String jwt = new LoginToken().encrypt(vo.getLicense(), vo.getId().toString(), vo.getLicense());
		System.out.println(jwt);
		request.getSession().setAttribute("jsonActionTree", vo.getActionTree());
		request.getSession().setAttribute(CommonConstants.LOGIN_SESSION_USER, vo);
		if (!Strings.isNullOrEmpty(request.getParameter("from"))) {
			if (!Strings.isNullOrEmpty(request.getParameter("from"))) {
				return "redirect:" + request.getParameter("from");
			}
			return "redirect:" + request.getParameter("from");
		}
		return "redirect:/admin/index";
	}

	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		LOG.debug("Selene view admin index");
		MerchantsUserVO currentUser = (MerchantsUserVO) request.getSession()
				.getAttribute(CommonConstants.LOGIN_SESSION_USER);
		if (null == currentUser) {
			return "/admin/login";
		} else {
			return "/admin/index";
		}
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		LOG.debug("Selene view admin logout");
		request.getSession().setAttribute(CommonConstants.LOGIN_SESSION_USER, null);
		return "/admin/login";
	}
}
