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
import static cn.com.lemon.base.Strings.MD5;
import static cn.com.lemon.base.Strings.join;
import static cn.com.lemon.base.Strings.toArray;
import static cn.com.lemon.util.security.ContentEncryptUtil.encrypt;
import static com.selene.common.util.Chineses.lower;
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

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String preNew(Model model, HttpServletRequest request) {
		MerchantsUserVO vo = commonService.user(request);
		model.addAttribute("org", new MerchantsOrg());
		List<Node<Integer, String>> list = userService.findOrgRole(vo.getId());
		model.addAttribute("groupListJson", json(list));
		return "/admin/merchants/org/edit";
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Integer id, Model model, HttpServletRequest request) {
		MerchantsUserVO vo = commonService.user(request);
		MerchantsOrg merchantsOrg = userService.findMerchantsOrgById(id);
		List<Integer> orgRoleIdList = userService.findMerchantsOrgRoleById(id);
		merchantsOrg
				.setTreeSelId(/* list to join */join(CommonConstants.COMMA_SEPARATOR, toArray(toList(orgRoleIdList))));
		model.addAttribute("org", merchantsOrg);
		List<Node<Integer, String>> list = userService.findOrgRole(vo.getId());
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
				if (/* Super Administrator */vo.getId().intValue() == CommonConstants.SUPER_ADMIN) {
					if (/* new organization */merchantsOrg.getParentId().intValue() == 0) {
						String /* Needed extension */ license = MD5(
								encrypt(lower(merchantsOrg.getName()), CommonConstants.SELENE));
						merchantsOrg.setLicense(license);
						merchantsOrg.setSuperStatus(false);
					} else {
						MerchantsOrg parentOrg = userService.findMerchantsOrgById(merchantsOrg.getParentId());
						merchantsOrg.setLicense(parentOrg.getLicense());
						merchantsOrg.setSuperStatus(parentOrg.isSuperStatus());
					}
				} else {
					if (/* Only add child organization */merchantsOrg.getParentId().intValue() != 0) {
						MerchantsOrg parentOrg = userService.findMerchantsOrgById(merchantsOrg.getParentId());
						merchantsOrg.setLicense(parentOrg.getLicense());
						merchantsOrg.setSuperStatus(parentOrg.isSuperStatus());
					}
				}
				if (null != merchantsOrg.getId()) {
					MerchantsOrg oldOrg = userService.findMerchantsOrgById(merchantsOrg.getId());
					merchantsOrg.setCreatorId(oldOrg.getCreatorId());
					merchantsOrg.setCreateTime(oldOrg.getCreateTime());
					merchantsOrg.setOrderId(oldOrg.getOrderId());
				} else {
					merchantsOrg.setCreatorId(vo.getId());
					merchantsOrg.setCreateTime(new Date());
					merchantsOrg.setOrderId(1);
				}
				merchantsOrg.setStatus(EOrgStatus.Normal);
				merchantsOrg.setUpdaterId(vo.getId());
				merchantsOrg.setUpdateTime(new Date());
				// save new organization
				userService.saveMerchantsOrg(merchantsOrg);
			}

			@Override
			public String success() {
				return "redirect:/admin/merchants";
			}

			@Override
			public String fail() {
				model.addAttribute("org", merchantsOrg);
				return "/admin/merchants/org/edit";
			}

			@Override
			public void error() {
			}
		});
	}
}
