package com.selene.viewing.admin.controller.dataing.databse;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.service.CommonService;
import com.selene.viewing.admin.service.dataing.DataService;

@Controller
@RequestMapping("/admin/dataing/library")
public class DataingLibraryController extends BaseController {
	@Resource
	private DataService dataService;
	@Resource
	private CommonService commonService;

	@RequestMapping
	private String list() {
		return "/admin/dataing/library/list";
	}
}
