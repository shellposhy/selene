package com.selene.viewing.admin.controller.templating;

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
import com.selene.common.result.ListResult;
import com.selene.common.result.ObjectResult;
import com.selene.common.tree.DefaultTreeNode;
import com.selene.templating.model.TemplatingModel;
import com.selene.templating.model.TemplatingModelBill;
import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.service.TokenService;
import com.selene.viewing.admin.service.templating.TemplatingService;
import com.selene.viewing.admin.vo.merchants.MerchantsUserVO;

@Controller
@RequestMapping("/admin/templating/model")
public class TemplatingModelController extends BaseController {
	@Resource
	private TemplatingService templatingService;
	@Resource
	private TokenService commonService;

	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "/admin/templating/model/list";
	}

	/* Model bill processing */

	@RequestMapping("/{billId}/new")
	public String modelNew(@PathVariable("billId") Integer billId, Model model) {
		TemplatingModel templatingModel = new TemplatingModel();
		templatingModel.setBillId(billId);
		model.addAttribute("templatingModel", templatingModel);
		return "/admin/templating/model/edit";
	}

	@RequestMapping("/{id}/edit")
	public String modelEdit(@PathVariable("id") int id, Model model) {
		TemplatingModel templatingModel = templatingService.findModelById(id);
		model.addAttribute("templatingModel", templatingModel);
		return "/admin/templating/model/edit";
	}

	@RequestMapping(value = "/find/{billId}", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue find(@PathVariable("billId") Integer billId, String callback,
			HttpServletRequest request) {
		ListResult<TemplatingModel> result = new ListResult<TemplatingModel>(HttpStatus.OK.code(),
				HttpStatus.OK.message());
		MerchantsUserVO vo = commonService.user(request);
		List<TemplatingModel> modelList = templatingService.findModelByLicenseAndBillId(vo.getLicense(), billId);
		if (null != modelList && modelList.size() > 0) {
			result.setTotal(modelList.size());
			result.setData(modelList);
		} else {
			result.setTotal(0);
			result.setData(null);
		}
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	/* Model bill processing */
	@RequestMapping("/bill/{parentId}/new")
	public String billNew(@PathVariable("parentId") int parentId, Model model) {
		TemplatingModelBill modelBill = new TemplatingModelBill();
		modelBill.setParentId(parentId);
		model.addAttribute("modelBill", modelBill);
		return "/admin/templating/model/bill/edit";
	}

	@RequestMapping("/bill/{id}/edit")
	public String billEdit(@PathVariable("id") int id, Model model) {
		TemplatingModelBill modelBill = templatingService.findModelBillById(id);
		model.addAttribute("modelBill", modelBill);
		return "/admin/templating/model/bill/edit";
	}

	@RequestMapping(value = "/bill/save", method = RequestMethod.POST)
	public String billSave(@Validated final TemplatingModelBill modelBill, BindingResult result, final Model model,
			final HttpServletRequest request) {
		final MerchantsUserVO vo = commonService.user(request);
		return super.save(modelBill, result, model, new Operator() {
			@Override
			public void operate() {
				if (modelBill.getId() == null) {
					modelBill.setLicense(vo.getLicense());
					modelBill.setOrderId(1);
					modelBill.setCreatorId(vo.getId());
					modelBill.setCreateTime(new Date());
					modelBill.setUpdaterId(vo.getId());
					modelBill.setUpdateTime(new Date());
				} else {
					modelBill.setUpdaterId(vo.getId());
					modelBill.setUpdateTime(new Date());
				}
				templatingService./* Save bill */saveModelBill(modelBill);
			}

			@Override
			public String success() {
				return "redirect:/admin/templating/model";
			}

			@Override
			public String fail() {
				model.addAttribute("modelBill", modelBill);
				return "/admin/templating/model/bill/edit";
			}

			@Override
			public void error() {
			}
		});
	}

	@RequestMapping(value = "/bill/tree", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue tree(String callback, HttpServletRequest request) {
		ObjectResult<DefaultTreeNode> result = new ObjectResult<DefaultTreeNode>(HttpStatus.OK.code(),
				HttpStatus.OK.message());
		MerchantsUserVO vo = commonService.user(request);
		DefaultTreeNode tree = DefaultTreeNode.parseTree(templatingService.findModelBillByLicense(vo.getLicense()));
		tree.setName("模板根目录");
		result.setData(tree);
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}
}
