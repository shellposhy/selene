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
import com.selene.viewing.admin.framework.vo.Customer;
import com.selene.viewing.admin.service.TokenService;
import com.selene.viewing.admin.service.dataing.DataService;

@Controller
@RequestMapping("/admin/dataing/directory")
public class DataingDirectoryController extends BaseController {
	@Resource
	private DataService dataService;
	@Resource
	private TokenService commonService;

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	private MappingJacksonValue tree(String callback, HttpServletRequest request) {
		ObjectResult<LibraryTreeNode> result = new ObjectResult<LibraryTreeNode>(HttpStatus.OK.code(),
				HttpStatus.OK.message());
		Customer customer = commonService.user(request);
		LibraryTreeNode treeNode = dataService.tree(customer.getLicense(), null, ELibraryType.Default);
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
		Customer customer = commonService.user(request);
		List<DataingDatabase> list = dataService.emptyTreeList(customer.getLicense(), ELibraryType.Default);
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

	@RequestMapping("/{id}/edit")
	public String edit(@PathVariable("id") int id, Model model) {
		DataingDatabase database = dataService.findDatabase(id);
		model.addAttribute("dataBase", database);
		return "/admin/dataing/directory/edit";
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue delete(@PathVariable Integer id, String callback, HttpServletRequest request) {
		ObjectResult<String> result = new ObjectResult<String>();
		Customer customer = commonService.user(request);
		List<DataingDatabase> /* Make sure no subitems in the directory. */ list = dataService
				.findBaseByParentId(customer.getLicense(), ELibraryType.Default, id);
		if (list != null && list.size() > 0) {
			result.setCode(HttpStatus.ERROR.code());
			result.setMsg("数据库目录删除前，请确保该目录下没有子目录或数据库！");
		} else {
			int number = dataService.deleteDirectory(id);
			if (number > 0) {
				result.setCode(HttpStatus.OK.code());
			} else {
				result.setCode(HttpStatus.ERROR.code());
				result.setMsg("数据库目录删除失败！");
			}
		}
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(final DataingDatabase database, BindingResult result, final Model model,
			final HttpServletRequest request) {
		final Customer customer = commonService.user(request);
		return super.save(database, result, model, new Operator() {
			@Override
			public void operate() {
				if (database.getId() == null) {
					database.setModelId(0);
					database.setTables(0);
					database.setLicense(customer.getLicense());
					database.setNodeType(ELibraryNodeType.Directory);
					database.setStatus(EDataStatus.Normal);
					database.setCreatorId(customer.getId());
					database.setCreateTime(new Date());
				}
				database.setUpdaterId(customer.getId());
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
