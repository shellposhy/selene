package com.selene.viewing.admin.controller.merchants;

import static cn.com.lemon.base.Strings.join;
import static cn.com.lemon.base.Strings.split;
import static cn.com.lemon.base.Strings.toArray;
import static com.selene.common.util.Containers.toList;

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
import com.selene.common.datatable.DataTable;
import com.selene.common.datatable.DataTableArray;
import com.selene.common.datatable.DataTableResult;
import com.selene.common.result.ListResult;
import com.selene.common.result.ObjectResult;
import com.selene.common.tree.DefaultTreeNode;
import com.selene.common.util.Containers;
import com.selene.merchants.model.MerchantsRole;
import com.selene.merchants.model.enums.EActionUserType;
import com.selene.merchants.model.enums.EPageType;
import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.framework.vo.Customer;
import com.selene.viewing.admin.service.TokenService;
import com.selene.viewing.admin.service.merchants.UserService;

@Controller
@RequestMapping("/admin/merchants/role")
public class MerchantsRoleController extends BaseController {
	@Resource
	private UserService userService;
	@Resource
	private TokenService commonService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		return "/admin/merchants/role/list";
	}

	@RequestMapping(value = "/s", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue search(@RequestBody DataTableArray[] dataArray, String callback,
			HttpServletRequest request) {
		Customer customer = commonService.user(request);
		DataTable dataTable = Containers.table(dataArray);
		Integer pageSize = dataTable.getiDisplayLength();
		Integer pageStart = dataTable.getiDisplayStart();
		ListResult<MerchantsRole> list = userService.findRoleByPage(dataTable.getsSearch(),
				/* The current user license */customer.getLicense(), pageStart, pageSize);
		DataTableResult<MerchantsRole> result = new DataTableResult<MerchantsRole>(dataTable.getsEcho(),
				list.getTotal(), list.getTotal(), list.getData());
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping(value = "/action/s", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue loadMerchantsOrgTree(String callback, HttpServletRequest request) {
		ObjectResult<DefaultTreeNode> result = new ObjectResult<DefaultTreeNode>(HttpStatus.OK.code(),
				HttpStatus.OK.message());
		DefaultTreeNode treeNode = userService.findActionTreeByType(EActionUserType.Admin);
		treeNode.name = "根机构";
		result.setData(treeNode);
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String preNew(Model model) {
		model.addAttribute("merchantsRole", new MerchantsRole());
		return "/admin/merchants/role/edit";
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable(value = "id") Integer id, Model model) {
		MerchantsRole merchantsRole = userService.findRoleById(id);
		List<Integer> actionIds = userService.findRoleActionIdByRoleId(id);
		if (actionIds != null && actionIds.size() > 0) {
			merchantsRole
					.setTreeSelId(/* list to join */join(CommonConstants.COMMA_SEPARATOR, toArray(toList(actionIds))));
		}
		model.addAttribute("merchantsRole", merchantsRole);
		return "/admin/merchants/role/edit";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue delete(@RequestBody DataTableArray value, String callback, HttpServletRequest request) {
		ObjectResult<String> result = new ObjectResult<String>();
		String[] /* Roles need to deleted. */ roleIds = split(CommonConstants.COMMA_SEPARATOR, value.value);
		boolean isHasUser = false;
		for (String roleId : roleIds) {
			if (/* Make sure the role is not in use before deleting it. */userService
					.checkPreDeleteRole(Integer.valueOf(roleId))) {
				isHasUser = true;
				break;
			}
		}
		if (isHasUser) {
			result.setCode(HttpStatus.ERROR.code());
			result.setMsg("角色删除前，请确保该角色没有被机构或用户使用！");
		} else {
			for (String roleId : roleIds) {
				userService.deleteRole(Integer.valueOf(roleId));
			}
			result.setCode(HttpStatus.OK.code());
		}
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Validated final MerchantsRole merchantsRole, BindingResult result, final Model model,
			final HttpServletRequest request) {
		return super.save(merchantsRole, result, model, new Operator() {
			@Override
			public void operate() {
				Customer customer = commonService.user(request);
				if (/* new role */merchantsRole.getId() == null) {
					merchantsRole.setCreatorId(customer.getId());
					merchantsRole.setCreateTime(new Date());
					merchantsRole.setLicense(customer.getLicense());
					merchantsRole.setAllDataAuthority(false);
					merchantsRole.setAllFrontAuthority(false);
					merchantsRole.setDefaultPageType(EPageType.SysPage);
					merchantsRole.setDefaultPageId(0);
					merchantsRole.setDefaultPageUrl(null);
				}
				merchantsRole.setUpdaterId(customer.getId());
				merchantsRole.setUpdateTime(new Date());
				userService.saveMerchantsRole(merchantsRole);
			}

			@Override
			public String success() {
				return "redirect:/admin/merchants/role";
			}

			@Override
			public String fail() {
				model.addAttribute("code", "100");
				model.addAttribute("msg", "角色名称为：" + merchantsRole.getName() + "，创建失败！");
				return "redirect:/admin/error";
			}

			@Override
			public void error() {
			}
		});
	}
}
