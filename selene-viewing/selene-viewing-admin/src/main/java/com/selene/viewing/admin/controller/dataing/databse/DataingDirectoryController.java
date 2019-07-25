package com.selene.viewing.admin.controller.dataing.databse;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selene.common.HttpStatus;
import com.selene.common.Operator;
import com.selene.common.constants.util.EDataStatus;
import com.selene.common.constants.util.ELibraryNodeType;
import com.selene.common.constants.util.ELibraryType;
import com.selene.common.result.ObjectResult;
import com.selene.common.tree.DefaultTreeNode;
import com.selene.common.tree.support.LibraryTreeNode;
import com.selene.dataing.model.DataingDatabase;
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

	@RequestMapping(value = "/emptyTree", method = RequestMethod.POST)
	@ResponseBody
	private MappingJacksonValue emptyTree(String callback, HttpServletRequest request) {
		ObjectResult<DefaultTreeNode> result = new ObjectResult<DefaultTreeNode>(HttpStatus.OK.code(),
				HttpStatus.OK.message());
		MerchantsUserVO vo = commonService.user(request);
		List<DataingDatabase> list = dataService.emptyTreeList(vo.getLicense(), ELibraryType.Default);
		DefaultTreeNode treeNode = DefaultTreeNode.parseTree(list);
		treeNode.id = 0;
		treeNode.name = "根目录";
		result.setData(treeNode);
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping("/{parentId}/new")
	public String preNewDirectory(@PathVariable("parentId") int parentId, Model model) {
		DataingDatabase database = new DataingDatabase();
		database.setParentId(parentId);
		database.setType(ELibraryType.Default);
		model.addAttribute("dataBase", database);
		return "/admin/dataing/directory/edit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(final DataingDatabase database, BindingResult result, final Model model,
			final HttpServletRequest request) {
		final MerchantsUserVO vo = commonService.user(request);
		return super.save(database, result, model, new Operator() {
			@Override
			public void operate() {
				if (database.getId() == null) {
					database.setModelId(0);
					database.setTables(0);
					database.setLicense(vo.getLicense());
					database.setNodeType(ELibraryNodeType.Directory);
					database.setStatus(EDataStatus.Normal);
					database.setCreatorId(vo.getId());
					database.setCreateTime(new Date());
				}
				database.setUpdaterId(vo.getId());
				database.setUpdateTime(new Date());
				dataService.saveDirectory(database);
			}

			@Override
			public String success() {
				return "redirect:/admin/dataing/library";
			}

			@Override
			public String fail() {
				model.addAttribute("dataBase", database);
				return "/admin/dataing/directory/edit";
			}

			@Override
			public void error() {
			}
		});
	}
}
