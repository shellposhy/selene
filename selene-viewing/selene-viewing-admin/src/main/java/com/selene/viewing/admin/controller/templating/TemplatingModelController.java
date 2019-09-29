package com.selene.viewing.admin.controller.templating;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selene.common.HttpStatus;
import com.selene.common.result.ListResult;
import com.selene.common.result.ObjectResult;
import com.selene.common.tree.DefaultTreeNode;
import com.selene.templating.model.TemplatingModel;
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

	@RequestMapping(value = "/bill/tree", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue tree(String callback, HttpServletRequest request) {
		ObjectResult<DefaultTreeNode> result = new ObjectResult<DefaultTreeNode>(HttpStatus.OK.code(),
				HttpStatus.OK.message());
		MerchantsUserVO vo = commonService.user(request);
		DefaultTreeNode tree = DefaultTreeNode.parseTree(templatingService.findModelBillByLicense(vo.getLicense()));
		result.setData(tree);
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
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
}
