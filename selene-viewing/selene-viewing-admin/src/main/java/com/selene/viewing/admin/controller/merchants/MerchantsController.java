package com.selene.viewing.admin.controller.merchants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.selene.viewing.admin.controller.BaseController;

@Controller
@RequestMapping("/admin/merchants")
public class MerchantsController extends BaseController {
	private final static Logger LOG = LoggerFactory.getLogger(MerchantsController.class.getName());

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		LOG.debug("Selene view admin merchants list");
		return "/admin/merchants/list";
	}
}
