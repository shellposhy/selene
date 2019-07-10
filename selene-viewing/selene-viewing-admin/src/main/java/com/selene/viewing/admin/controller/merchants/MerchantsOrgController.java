package com.selene.viewing.admin.controller.merchants;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selene.common.HttpStatus;
import com.selene.common.Node;
import com.selene.common.Operator;
import com.selene.common.constants.CommonConstants;
import com.selene.common.result.ObjectResult;
import com.selene.common.tree.DefaultTreeNode;
import com.selene.merchants.model.MerchantsOrg;
import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.service.CommonService;
import com.selene.viewing.admin.service.merchants.UserService;
import com.selene.viewing.admin.vo.merchants.MerchantsUserVO;

import static cn.com.lemon.base.util.Jsons.json;;

@Controller
@RequestMapping("/admin/merchants/org")
public class MerchantsOrgController extends BaseController {
	@Resource
	private UserService userService;
	@Resource
	private CommonService commonService;

	@RequestMapping(value = "/s", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue loadMerchantsOrgTree(String callback, HttpServletRequest request) {
		ObjectResult<DefaultTreeNode> result = new ObjectResult<DefaultTreeNode>(HttpStatus.OK.code(),
				HttpStatus.OK.message());
		MerchantsUserVO vo = commonService.user(request);
		DefaultTreeNode treeNode = userService.findUserOrgTree(vo.getId());
		treeNode.name = "根机构";
		result.setData(treeNode);
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String preNew(Model model, HttpServletRequest request) {
		MerchantsUserVO vo = commonService.user(request);
		model.addAttribute("org", new MerchantsOrg());
		List<Node<Integer, String>> list = userService.findOrgRole(vo.getId());
		model.addAttribute("groupListJson", json(list));
		return "/admin/merchants/org/edit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Validated final MerchantsOrg merchantsOrg, BindingResult result, final Model model,
			final HttpServletRequest request) {
		return super.save(merchantsOrg, result, model, new Operator() {
			@Override
			public void operate() {
				MerchantsUserVO vo = commonService.user(request);
				if (/* Super Administrator */vo.getId().intValue() == CommonConstants.SUPER_ADMIN) {
					if (/* new organization */merchantsOrg.getParentId().intValue() == 0) {
						
					}
				}
			}

			@Override
			public String success() {
				return "redirect:/admin/merchants";
			}

			@Override
			public String fail() {
				return "/admin/merchants/org/edit";
			}

			@Override
			public void error() {
			}
		});
	}
}
