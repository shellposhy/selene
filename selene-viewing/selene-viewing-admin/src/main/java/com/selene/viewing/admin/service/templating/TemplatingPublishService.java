package com.selene.viewing.admin.service.templating;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.selene.common.constants.CommonConstants;
import com.selene.common.constants.ResourceConstants;
import com.selene.common.page.ListArticle;
import com.selene.templating.model.TemplatingItem;
import com.selene.templating.model.TemplatingModel;
import com.selene.templating.model.TemplatingPage;
import com.selene.templating.model.constants.EPageStatus;
import com.selene.templating.model.util.publisher.HomePages;
import com.selene.viewing.admin.service.ResourceService;

import cn.com.lemon.base.Strings;

@Service
public class TemplatingPublishService {
	private static final Logger LOG = LoggerFactory.getLogger(TemplatingPublishService.class.getName());
	private static final String CLASSPATH = TemplatingPublishService.class.getResource("/").getPath();
	@Resource
	private TemplatingService templatingService;
	@Resource
	private ResourceService resourceService;

	/**
	 * Home publish
	 * 
	 * @param pageId
	 * @return
	 */
	public void home(Integer pageId) {
		String ftlDir = CLASSPATH.substring(0, CLASSPATH.indexOf("WEB-INF")) + "WEB-INF"
				+ ResourceConstants.BASE_TEMPLATE_PATH;
		Map<String, Object> data = new HashMap<String, Object>();
		TemplatingPage page = templatingService.findPageById(pageId);
		if (null != pageId) {
			TemplatingModel model = templatingService.findModelById(page.getPageModelId());
			String ftlFullName = model.getModelFile();
			File /* Ftl template file */ ftlFile = new File(ftlDir + ftlFullName);
			LOG.info("ftl path=" + ftlFile.getAbsolutePath());
			if (ftlFile.exists() && ftlFile.isFile()) {
				List<TemplatingItem> itemList = templatingService.findModelItems(model.getId());
				for (TemplatingItem item : itemList) {
					ListArticle listArticle = templatingService.packagePageData(pageId, item.getId());
					data.put(item.getItemCode(), listArticle);
				}
				String /* Output dir */ outputDir = resourceService.realPage();
				String output = outputDir + page.getPathCode() + ftlFile.getName();
				/* File not exist create first */
				if (!Strings.isNullOrEmpty(output)) {
					File outputFile = new File(output);
					if (!outputFile.getParentFile().exists()) {
						outputFile.getParentFile().mkdirs();
					}
					try {
						if (!outputFile.exists()) {
							outputFile.createNewFile();
						}
						// Output template name format:*_publish.html
						String destPublishFileName = ftlFile.getName().substring(0, ftlFile.getName().lastIndexOf("."))
								+ CommonConstants.DEFAULT_FREEMARKER_PUBLISH_PREFIX
								+ ftlFile.getName().substring(ftlFile.getName().lastIndexOf("."));
						if (HomePages.publish(ftlFile.getParentFile().getAbsolutePath(), destPublishFileName, output,
								data)) {
							String pageHtmlPath = resourceService.realRelativePage() + page.getPathCode()
									+ ftlFile.getName();
							page.setPageHtmlPath(pageHtmlPath);
							page.setStatus(true);
							page.setPageStatus(EPageStatus.Published);
							page.setPublishTime(new Date());
							templatingService./* Update page */savePage(page);
						}
					} catch (IOException e) {
						LOG.error("[" + output + "] create Fail!", e);
					}
				}
			}
		}
	}
}
