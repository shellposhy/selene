package com.selene.viewing.admin.controller.dataing;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selene.common.datatable.DataTable;
import com.selene.common.datatable.DataTableArray;
import com.selene.common.datatable.DataTableResult;
import com.selene.common.result.ListResult;
import com.selene.common.util.Containers;
import com.selene.dataing.model.DataingDataModel;
import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.service.dataing.DataService;

@Controller
@RequestMapping("/admin/dataing/model")
public class DataingModelController extends BaseController {

	@Resource
	private DataService dataService;

	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "/admin/dataing/model/list";
	}

	@RequestMapping(value = "/s", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue search(@RequestBody DataTableArray[] dataArray, String callback,
			HttpServletRequest request) {
		DataTable /* Packaging request parameters */ dataTable = Containers.table(dataArray);
		Integer pageSize = dataTable.getiDisplayLength();
		Integer pageStart = dataTable.getiDisplayStart();
		ListResult<DataingDataModel> list = dataService.findModelByPage(dataTable.getsSearch(), pageStart, pageSize);
		DataTableResult<DataingDataModel> result = new DataTableResult<DataingDataModel>(dataTable.getsEcho(),
				list.getTotal(), list.getTotal(), list.getData());
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}
}
