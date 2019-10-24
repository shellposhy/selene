package com.selene.viewing.admin.controller.dataing.model;

import static cn.com.lemon.base.Strings.split;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selene.common.HttpStatus;
import com.selene.common.Operator;
import com.selene.common.constants.CommonConstants;
import com.selene.common.constants.util.EDataFieldType;
import com.selene.common.datatable.DataTable;
import com.selene.common.datatable.DataTableArray;
import com.selene.common.datatable.DataTableResult;
import com.selene.common.result.ListResult;
import com.selene.common.result.ObjectResult;
import com.selene.common.util.Containers;
import com.selene.dataing.model.DataingDataField;
import com.selene.dataing.model.DataingDataModel;
import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.framework.vo.Customer;
import com.selene.viewing.admin.service.TokenService;
import com.selene.viewing.admin.service.dataing.DataService;

@Controller
@RequestMapping("/admin/dataing/model")
public class DataingModelController extends BaseController {

	@Resource
	private DataService dataService;
	@Resource
	private TokenService commonService;

	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "/admin/dataing/model/list";
	}

	@RequestMapping(value = "/s", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue search(@RequestBody DataTableArray[] dataArray, String callback,
			HttpServletRequest request) {
		final Customer customer = commonService.user(request);
		DataTable /* Packaging request parameters */ dataTable = Containers.table(dataArray);
		Integer pageSize = dataTable.getiDisplayLength();
		Integer pageStart = dataTable.getiDisplayStart();
		ListResult<DataingDataModel> list = dataService.findModelByPage(customer.getLicense(), dataTable.getsSearch(),
				pageStart, pageSize);
		DataTableResult<DataingDataModel> result = new DataTableResult<DataingDataModel>(dataTable.getsEcho(),
				list.getTotal(), list.getTotal(), list.getData());
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String preNew(Model model) {
		List<DataingDataField> /* Fields can be provided a selection */ allFieldList = dataService
				.findFieldByType(EDataFieldType.Normal);
		model.addAttribute("dataModel", new DataingDataModel());
		model.addAttribute("allFields", allFieldList);
		return "/admin/dataing/model/edit";
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Integer id, Model model) {
		DataingDataModel dataModel = dataService.findModelById(id);
		List<DataingDataField> /* Fields can be provided a selection */ allFieldList = dataService
				.findFieldByType(EDataFieldType.Normal);
		model.addAttribute("dataModel", dataModel);
		model.addAttribute("allFields", allFieldList);
		return "/admin/dataing/model/edit";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue delete(@RequestBody DataTableArray value, String callback, HttpServletRequest request) {
		ObjectResult<String> result = new ObjectResult<String>();
		final Customer customer = commonService.user(request);
		String[] /* Models need to deleted. */ modelIds = split(CommonConstants.COMMA_SEPARATOR, value.value);
		boolean isHasDatabase = false;
		for (String modelId : modelIds) {
			if (/* Make sure the model is not in use before deleting it. */dataService
					.checkPreDeleteModel(customer.getLicense(), Integer.valueOf(modelId))) {
				isHasDatabase = true;
				break;
			}
		}
		if (isHasDatabase) {
			result.setCode(HttpStatus.ERROR.code());
			result.setMsg("数据模型删除前，请确保该模型没有被数据库使用！");
		} else {
			for (String modelId : modelIds) {
				dataService.deleteModel(Integer.valueOf(modelId));
			}
			result.setCode(HttpStatus.OK.code());
		}
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Validated final DataingDataModel dataModel, BindingResult result, final Model model,
			final HttpServletRequest request) {
		final Customer customer = commonService.user(request);
		return super.save(dataModel, result, model, new Operator() {
			@Override
			public void operate() {
				if (dataModel.getId() == null) {
					dataModel/* Default not system */.setForSystem(false);
					dataModel.setLicense(customer.getLicense());
					dataModel.setCreatorId(customer.getId());
					dataModel.setCreateTime(new Date());
				}
				dataModel.setUpdaterId(customer.getId());
				dataModel.setUpdateTime(new Date());
				dataService.saveDataModel(dataModel);
			}

			@Override
			public String success() {
				return "redirect:/admin/dataing/model";
			}

			@Override
			public String fail() {
				model.addAttribute("code", "100");
				model.addAttribute("msg", "数据模板保存或修改出错！");
				return "redirect:/admin/error";
			}

			@Override
			public void error() {
			}
		});
	}
}
