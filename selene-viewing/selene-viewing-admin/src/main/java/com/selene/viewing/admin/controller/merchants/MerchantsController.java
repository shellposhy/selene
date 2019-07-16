package com.selene.viewing.admin.controller.merchants;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.selene.viewing.admin.controller.BaseController;

@Controller
@RequestMapping("/admin/merchants")
public class MerchantsController extends BaseController {
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		return "/admin/merchants/list";
	}
}
