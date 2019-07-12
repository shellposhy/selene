package com.selene.viewing.admin.controller.merchants;

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
import com.selene.common.Node;
import com.selene.common.Operator;
import com.selene.common.constants.CommonConstants;
import com.selene.common.result.ObjectResult;
import com.selene.common.tree.DefaultTreeNode;
import com.selene.merchants.model.MerchantsOrg;
import com.selene.merchants.model.enums.EOrgStatus;
import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.service.CommonService;
import com.selene.viewing.admin.service.merchants.UserService;
import com.selene.viewing.admin.vo.merchants.MerchantsUserVO;

import static cn.com.lemon.base.util.Jsons.json;
import static cn.com.lemon.base.Strings.join;
import static cn.com.lemon.base.Strings.toArray;
import static com.selene.common.util.Containers.toList;

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

	@RequestMapping(value = "/{parentId}/new", method = RequestMethod.GET)
	public String preNew(@PathVariable("parentId") Integer parentId, Model model, HttpServletRequest request) {
		MerchantsOrg parentOrg = /* Parent organization */userService.findMerchantsOrgById(parentId);
		MerchantsOrg newChildOrg = new MerchantsOrg();
		newChildOrg.setParentId(/* Inherit parent organization */parentId);
		newChildOrg.setLicense(/* Inherit parent organization license */parentOrg.getLicense());
		newChildOrg.setOrgType(/* Inherit parent organization type */parentOrg.getOrgType());
		newChildOrg.setSuperStatus(/* Inherit parent organization super */parentOrg.isSuperStatus());
		List<Node<Integer, String>> list = userService.findRoleByLicense(
				/* Role list by parent organization license */parentOrg.getLicense());
		model.addAttribute("groupListJson", /* to json */json(list));
		model.addAttribute("org", newChildOrg);
		return "/admin/merchants/org/edit";
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Integer id, Model model, HttpServletRequest request) {
		MerchantsOrg merchantsOrg = userService.findMerchantsOrgById(id);
		List<Integer> orgRoleIdList = userService.findMerchantsOrgRoleById(id);
		merchantsOrg
				.setTreeSelId(/* list to join */join(CommonConstants.COMMA_SEPARATOR, toArray(toList(orgRoleIdList))));
		model.addAttribute("org", merchantsOrg);
		List<Node<Integer, String>> list = userService.findRoleByLicense(merchantsOrg.getLicense());
		model.addAttribute("groupListJson", json(list));
		return "/admin/merchants/org/edit";
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue delete(@PathVariable("id") Integer id, String callback, HttpServletRequest request) {
		ObjectResult<String> result = new ObjectResult<String>();
		int realUsrCount = userService.countByOrgId(id);
		List<MerchantsOrg> subMerchantsOrgList = userService.findMerchantsOrgByParentId(id);
		if (subMerchantsOrgList != null && subMerchantsOrgList.size() > 0) {
			result.setCode(HttpStatus.ERROR.code());
			result.setMsg("该机构下有子机构，无法删除，请先清空子机构！");
		} else if (realUsrCount > 0) {
			result.setCode(HttpStatus.ERROR.code());
			result.setMsg("该机构下有用户，无法删除，请先清空用户！");
		} else {
			userService.deleteMerchantsOrg(id);
			result.setCode(HttpStatus.OK.code());
		}
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Validated final MerchantsOrg merchantsOrg, BindingResult result, final Model model,
			final HttpServletRequest request) {
		return super.save(merchantsOrg, result, model, new Operator() {
			@Override
			public void operate() {
				MerchantsUserVO vo = commonService.user(request);
				if (/* new organization */merchantsOrg.getId() == null) {
					MerchantsOrg parentOrg = userService.findMerchantsOrgById(merchantsOrg.getParentId());
					merchantsOrg.setCreatorId(vo.getId());
					merchantsOrg.setCreateTime(new Date());
					merchantsOrg.setOrderId(1);
					merchantsOrg.setLicense(/* Not allowed edit */parentOrg.getLicense());
					merchantsOrg.setSuperStatus(/* Not allowed edit */parentOrg.isSuperStatus());
					merchantsOrg.setOrgType(/* Not allowed edit */parentOrg.getOrgType());
				} /* Update organization */else {
					MerchantsOrg oldOrg = userService.findMerchantsOrgById(merchantsOrg.getId());
					merchantsOrg.setCreatorId(/* Not allowed edit */oldOrg.getCreatorId());
					merchantsOrg.setCreateTime(/* Not allowed edit */oldOrg.getCreateTime());
					merchantsOrg.setOrderId(/* Not allowed edit */oldOrg.getOrderId());
					merchantsOrg.setLicense(/* Not allowed edit */oldOrg.getLicense());
					merchantsOrg.setSuperStatus(/* Not allowed edit */oldOrg.isSuperStatus());
					merchantsOrg.setOrgType(/* Not allowed edit */oldOrg.getOrgType());
				}
				merchantsOrg.setStatus(EOrgStatus.Normal);
				merchantsOrg.setUpdaterId(vo.getId());
				merchantsOrg.setUpdateTime(new Date());
				userService/* save new organization */.saveMerchantsOrg(merchantsOrg);
			}

			@Override
			public String success() {
				return "redirect:/admin/merchants";
			}

			@Override
			public String fail() {
				model.addAttribute("code", "100");
				model.addAttribute("msg", "机构保存或修改出错！");
				return "redirect:/admin/error";
			}

			@Override
			public void error() {
			}
		});
	}
}
