package com.selene.viewing.admin.controller.merchants;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selene.common.Node;
import com.selene.common.datatable.DataTable;
import com.selene.common.datatable.DataTableArray;
import com.selene.common.datatable.DataTableResult;
import com.selene.common.result.ListResult;
import com.selene.common.util.Containers;
import com.selene.merchants.model.MerchantsUser;
import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.service.CommonService;
import com.selene.viewing.admin.service.merchants.UserService;
import com.selene.viewing.admin.vo.merchants.MerchantsUserVO;

import static cn.com.lemon.base.util.Jsons.json;

import java.util.List;

@Controller
@RequestMapping("/admin/merchants")
public class MerchantsController extends BaseController {
	private final static Logger LOG = LoggerFactory.getLogger(MerchantsController.class.getName());
	@Resource
	private UserService userService;
	@Resource
	private CommonService commonService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		LOG.debug("Selene view admin merchants list");
		return "/admin/merchants/list";
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
		MerchantsUserVO vo = commonService.user(request);
		List<Node<Integer, String>> list = userService.findOrgRole(vo.getId());
		MerchantsUser merchantsUser = new MerchantsUser();
		merchantsUser.setOrgId(orgId);
		model.addAttribute("user", merchantsUser);
		model.addAttribute("groupListJson", json(list));
		return "/admin/merchants/edit";
	}
}
