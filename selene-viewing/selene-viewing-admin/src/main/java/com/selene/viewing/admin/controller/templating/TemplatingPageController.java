package com.selene.viewing.admin.controller.templating;

import static cn.com.lemon.base.Strings.split;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.selene.common.Operator;
import com.selene.common.constants.CommonConstants;
import com.selene.common.datatable.DataTable;
import com.selene.common.datatable.DataTableArray;
import com.selene.common.datatable.DataTableResult;
import com.selene.common.result.ListResult;
import com.selene.common.result.ObjectResult;
import com.selene.common.tree.support.PageTreeNode;
import com.selene.common.util.Containers;
import com.selene.templating.model.TemplatingModel;
import com.selene.templating.model.TemplatingPage;
import com.selene.templating.model.constants.EModelType;
import com.selene.templating.model.constants.EPageStatus;
import com.selene.templating.model.constants.EPageTransfer;
import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.framework.vo.Customer;
import com.selene.viewing.admin.service.TokenService;
import com.selene.viewing.admin.service.templating.TemplatingPublishService;
import com.selene.viewing.admin.service.templating.TemplatingService;

import cn.com.lemon.base.Strings;

@Controller
@RequestMapping("/admin/templating/page")
public class TemplatingPageController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(TemplatingPageController.class.getName());
	@Resource
	private TemplatingService templatingService;
	@Resource
	private TemplatingPublishService publishService;
	@Resource
	private TokenService commonService;

	@RequestMapping
	public String list() {
		return "/admin/templating/page/list";
	}

	@RequestMapping(value = "/{type}/transfer", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue transfer(@RequestBody DataTableArray value, @PathVariable("type") int type,
			String callback, HttpServletRequest request) {
		ObjectResult<String> result = new ObjectResult<String>();
		String[] /* Index pages need to published. */ pageIds = split(CommonConstants.COMMA_SEPARATOR, value.value);
		if (pageIds != null && pageIds.length > 0) {
			for (String pageId : pageIds) {
				TemplatingPage page = templatingService.findPageById(Integer.valueOf(pageId));
				if (type == EPageTransfer.On.ordinal()) {
					page.setStatus(true);
				} else {
					page.setStatus(false);
				}
				templatingService.savePage(page);
			}
		}
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping(value = "/publish", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue publish(@RequestBody DataTableArray value, String callback, HttpServletRequest request) {
		ObjectResult<String> result = new ObjectResult<String>();
		String[] /* Index pages need to published. */ pageIds = split(CommonConstants.COMMA_SEPARATOR, value.value);
		if (pageIds != null && pageIds.length > 0) {
			for (String pageId : pageIds) {
				TemplatingPage page = templatingService.findPageById(Integer.valueOf(pageId));
				if (/* Only publish put on shelf page */page.getStatus()) {
					if (/* Only home page publishing */page.getPageType() == EModelType.Home
							|| page.getPageType() == EModelType.Site || page.getPageType() == EModelType.Subject) {
						publishService.home(Integer.valueOf(pageId));
					}
				}
			}
		}
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@SuppressWarnings("unused")
	@RequestMapping("/{parentId}/new")
	public String preNew(HttpServletRequest request, @PathVariable Integer parentId, Model model) {
		TemplatingPage templatingPage = new TemplatingPage();
		templatingPage.setParentId(parentId);
		Customer customer = commonService.user(request);
		List<TemplatingPage> subPageList = templatingService.findByParentId(customer.getLicense(), parentId);
		model.addAttribute("templatingPage", templatingPage);
		return "/admin/templating/page/edit";
	}

	@RequestMapping(value = "/model/{type}", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue getPageModel(@PathVariable EModelType type, String callback,
			HttpServletRequest request) {
		ListResult<TemplatingModel> result = new ListResult<TemplatingModel>(HttpStatus.OK.code(),
				HttpStatus.OK.message());
		Customer customer = commonService.user(request);
		result.setData(templatingService.findModelByType(customer.getLicense(), type));
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping(value = "/search/{pageId}", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue search(@PathVariable int pageId, @RequestBody DataTableArray[] dataArray,
			String callback, HttpServletRequest request) {
		LOG.debug("Only search single data!");
		// Parameter process
		DataTable dataTable = Containers.table(dataArray);
		List<TemplatingPage> list = new ArrayList<TemplatingPage>();
		TemplatingPage page = templatingService.findPageById(pageId);
		list.add(page);
		DataTableResult<TemplatingPage> result = new DataTableResult<>(dataTable.getsEcho(), 1, 1, list);
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue tree(String callback, HttpServletRequest request) {
		ObjectResult<PageTreeNode> result = new ObjectResult<PageTreeNode>(HttpStatus.OK.code(),
				HttpStatus.OK.message());
		Customer customer = commonService.user(request);
		PageTreeNode treeNode = templatingService.pageTree(customer.getLicense());
		treeNode.setName("根节点");
		result.setData(treeNode);
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Validated final TemplatingPage templatingPage, BindingResult result, final Model model,
			final HttpServletRequest request) {
		return super.save(templatingPage, result, model, new Operator() {
			@Override
			public void operate() {
				Customer customer = commonService.user(request);
				TemplatingPage parentPage = templatingService.findPageById(templatingPage.getParentId());
				if (parentPage != null && !Strings.isNullOrEmpty(parentPage.getPathCode())) {
					templatingPage.setPathCode(parentPage.getPathCode() + templatingPage.getCode() + "/");
				} else {
					templatingPage.setPathCode("/" + templatingPage.getCode() + "/");
				}
				templatingPage.setLicense(customer.getLicense());
				if (templatingPage.getId() == null) {
					templatingPage.setCreatorId(customer.getId());
					templatingPage.setCreateTime(new Date());
					templatingPage.setStatus(false);
					templatingPage.setPageStatus(EPageStatus.Unpublish);
				} else {
					TemplatingPage historyPage = templatingService.findPageById(templatingPage.getId());
					templatingPage.setStatus(historyPage.getStatus());
					templatingPage.setPageStatus(historyPage.getPageStatus());
					templatingPage.setPageHtmlPath(historyPage.getPageHtmlPath());
					templatingPage.setPublishTime(historyPage.getPublishTime());
				}
				templatingPage.setUpdaterId(customer.getId());
				templatingPage.setUpdateTime(new Date());
				templatingService.savePage(templatingPage);
			}

			@Override
			public String success() {
				return "redirect:/admin/templating/page";
			}

			@Override
			public String fail() {
				model.addAttribute("templatingPage", templatingPage);
				return "/admin/templating/page/edit";
			}

			@Override
			public void error() {
			}
		});
	}
}
