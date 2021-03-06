package com.selene.viewing.admin.controller.templating;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.selene.common.HttpStatus;
import com.selene.common.Node;
import com.selene.common.Operator;
import com.selene.common.constants.CommonConstants;
import com.selene.common.constants.ResourceConstants;
import com.selene.common.datatable.DataTableArray;
import com.selene.common.result.ListResult;
import com.selene.common.result.ObjectResult;
import com.selene.common.tree.DefaultTreeNode;
import com.selene.common.tree.FileNode;
import com.selene.templating.model.TemplatingModel;
import com.selene.templating.model.TemplatingModelBill;
import com.selene.templating.model.TemplatingPage;
import com.selene.templating.model.constants.EModelType;
import com.selene.templating.model.constants.EReplaceType;
import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.framework.vo.Customer;
import com.selene.viewing.admin.service.ResourceService;
import com.selene.viewing.admin.service.TokenService;
import com.selene.viewing.admin.service.templating.TemplatingService;

import com.selene.templating.model.util.Templatings;

@Controller
@RequestMapping("/admin/templating/model")
public class TemplatingModelController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(TemplatingModelController.class.getName());
	private StringBuilder sb = new StringBuilder();
	@Resource
	private TemplatingService templatingService;
	@Resource
	private TokenService commonService;
	@Resource
	private ResourceService resourceService;

	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "/admin/templating/model/list";
	}

	/* Model bill processing */
	@RequestMapping(value = "/{modelId}/template", method = RequestMethod.GET)
	public String template(@PathVariable("modelId") Integer modelId, Model model) {
		model.addAttribute("modelId", modelId);
		return "/admin/templating/model/template";
	}

	@RequestMapping(value = "/scan/{modelId}", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue scan(@PathVariable("modelId") Integer modelId, String callback) {
		ObjectResult<String> result = new ObjectResult<String>();
		TemplatingModel model = templatingService.findModelById(modelId);
		String rootPath = resourceService.root();
		String fileName = model.getModelFile();
		File template = new File(rootPath + fileName);
		if (template.exists() && template.isFile()) {
			/** Copy original template to WEB-INF/template */
			String classesPath = TemplatingModelController.class.getResource("/").getPath();
			String targetPath = classesPath.substring(0, classesPath.indexOf("WEB-INF")) + "WEB-INF"
					+ ResourceConstants.BASE_TEMPLATE_PATH;
			String newFileName = fileName.replace('\\', '/').replace("//", "/");
			File destDir = new File(targetPath + newFileName.substring(0, newFileName.lastIndexOf("/")));
			try {
				FileUtils.copyFileToDirectory(template, destDir);
				/** Copy template file to publish freemaker files */
				String pubFileName = template.getName().substring(0, template.getName().lastIndexOf("."))
						+ CommonConstants.DEFAULT_FREEMARKER_PUBLISH_PREFIX
						+ template.getName().substring(template.getName().lastIndexOf("."));
				String pubPath = template.getParentFile().getAbsolutePath().replace("//", "/");
				File pubFile = new File(pubPath + "/" + pubFileName);
				FileUtils.copyFileToDirectory(pubFile, destDir);
				/** Copy original ftl/** to WEB-INF/template//ftl */
				String ftlPath = template.getParentFile().getAbsolutePath().replace('\\', '/').replace("//", "/")
						+ ResourceConstants.BASE_FTL_PATH;
				File ftlPathDir = new File(ftlPath);
				if (ftlPathDir.exists() && ftlPathDir.isDirectory()) {
					File newFtlDir = new File(targetPath + newFileName.substring(0, newFileName.lastIndexOf("/"))
							+ ResourceConstants.BASE_FTL_PATH);
					FileUtils.copyDirectory(ftlPathDir, newFtlDir);
				}
				/** Scan template files */
				if (templatingService.scanModel(modelId, template) > 0) {
					result.setCode(HttpStatus.OK.code());
				} else {
					result.setCode(HttpStatus.ERROR.code());
					result.setMsg("模板配置内容扫描失败！");
				}
			} catch (IOException e) {
				result.setCode(HttpStatus.ERROR.code());
				result.setMsg("复制模板文件错误！");
			}
		} else {
			result.setCode(HttpStatus.ERROR.code());
			result.setMsg("扫描模板文件不存在，请检测是否已上传模板文件！");
		}
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping(value = "/{modelId}/template/file", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue files(@PathVariable("modelId") Integer modelId, String callback) {
		TemplatingModel templatingModel = templatingService.findModelById(modelId);
		FileNode result = new FileNode();
		// File processing
		String rootPath = resourceService.realStatic(templatingModel.getModelCode());
		File root = new File(rootPath);
		String path = resourceService.root().replace('\\', '/').replace("//", "/");
		if (root.exists()) {
			result.setName(root.getName());
			result.setDirectory(root.isDirectory());
			result.setAbsolutePath(root.getAbsolutePath());
			result.setFilePath("#");
			if (/* Recurse all files */root.isDirectory()) {
				Templatings.template(root, result, path);
			}
		}
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping(value = "/template/file/read", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue read(@RequestBody DataTableArray value, String callback) {
		ObjectResult<String> result = new ObjectResult<String>(HttpStatus.OK.code(), HttpStatus.OK.message());
		String path = value.value;
		File file = new File(path);
		if (file.exists() && file.isFile()) {
			try {
				result.setData(FileUtils.readFileToString(file, Charset.forName("UTF-8")));
			} catch (IOException e) {
				LOG.error("[" + path + "] not exist or read content error!", e);
				result.setCode(HttpStatus.ERROR.code());
				result.setMsg("文件不存在或文件读取内容失败！");
			}
		} else {
			result.setCode(HttpStatus.ERROR.code());
			result.setMsg("文件不存在或文件读取内容失败！");
		}
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping("/{billId}/new")
	public String modelNew(@PathVariable("billId") Integer billId, Model model) {
		TemplatingModel templatingModel = new TemplatingModel();
		templatingModel.setBillId(billId);
		TemplatingModelBill modelBill = templatingService.findModelBillById(billId);
		templatingModel.setModelType(modelBill.getType());
		model.addAttribute("templatingModel", templatingModel);
		return "/admin/templating/model/edit";
	}

	@RequestMapping("/{id}/edit")
	public String modelEdit(@PathVariable("id") int id, Model model) {
		TemplatingModel templatingModel = templatingService.findModelById(id);
		model.addAttribute("templatingModel", templatingModel);
		return "/admin/templating/model/edit";
	}

	@RequestMapping(value = "/{modelId}/delete", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue delete(@PathVariable("modelId") Integer modelId, HttpServletRequest request,
			String callback) {
		ObjectResult<String> result = new ObjectResult<String>(HttpStatus.OK.code(), HttpStatus.OK.message());
		Customer customer = commonService.user(request);
		List<TemplatingPage> pageList = templatingService.findByModelId(customer.getLicense(), modelId);
		if (pageList != null && pageList.size() > 0) {
			result.setCode(HttpStatus.ERROR.code());
			result.setMsg("该页面模板已被使用，无法删除！");
		} else {
			/** Delete template file first */
			TemplatingModel model = templatingService.findModelById(modelId);
			resourceService./* Delete static files */deleteFolder(resourceService.realStatic(model.getModelCode()));
			resourceService./* Delete template files */deleteFolder(resourceService.realTemplate(model.getModelCode()));
			if (!(templatingService.deleteModel(modelId) > 0)) {
				result.setCode(HttpStatus.ERROR.code());
				result.setMsg("该页面模板删除失败，请重新再试！");
			}
		}
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Validated final TemplatingModel templatingModel, final @RequestParam("file") MultipartFile file,
			BindingResult result, final Model model, HttpServletRequest request) {
		final Customer customer = commonService.user(request);
		return super.save(templatingModel, result, model, new Operator() {
			@Override
			public void operate() {
				templatingModel.setLicense(customer.getLicense());
				/** Upload template zip file processing */
				String filename = file.getOriginalFilename();
				String modelCode = templatingModel.getModelCode();
				String templatePath = resourceService.realTemplate(modelCode);
				String rootPath = resourceService.root();
				File templateDirectory = new File(templatePath);
				if (/* If exist path,delete first */templateDirectory.exists()) {
					if (resourceService.deleteFolder(templatePath)) {
						templateDirectory.mkdirs();
					}
				} else {
					templateDirectory.mkdirs();
				}
				String templateFilePath = templatePath.endsWith("/") ? templatePath + filename
						: templatePath + "/" + filename;
				File templateFile = new File(templateFilePath);
				try {
					templateFile.createNewFile();
					file.transferTo(templateFile);
					/** Uncompressing zip files */
					if (templateFile.getName().endsWith(".zip")) {
						String staticPath = resourceService.realStatic(modelCode);
						File staticDirectory = new File(staticPath);
						if (/* If exist path,delete first */staticDirectory.exists()) {
							if (resourceService.deleteFolder(staticPath)) {
								staticDirectory.mkdirs();
							}
						} else {
							staticDirectory.mkdirs();
						}
						Templatings.unzip(templateFile, staticPath);
						/** Replacing html files content */
						List<File> htmlFiles = resourceService.files(staticPath);
						if (htmlFiles != null && htmlFiles.size() > 0) {
							List<File> htmlFileList = new ArrayList<File>();
							for (File file : htmlFiles) {
								if (file.getName().endsWith(".html")) {
									htmlFileList.add(file);
								}
							}
							sb.setLength(0);
							for (/* Html files */File file : htmlFileList) {
								String path = file.getParentFile().getAbsolutePath();
								/* For publish freemaker template */
								String pubFileName = Templatings.publish(file.getName());
								File pubFile = new File(path + "/" + pubFileName);
								FileUtils.copyFile(file, pubFile);
								/** create freemaker preview html files */
								sb.append(Templatings.path(file.getAbsolutePath(), rootPath))
										.append(CommonConstants.COMMA_SEPARATOR);
								for (EReplaceType type : EReplaceType.values()) {
									Templatings.replace(file, type, Templatings.path(path, rootPath));
								}
								/** create freemaker publish html files */
								for (EReplaceType type : EReplaceType.values()) {
									if (type != EReplaceType.Ftl) {
										Templatings.replace(pubFile, type, Templatings.path(path, rootPath));
									}
								}
							}
						}
					}
				} catch (IOException e) {
					LOG.error("Upload template fail!", e);
					fail();
				}
				/** Template model processing */
				if (sb.toString().length() > 0) {
					sb.deleteCharAt(sb.toString().length() - 1);
					templatingModel.setModelFile(sb.toString().replace('\\', '/').replace("//", "/"));
				}
				if (templatingModel.getId() == null) {
					templatingModel.setCreatorId(customer.getId());
					templatingModel.setCreateTime(new Date());
				}
				templatingModel.setUpdaterId(customer.getId());
				templatingModel.setUpdateTime(new Date());
				if (!(templatingService.saveModel(templatingModel) > 0)) {
					fail();
				}
			}

			@Override
			public String success() {
				return "redirect:/admin/templating/model";
			}

			@Override
			public String fail() {
				model.addAttribute("templatingModel", templatingModel);
				return "/admin/templating/model/edit";
			}

			@Override
			public void error() {
			}
		});
	}

	@RequestMapping(value = "/find/{billId}", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue find(@PathVariable("billId") Integer billId, String callback,
			HttpServletRequest request) {
		ListResult<TemplatingModel> result = new ListResult<TemplatingModel>(HttpStatus.OK.code(),
				HttpStatus.OK.message());
		Customer customer = commonService.user(request);
		List<TemplatingModel> modelList = templatingService.findModelByLicenseAndBillId(customer.getLicense(), billId);
		if (null != modelList && modelList.size() > 0) {
			result.setTotal(modelList.size());
			result.setData(modelList);
		} else {
			result.setTotal(0);
			result.setData(null);
		}
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	/* Model bill processing */
	@RequestMapping("/bill/{parentId}/new")
	public String billNew(@PathVariable("parentId") int parentId, Model model) {
		TemplatingModelBill modelBill = new TemplatingModelBill();
		List<Node<String, String>> typeList = null;
		if (parentId > 0) {
			TemplatingModelBill parent = templatingService.findModelBillById(parentId);
			if (parent.getType() == EModelType.Site) {
				typeList = EModelType.all();
			} else if (parent.getType() == EModelType.Home || parent.getType() == EModelType.Subject) {
				typeList = EModelType.homeAndSubject();
			} else if (parent.getType() == EModelType.List) {
				typeList = EModelType.list();
			}
		} else {
			typeList = EModelType.site();
		}
		modelBill.setParentId(parentId);
		model.addAttribute("modelBill", modelBill);
		model.addAttribute("typeList", typeList);
		return "/admin/templating/model/bill/edit";
	}

	@RequestMapping("/bill/{id}/edit")
	public String billEdit(@PathVariable("id") int id, Model model) {
		TemplatingModelBill modelBill = templatingService.findModelBillById(id);
		List<Node<String, String>> typeList = null;
		if (modelBill.getParentId().intValue() > 0) {
			TemplatingModelBill parent = templatingService.findModelBillById(modelBill.getParentId());
			if (parent.getType() == EModelType.Site) {
				typeList = EModelType.all();
			} else if (parent.getType() == EModelType.Home || parent.getType() == EModelType.Subject) {
				typeList = EModelType.homeAndSubject();
			} else if (parent.getType() == EModelType.List) {
				typeList = EModelType.list();
			}
		} else {
			typeList = EModelType.site();
		}
		model.addAttribute("modelBill", modelBill);
		model.addAttribute("typeList", typeList);
		return "/admin/templating/model/bill/edit";
	}

	@RequestMapping(value = "/bill/save", method = RequestMethod.POST)
	public String billSave(@Validated final TemplatingModelBill modelBill, BindingResult result, final Model model,
			final HttpServletRequest request) {
		final Customer customer = commonService.user(request);
		return super.save(modelBill, result, model, new Operator() {
			@Override
			public void operate() {
				/** Set pathcode */
				if (modelBill.getParentId().intValue() == 0) {
					modelBill.setPathCode("/" + modelBill.getCode() + "/");
				} else {
					TemplatingModelBill parent = templatingService.findModelBillById(modelBill.getParentId());
					modelBill.setPathCode(parent.getPathCode() + modelBill.getCode() + "/");
				}
				if (modelBill.getId() == null) {
					modelBill.setLicense(customer.getLicense());
					modelBill.setOrderId(1);
					modelBill.setCreatorId(customer.getId());
					modelBill.setCreateTime(new Date());
					modelBill.setUpdaterId(customer.getId());
					modelBill.setUpdateTime(new Date());
				} else {
					modelBill.setUpdaterId(customer.getId());
					modelBill.setUpdateTime(new Date());
				}
				templatingService./* Save bill */saveModelBill(modelBill);
			}

			@Override
			public String success() {
				return "redirect:/admin/templating/model";
			}

			@Override
			public String fail() {
				model.addAttribute("modelBill", modelBill);
				return "/admin/templating/model/bill/edit";
			}

			@Override
			public void error() {
			}
		});
	}

	@RequestMapping(value = "/bill/tree", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue tree(String callback, HttpServletRequest request) {
		ObjectResult<DefaultTreeNode> result = new ObjectResult<DefaultTreeNode>(HttpStatus.OK.code(),
				HttpStatus.OK.message());
		Customer customer = commonService.user(request);
		DefaultTreeNode tree = DefaultTreeNode
				.parseTree(templatingService.findModelBillByLicense(customer.getLicense()));
		tree.setName("模板根目录");
		result.setData(tree);
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}
}
