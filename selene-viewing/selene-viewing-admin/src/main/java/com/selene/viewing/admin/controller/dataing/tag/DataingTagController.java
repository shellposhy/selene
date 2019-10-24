package com.selene.viewing.admin.controller.dataing.tag;

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
import com.selene.common.constants.util.EDataStatus;
import com.selene.common.datatable.DataTable;
import com.selene.common.datatable.DataTableArray;
import com.selene.common.result.ObjectResult;
import com.selene.common.tree.DefaultTreeNode;
import com.selene.common.util.Containers;
import com.selene.dataing.model.DataingDataTag;
import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.framework.vo.Customer;
import com.selene.viewing.admin.service.TokenService;
import com.selene.viewing.admin.service.dataing.DataService;

@Controller
@RequestMapping("/admin/dataing/tag")
public class DataingTagController extends BaseController {
	@Resource
	private DataService dataService;
	@Resource
	private TokenService commonService;

	@RequestMapping
	public String list() {
		return "/admin/dataing/tag/list";
	}

	@RequestMapping(value = "/s", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue search(@RequestBody DataTableArray[] dataArray, String callback,
			HttpServletRequest request) {
		final Customer customer = commonService.user(request);
		DataTable /* Packaging request parameters */ dataTable = Containers.table(dataArray);
		String word = dataTable.getsSearch();
		List<DataingDataTag> list = dataService.findTag(customer.getLicense(), word);
		DefaultTreeNode result = DefaultTreeNode.parseTree(list);
		result.name = "根分类";
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping(value = "/custom/s", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue tree(String callback, HttpServletRequest request) {
		final Customer customer = commonService.user(request);
		List<DataingDataTag> list = dataService.findTagByType(customer.getLicense(), 1);
		DefaultTreeNode result = DefaultTreeNode.parseTree(list);
		result.name = "根分类";
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping(value = "/{parentId}/new")
	public String preNew(@PathVariable(value = "parentId") Integer parentId, Model model) {
		DataingDataTag dataTag = new DataingDataTag();
		dataTag.setParentId(parentId);
		model.addAttribute("dataTag", dataTag);
		return "/admin/dataing/tag/edit";
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable(value = "id") Integer id, Model model) {
		DataingDataTag dataTag = dataService.findTagById(id);
		model.addAttribute("dataTag", dataTag);
		return "/admin/dataing/tag/edit";
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue delete(@PathVariable("id") Integer id, String callback, HttpServletRequest request) {
		ObjectResult<String> result = new ObjectResult<String>();
		Customer customer = commonService.user(request);
		DataingDataTag dataTag = dataService.findTagById(id);
		if (dataTag.getType() == 0) {
			result.setCode(HttpStatus.ERROR.code());
			result.setMsg("该数据标签为通用数据标签，不能删除！");
		} else {
			if (dataTag.getParentId().intValue() == 0) {
				result.setCode(HttpStatus.ERROR.code());
				result.setMsg("该数据标签为自定义数据标签根节点，不能删除！");
			} else {
				List<DataingDataTag> subTagList = dataService.findSubTag(customer.getLicense(), id);
				if (subTagList != null && subTagList.size() > 0) {
					result.setCode(HttpStatus.ERROR.code());
					result.setMsg("该数据标签下存在子标签，不能删除！");
				} else {
					if (dataService.deleteTag(id) > 0) {
						result.setCode(HttpStatus.OK.code());
					} else {
						result.setCode(HttpStatus.ERROR.code());
						result.setMsg("数据标签删除失败！");
					}
				}
			}
		}
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Validated final DataingDataTag dataTag, BindingResult result, final Model model,
			HttpServletRequest request) {
		final Customer customer = commonService.user(request);
		dataTag.setLicense(customer.getLicense());
		if (/* New tag */dataTag.getId() == null) {
			dataTag.setCreatorId(customer.getId());
			dataTag.setCreateTime(new Date());
		}
		dataTag.setUpdaterId(customer.getId());
		dataTag.setUpdateTime(new Date());
		DataingDataTag /* Parent tag */ parentTag = (dataTag.getParentId().intValue() != 0)
				? dataService.findTagById(dataTag.getParentId()) : null;
		dataTag.setOrderId(1);
		dataTag.setStatus(EDataStatus.Normal);
		dataTag./* 0-system 1-custom */setType(null != parentTag ? parentTag.getType() : 1);
		if (parentTag == null) {
			dataTag.setPathCode("/" + dataTag.getCode() + "/");
		} else {
			dataTag.setPathCode(parentTag.getPathCode() + dataTag.getCode() + "/");
		}
		return super.save(dataTag, result, model, new Operator() {
			@Override
			public void operate() {
				dataService.saveTag(dataTag);
			}

			@Override
			public String success() {
				return "redirect:/admin/dataing/tag";
			}

			@Override
			public String fail() {
				model.addAttribute("dataTag", dataTag);
				return "/admin/dataing/tag/edit";
			}

			@Override
			public void error() {

			}
		});
	}
}
