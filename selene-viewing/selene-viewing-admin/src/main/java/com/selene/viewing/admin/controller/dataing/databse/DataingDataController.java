package com.selene.viewing.admin.controller.dataing.databse;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selene.common.HttpStatus;
import com.selene.common.constants.CommonConstants;
import com.selene.common.constants.CommonFields;
import com.selene.common.datatable.DataTable;
import com.selene.common.datatable.DataTableArray;
import com.selene.common.datatable.DataTableResult;
import com.selene.common.result.ListResult;
import com.selene.common.util.Containers;
import com.selene.dataing.model.DataingDataField;
import com.selene.dataing.model.enums.EAccessType;
import com.selene.dataing.model.jdbc.DataingData;
import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.service.CommonService;
import com.selene.viewing.admin.service.dataing.DataService;

import cn.com.lemon.base.Strings;

@Controller
@RequestMapping("/admin/dataing/library/data")
public class DataingDataController extends BaseController {
	@Resource
	private DataService dataService;
	@Resource
	private CommonService commonService;

	@SuppressWarnings("unused")
	@RequestMapping(value = "/search/{libraryId}", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue search(@PathVariable int libraryId, @RequestBody DataTableArray[] dataArray,
			String callback, HttpServletRequest request) {
		// Parameter process
		DataTable dataTable = Containers.table(dataArray);
		Integer pageSize = dataTable.getiDisplayLength();
		Integer pageStart = dataTable.getiDisplayStart();
		// Data process
		ListResult<DataingData> dataList = new ListResult<DataingData>(HttpStatus.OK.code(), HttpStatus.OK.message());
		DataTableResult<DataingData> result = new DataTableResult<>(dataTable.getsEcho(), 0, 0,
				new ArrayList<DataingData>());
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping("/head/{libraryId}")
	@ResponseBody
	public List<DataingDataField> head(@PathVariable int libraryId) {
		List<DataingDataField> showDataFieldList = dataService.findDisplayFieldsByBaseId(libraryId);
		for (DataingDataField dataField : showDataFieldList) {
			if (CommonFields.TITLE.equals(dataField.getCode())) {
				showDataFieldList.remove(dataField);
				break;
			}
		}
		return showDataFieldList;
	}

	@RequestMapping("/new/{libraryId}")
	public String preNew(@PathVariable Integer libraryId, Model model) {
		List<DataingDataField> libraryFieldList = dataService.findFieldsByBaseId(libraryId);
		DataingData dataVo = new DataingData();
		dataVo.setUuid(Strings.uuid());
		StringBuilder sb = new StringBuilder(200);
		if (libraryFieldList != null && libraryFieldList.size() > 0) {
			for (DataingDataField dataField : libraryFieldList) {
				if (dataField.getAccessType() != EAccessType.System || dataField.getCode().equals("Keywords")) {
					dataVo.putFieldMap(dataField.getCode(), "");
					sb.append(dataField.getCode()).append(CommonConstants.COMMA_SEPARATOR);
				}
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		model.addAttribute("dataVo", dataVo);
		model.addAttribute("baseId", libraryId);
		model.addAttribute("fieldsStr", sb.toString());
		return "/admin/dataing/data/edit";
	}
}
