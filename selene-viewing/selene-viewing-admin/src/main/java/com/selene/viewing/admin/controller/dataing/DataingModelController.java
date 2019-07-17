package com.selene.viewing.admin.controller.dataing;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selene.common.datatable.DataTable;
import com.selene.common.datatable.DataTableArray;
import com.selene.common.util.Containers;
import com.selene.viewing.admin.controller.BaseController;

@Controller
@RequestMapping("/admin/dataing/model")
public class DataingModelController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "/admin/dataing/model/list";
	}

	@RequestMapping(value = "/s", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue search(@RequestBody DataTableArray[] dataArray, String callback,
			HttpServletRequest request) {
		DataTable dataTable = Containers.table(dataArray);
		Integer pageSize = dataTable.getiDisplayLength();
		Integer pageStart = dataTable.getiDisplayStart();
		return null;
	}
}
