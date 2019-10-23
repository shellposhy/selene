package com.selene.viewing.admin.controller.templating;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.service.TokenService;
import com.selene.viewing.admin.service.templating.TemplatingService;

@Controller
@RequestMapping("/admin/templating/page/config")
public class TemplatingPageConfigController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(TemplatingPageConfigController.class.getName());
	@Resource
	private TemplatingService templatingService;
	@Resource
	private TokenService commonService;

	@RequestMapping(value = "/{pageId}")
	public String config(@PathVariable Integer pageId, Model model) {
		LOG.debug("[Selene templating]");
		
		return "/admin/templating/page/config";
	}
}
