package com.selene.viewing.admin.controller.merchants;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.selene.common.HttpStatus;
import com.selene.common.result.ObjectResult;
import com.selene.common.tree.DefaultTreeNode;
import com.selene.viewing.admin.controller.BaseController;

@Controller
@RequestMapping("/admin/merchants/org")
public class MerchantsOrgController extends BaseController {

	@RequestMapping(value = "/s", method = RequestMethod.POST)
	public MappingJacksonValue loadMerchantsOrgTree(String callback, HttpServletRequest request) {
		ObjectResult<DefaultTreeNode> result = new ObjectResult<DefaultTreeNode>(HttpStatus.OK.code(),
				HttpStatus.OK.message());
		
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}
}
