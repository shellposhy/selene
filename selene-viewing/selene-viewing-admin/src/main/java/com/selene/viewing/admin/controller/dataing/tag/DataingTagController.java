package com.selene.viewing.admin.controller.dataing.tag;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.service.CommonService;
import com.selene.viewing.admin.service.dataing.DataService;

@Controller
@RequestMapping("/admin/dataing/tag")
public class DataingTagController extends BaseController {
	@Resource
	private DataService dataService;
	@Resource
	private CommonService commonService;

	@RequestMapping
	public String list() {
		return "/admin/dataing/tag/list";
	}
}
