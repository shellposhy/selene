package com.selene.viewing.admin.controller.templating;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selene.common.HttpStatus;
import com.selene.common.Node;
import com.selene.common.Operator;
import com.selene.common.constants.util.ELibraryType;
import com.selene.common.page.ListArticle;
import com.selene.common.result.ListResult;
import com.selene.common.result.MixedResult;
import com.selene.common.tree.DefaultTreeNode;
import com.selene.templating.model.TemplatingContent;
import com.selene.templating.model.TemplatingItem;
import com.selene.templating.model.TemplatingModel;
import com.selene.templating.model.TemplatingPage;
import com.selene.templating.model.constants.EFilterDir;
import com.selene.templating.model.constants.EFilterStatus;
import com.selene.templating.model.constants.EFilterType;
import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.framework.vo.Customer;
import com.selene.viewing.admin.service.TokenService;
import com.selene.viewing.admin.service.dataing.DataService;
import com.selene.viewing.admin.service.templating.TemplatingService;

import static cn.com.lemon.base.Strings.isNullOrEmpty;
import static com.selene.templating.model.util.PageConfigers.condition;

@Controller
@RequestMapping("/admin/templating/page/config")
public class TemplatingPageConfigController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(TemplatingPageConfigController.class.getName());
	@Resource
	private TemplatingService templatingService;
	@Resource
	private DataService dataService;
	@Resource
	private TokenService commonService;

	@RequestMapping(value = "/{pageId}")
	public String config(@PathVariable Integer pageId, Model model) {
		LOG.debug("[Selene templating]");
		TemplatingPage page = templatingService.findPageById(pageId);
		model.addAttribute("pageId", pageId);
		model.addAttribute("pageTitle", page.getName());
		model.addAttribute("modelId", page.getPageModelId());
		model.addAttribute("templatingContent", new TemplatingContent());
		return "/admin/templating/page/config";
	}

	@RequestMapping(value = "/preview/{pageId}")
	public String preview(HttpServletRequest request, @PathVariable("pageId") Integer pageId) {
		TemplatingPage page = templatingService.findPageById(pageId);
		TemplatingModel model = templatingService.findModelById(page.getPageModelId());
		List<TemplatingItem> itemList = templatingService.findModelItems(model.getId());
		for (TemplatingItem item : itemList) {
			ListArticle listArticle = templatingService.packagePageData(pageId, item.getId());
			request.setAttribute(item.getItemCode(), listArticle);
		}
		String templateFileName = model.getModelFile();
		String templatePath = templateFileName.substring(0, templateFileName.lastIndexOf("."));
		if (templatePath.replace('\\', '/').replace("//", "/").startsWith("/")) {
			templatePath = templatePath.substring(1);
		}
		LOG.info(templatePath);
		return templatePath;
	}

	@RequestMapping("/filter/{name}")
	@ResponseBody
	public MappingJacksonValue filter(@PathVariable String name, String callback) {
		ListResult<Node<String, String>> result = new ListResult<Node<String, String>>(HttpStatus.OK.code(),
				HttpStatus.OK.message());
		EFilterDir filterDir = EFilterDir.valueOfName(name);
		result.setTotal(0);
		if (filterDir != null) {
			List<EFilterType> list = EFilterType.valueOf(filterDir.getIndex());
			if (list != null && list.size() > 0) {
				List<Node<String, String>> dataList = new ArrayList<Node<String, String>>();
				result.setTotal(list.size());
				for (EFilterType filterType : list) {
					Node<String, String> node = new Node<String, String>(filterType.name(), filterType.getName());
					dataList.add(node);
				}
				result.setData(dataList);
			}
		}
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping("/{pageId}/{itemCode}")
	@ResponseBody
	public MappingJacksonValue config(@PathVariable int pageId, @PathVariable String itemCode, String callback,
			HttpServletRequest request) {
		MixedResult<TemplatingContent, DefaultTreeNode> result = new MixedResult<TemplatingContent, DefaultTreeNode>(
				HttpStatus.OK.code(), HttpStatus.OK.message());
		TemplatingPage page = templatingService.findPageById(pageId);
		TemplatingItem item = templatingService.findItemByModelIdAndCode(page.getPageModelId(), itemCode);
		TemplatingContent content = templatingService.findContentByPageIdAndItemId(pageId, item.getId());
		result/* Current content */.setKey(content);
		final Customer customer = commonService.user(request);
		DefaultTreeNode treeNode = dataService.tree(customer.getLicense(), ELibraryType.Default);
		result/* Current database tree */.setValue(treeNode);
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping(value = "/{pageId}/save", method = RequestMethod.POST)
	public String save(@PathVariable final int pageId, @Validated final TemplatingContent templatingContent,
			BindingResult result, final Model model, HttpServletRequest request) {
		final Customer customer = commonService.user(request);
		TemplatingPage page = templatingService.findPageById(pageId);
		final TemplatingItem item = templatingService.findItemByModelIdAndCode(page.getPageModelId(),
				templatingContent.getItemCode());
		return super.save(templatingContent, result, model, new Operator() {
			@Override
			public void operate() {
				templatingContent.setItemId(item.getId());
				templatingContent.setContentType(item.getItemType().ordinal());
				// Process filter for search index
				if (!isNullOrEmpty(templatingContent.getFilterValue())
						&& templatingContent.getFilterStatus() == EFilterStatus.Normal) {
					/* If null, set default EFilterDir.Basic */
					templatingContent.setFilterDir(templatingContent.getFilterDir() != null
							? templatingContent.getFilterDir() : EFilterDir.Basic);
					/* If null, set default EFilterType.Title */
					templatingContent.setFilterType(templatingContent.getFilterType() != null
							? templatingContent.getFilterType() : EFilterType.Title);
					templatingContent.setFilterCondition(
							condition(templatingContent.getFilterType(), templatingContent.getFilterValue()));
				} else {
					templatingContent.setFilterStatus(EFilterStatus.Stop);
					templatingContent.setFilterType(null);
					templatingContent.setFilterDir(null);
					templatingContent.setFilterValue(null);
					templatingContent.setFilterCondition(null);
				}
				// Save or update
				if (templatingContent.getId() == null) {
					templatingContent.setCreatorId(customer.getId());
					templatingContent.setCreateTime(new Date());
				}
				templatingContent.setUpdaterId(customer.getId());
				templatingContent.setUpdateTime(new Date());
				templatingService.saveContent(templatingContent);
			}

			@Override
			public String success() {
				return "redirect:/admin/templating/page/config/" + pageId;
			}

			@Override
			public String fail() {
				return "redirect:/admin/templating/page/config/" + pageId;
			}

			@Override
			public void error() {
				LOG.info("Save page item configuration error!");
			}
		});
	}
}
