package com.selene.viewing.admin.controller.merchants;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selene.common.HttpStatus;
import com.selene.common.Operator;
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
import com.selene.viewing.admin.service.CommonService;
import com.selene.viewing.admin.service.merchants.UserService;
import com.selene.viewing.admin.vo.merchants.MerchantsUserVO;

@Controller
@RequestMapping("/admin/merchants/role")
public class MerchantsRoleController extends BaseController {
	@Resource
	private UserService userService;
	@Resource
	private CommonService commonService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		return "/admin/merchants/role/list";
	}

	@RequestMapping(value = "/s", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue search(@RequestBody DataTableArray[] dataArray, String callback,
			HttpServletRequest request) {
		MerchantsUserVO vo = commonService.user(request);
		DataTable dataTable = Containers.table(dataArray);
		Integer pageSize = dataTable.getiDisplayLength();
		Integer pageStart = dataTable.getiDisplayStart();
		ListResult<MerchantsRole> list = userService.findRoleByPage(dataTable.getsSearch(),
				/* The current user license */vo.getLicense(), pageStart, pageSize);
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

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Validated final MerchantsRole merchantsRole, BindingResult result, final Model model,
			final HttpServletRequest request) {
		return super.save(merchantsRole, result, model, new Operator() {
			@Override
			public void operate() {
				MerchantsUserVO vo = commonService.user(request);
				if (/* new role */merchantsRole.getId() == null) {
					merchantsRole.setCreatorId(vo.getId());
					merchantsRole.setCreateTime(new Date());
					merchantsRole.setLicense(vo.getLicense());
					merchantsRole.setAllDataAuthority(false);
					merchantsRole.setAllFrontAuthority(false);
					merchantsRole.setDefaultPageType(EPageType.SysPage);
					merchantsRole.setDefaultPageId(0);
					merchantsRole.setDefaultPageUrl(null);
				}
				merchantsRole.setUpdaterId(vo.getId());
				merchantsRole.setUpdateTime(new Date());
				userService.saveMerchantsRole(merchantsRole);
			}

			@Override
			public String success() {
				return null;
			}

			@Override
			public String fail() {
				return null;
			}

			@Override
			public void error() {
			}
		});
	}
}
