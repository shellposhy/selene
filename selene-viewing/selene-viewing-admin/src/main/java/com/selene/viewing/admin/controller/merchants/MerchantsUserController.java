package com.selene.viewing.admin.controller.merchants;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.selene.common.Node;
import com.selene.common.Operator;
import com.selene.common.constants.CommonConstants;
import com.selene.common.datatable.DataTable;
import com.selene.common.datatable.DataTableArray;
import com.selene.common.datatable.DataTableResult;
import com.selene.common.result.ListResult;
import com.selene.common.result.ObjectResult;
import com.selene.common.util.Containers;
import com.selene.merchants.model.MerchantsOrg;
import com.selene.merchants.model.MerchantsUser;
import com.selene.merchants.model.enums.EActionUserType;
import com.selene.merchants.model.enums.EOrgStatus;
import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.service.TokenService;
import com.selene.viewing.admin.service.merchants.UserService;
import com.selene.viewing.admin.vo.merchants.MerchantsUserVO;

import static cn.com.lemon.base.util.Jsons.json;
import static com.selene.common.util.Containers.toList;
import static cn.com.lemon.base.Strings.md5;
import static cn.com.lemon.base.Strings.split;
import static cn.com.lemon.base.Strings.toArray;
import static cn.com.lemon.base.Strings.ip;
import static cn.com.lemon.base.Strings.join;
import static cn.com.lemon.base.Strings.uuid;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/merchants/user")
public class MerchantsUserController extends BaseController {
	private final static Logger LOG = LoggerFactory.getLogger(MerchantsUserController.class.getName());
	@Resource
	private UserService userService;
	@Resource
	private TokenService commonService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		LOG.debug("Selene view admin merchants list");
		return "/admin/merchants/user/list";
	}

	@RequestMapping(value = "/{orgId}/s", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue search(@PathVariable("orgId") Integer orgId, @RequestBody DataTableArray[] dataArray,
			String callback) {
		DataTable dataTable = Containers.table(dataArray);
		Integer pageSize = dataTable.getiDisplayLength();
		Integer pageStart = dataTable.getiDisplayStart();
		ListResult<MerchantsUser> list = userService.findOrgUserByPage(orgId, dataTable.getsSearch(), pageStart,
				pageSize);
		DataTableResult<MerchantsUser> result = new DataTableResult<MerchantsUser>(dataTable.getsEcho(),
				list.getTotal(), list.getTotal(), list.getData());
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping(value = "/{orgId}/new", method = RequestMethod.GET)
	public String preNew(@PathVariable("orgId") Integer orgId, Model model, HttpServletRequest request) {
		MerchantsOrg merchantsOrg = userService.findMerchantsOrgById(orgId);
		List<Node<Integer, String>> list = userService.findRoleByLicense(
				/* Role list by parent organization license */merchantsOrg.getLicense());
		MerchantsUser merchantsUser = new MerchantsUser();
		merchantsUser.setOrgId(orgId);
		model.addAttribute("user", merchantsUser);
		model.addAttribute("groupListJson", json(list));
		return "/admin/merchants/user/edit";
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Integer id, Model model, HttpServletRequest request) {
		MerchantsUser user = userService.find(id);
		List<Integer> userRoleIdList = userService.findMerchantsUserRoleById(id);
		user.setTreeSelId(/* list to join */join(CommonConstants.COMMA_SEPARATOR, toArray(toList(userRoleIdList))));
		List<Node<Integer, String>> list = userService.findRoleByLicense(
				/* Role list by parent organization license */userService.findLicenseByUserId(id));
		user.setOldPassword(user.getUserPassword());
		model.addAttribute("user", user);
		model.addAttribute("groupListJson", json(list));
		return "/admin/merchants/user/edit";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue delete(@RequestBody DataTableArray value, String callback, HttpServletRequest request) {
		ObjectResult<String> result = new ObjectResult<String>(HttpStatus.OK.code(), "删除成功！");
		MerchantsUserVO vo = commonService.user(request);
		String[] /* Users need to deleted. */ userIds = split(CommonConstants.COMMA_SEPARATOR, value.value);
		for (String userId : userIds) {
			MerchantsUser merchantsUser = userService.find(Integer.valueOf(userId));
			merchantsUser.setStatus(EOrgStatus.Stop);
			merchantsUser.setNickName(merchantsUser.getName());
			merchantsUser.setName(uuid());
			merchantsUser.setUpdaterId(vo.getId());
			merchantsUser.setUpdateTime(new Date());
			userService.deleteUser(merchantsUser);
		}
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@Validated final MerchantsUser user, BindingResult result, final Model model,
			final HttpServletRequest request) {
		final MerchantsUserVO vo = commonService.user(request);
		return super.save(user, result, model, new Operator() {
			@Override
			public void operate() {
				user.setUpdateTime(new Date());
				user.setUpdaterId(vo.getId());
				user.setUserPassword(md5(user.getUserPassword()));
				user.setOrderId(1);
				user.setType(EActionUserType.Admin);
				user.setIpAddress(ip(request));
				user.setPic("1");
				if (user.getId() == null) {
					user.setCreatorId(vo.getId());
					user.setCreateTime(new Date());
				}
				userService.saveMerchantsUser(user);
			}

			@Override
			public String success() {
				return "redirect:/admin/merchants/user";
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
