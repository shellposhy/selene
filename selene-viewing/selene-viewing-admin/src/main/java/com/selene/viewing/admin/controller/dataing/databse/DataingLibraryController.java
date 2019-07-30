package com.selene.viewing.admin.controller.dataing.databse;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selene.common.HttpStatus;
import com.selene.common.Operator;
import com.selene.common.constants.util.EDataStatus;
import com.selene.common.constants.util.ELibraryNodeType;
import com.selene.common.constants.util.ELibraryType;
import com.selene.common.result.ListResult;
import com.selene.dataing.model.DataingDataField;
import com.selene.dataing.model.DataingDataModel;
import com.selene.dataing.model.DataingDatabase;
import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.service.CommonService;
import com.selene.viewing.admin.service.dataing.DataService;
import com.selene.viewing.admin.vo.merchants.MerchantsUserVO;

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

	@RequestMapping(value = "/find/{id}", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue tree(@PathVariable("id") Integer id, String callback, HttpServletRequest request) {
		ListResult<DataingDatabase> result = new ListResult<DataingDatabase>(HttpStatus.OK.code(),
				HttpStatus.OK.message());
		MerchantsUserVO vo = commonService.user(request);
		List<DataingDatabase> list = dataService.findBaseByParentId(vo.getLicense(), ELibraryType.Default, id);
		result.setData(list);
		result.setTotal(list.size());
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping(value = "/show/{modelId}")
	@ResponseBody
	public MappingJacksonValue show(@PathVariable("modelId") Integer modelId, String callback) {
		ListResult<DataingDataField> result = new ListResult<DataingDataField>(HttpStatus.OK.code(),
				HttpStatus.OK.message());
		List<DataingDataField> list = dataService.findDisplayFieldsByModelId(modelId);
		result.setData(list);
		result.setTotal(list.size());
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping("/{directoryId}/new")
	public String preNew(@PathVariable("directoryId") Integer directoryId, HttpServletRequest request, Model model) {
		DataingDatabase library = new DataingDatabase();
		MerchantsUserVO vo = commonService.user(request);
		List<DataingDataModel> modelList = dataService.findModelByType(ELibraryType.Default, vo.getLicense());
		library.setParentId(directoryId);
		library.setType(ELibraryType.Default);
		model.addAttribute("modelList", modelList);
		model.addAttribute("library", library);
		return "/admin/dataing/library/edit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Validated final DataingDatabase library, BindingResult result, final Model model,
			HttpServletRequest request) {
		final MerchantsUserVO vo = commonService.user(request);
		return super.save(library, result, model, new Operator() {
			@Override
			public void operate() {
				if (/* New database */library.getId() == null) {
					library.setCreatorId(vo.getId());
					library.setCreateTime(new Date());
					library.setType(ELibraryType.Default);
					library.setNodeType(ELibraryNodeType.Lib);
					library.setTables(0);
					library.setStatus(EDataStatus.Normal);
				}
				library.setLicense(vo.getLicense());
				library.setUpdaterId(vo.getId());
				library.setUpdateTime(new Date());
				dataService.saveLibrary(/* Save process */library);
			}

			@Override
			public String success() {
				return "redirect:/admin/dataing/library";
			}

			@Override
			public String fail() {
				List<DataingDataModel> modelList = dataService.findModelByType(ELibraryType.Default, vo.getLicense());
				model.addAttribute("modelList", modelList);
				model.addAttribute("library", library);
				return "/admin/dataing/library/edit";
			}

			@Override
			public void error() {
			}
		});
	}
}
