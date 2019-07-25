package com.selene.viewing.admin.controller.dataing.databse;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selene.common.HttpStatus;
import com.selene.common.constants.util.ELibraryType;
import com.selene.common.result.ObjectResult;
import com.selene.common.tree.support.LibraryTreeNode;
import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.service.CommonService;
import com.selene.viewing.admin.service.dataing.DataService;
import com.selene.viewing.admin.vo.merchants.MerchantsUserVO;

@Controller
@RequestMapping("/admin/dataing/directory")
public class DataingDirectoryController extends BaseController {
	@Resource
	private DataService dataService;
	@Resource
	private CommonService commonService;

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	private MappingJacksonValue tree(String callback, HttpServletRequest request) {
		ObjectResult<LibraryTreeNode> result = new ObjectResult<LibraryTreeNode>(HttpStatus.OK.code(),
				HttpStatus.OK.message());
		MerchantsUserVO vo = commonService.user(request);
		LibraryTreeNode treeNode = dataService.tree(vo.getLicense(), null, ELibraryType.Default);
		result.setData(treeNode);
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}
}
