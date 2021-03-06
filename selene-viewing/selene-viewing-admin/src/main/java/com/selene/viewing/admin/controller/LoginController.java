package com.selene.viewing.admin.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Strings;
import com.selene.common.constants.CommonConstants;
import com.selene.common.util.RedisClient;
import com.selene.merchants.model.Merchants;
import com.selene.merchants.model.MerchantsLoginToken;
import com.selene.merchants.model.MerchantsUser;
import com.selene.viewing.admin.framework.vo.Customer;
import com.selene.viewing.admin.service.dataing.DataService;
import com.selene.viewing.admin.service.merchants.UserService;

import static cn.com.lemon.base.Strings.md5;

@Controller
@RequestMapping("/admin")
public class LoginController extends BaseController {
	private final static Logger LOG = LoggerFactory.getLogger(LoginController.class.getName());
	@Resource
	private UserService userService;
	@Resource
	private DataService dataService;
	@Resource
	private RedisClient redisClient;

	@RequestMapping("/install/save")
	public String save(@Validated Merchants merchants, BindingResult result, Model model, HttpServletRequest request) {
		merchants/* Set IP */.setIp(cn.com.lemon.base.Strings.ip(request));
		if (userService.saveInstall(merchants) > 0) {
			if (dataService.installTag(merchants.getLicense()) > 0) {
				return "redirect:/admin/login";
			} else {
				model.addAttribute("code", "100");
				model.addAttribute("msg", "首次安装失败，请联系客服人员！");
				return "redirect:/admin/error";
			}
		} else {
			model.addAttribute("code", "100");
			model.addAttribute("msg", "首次安装失败，请联系客服人员！");
			return "redirect:/admin/error";
		}
	}

	@RequestMapping("/install")
	public String install(Model model) {
		model.addAttribute("merchants", new Merchants());
		return "/admin/install";
	}

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
		MerchantsUser merchantsUser = userService.findByNameAndPass(name, md5(password));
		if (null == merchantsUser) {
			return "/admin/login";
		}
		Customer customer = new Customer(merchantsUser);
		// action process
		userService.findMenuTreeByUser(customer);
		customer.setLicense(userService.findLicenseByUserId(merchantsUser.getId()));
		// token process
		MerchantsLoginToken loginToken = userService.token(customer.getId(), customer.getLicense(), true, true);
		customer.setLoginTime(loginToken.getLoginTime());
		customer.setRedisKey(loginToken.getRedisKey());
		customer.setToken(loginToken.getToken());
		customer.setRefreshToken(loginToken.getRefreshToken());
		// redis process
		redisClient.set(customer.getToken(), customer, CommonConstants.DEFAULT_TOKEN_TIMEOUT);
		redisClient.set(customer.getRefreshToken(), customer, CommonConstants.DEFAULT_TOKEN_LONG_TIMEOUT);
		// session
		request.getSession().setAttribute("jsonActionTree", customer.getActionTree());
		request.getSession().setAttribute(CommonConstants.LOGIN_SESSION_USER, customer);
		return "redirect:/admin/index";
	}

	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		LOG.debug("Selene view admin index");
		Customer customer = (Customer) request.getSession()
				.getAttribute(CommonConstants.LOGIN_SESSION_USER);
		if (null == customer) {
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
