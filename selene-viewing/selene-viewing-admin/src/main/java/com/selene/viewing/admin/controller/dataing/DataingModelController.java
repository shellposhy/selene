package com.selene.viewing.admin.controller.dataing;

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

import com.selene.common.Operator;
import com.selene.common.datatable.DataTable;
import com.selene.common.datatable.DataTableArray;
import com.selene.common.datatable.DataTableResult;
import com.selene.common.result.ListResult;
import com.selene.common.util.Containers;
import com.selene.dataing.model.DataingDataField;
import com.selene.dataing.model.DataingDataModel;
import com.selene.dataing.model.enums.EDataFieldType;
import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.service.CommonService;
import com.selene.viewing.admin.service.dataing.DataService;
import com.selene.viewing.admin.vo.merchants.MerchantsUserVO;

@Controller
@RequestMapping("/admin/dataing/model")
public class DataingModelController extends BaseController {

	@Resource
	private DataService dataService;
	@Resource
	private CommonService commonService;

	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "/admin/dataing/model/list";
	}

	@RequestMapping(value = "/s", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue search(@RequestBody DataTableArray[] dataArray, String callback,
			HttpServletRequest request) {
		final MerchantsUserVO vo = commonService.user(request);
		DataTable /* Packaging request parameters */ dataTable = Containers.table(dataArray);
		Integer pageSize = dataTable.getiDisplayLength();
		Integer pageStart = dataTable.getiDisplayStart();
		ListResult<DataingDataModel> list = dataService.findModelByPage(vo.getLicense(), dataTable.getsSearch(),
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

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Validated final DataingDataModel dataModel, BindingResult result, final Model model,
			final HttpServletRequest request) {
		final MerchantsUserVO vo = commonService.user(request);
		return super.save(dataModel, result, model, new Operator() {
			@Override
			public void operate() {
				if (dataModel.getId() == null) {
					dataModel/* Default not system */.setForSystem(false);
					dataModel.setLicense(vo.getLicense());
					dataModel.setCreatorId(vo.getId());
					dataModel.setCreateTime(new Date());
				}
				dataModel.setUpdaterId(vo.getId());
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
