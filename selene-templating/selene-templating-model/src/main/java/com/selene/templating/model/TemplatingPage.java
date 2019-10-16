package com.selene.templating.model;

import java.util.Date;

import com.selene.common.tree.TreeNodeModel;
import com.selene.templating.model.constants.EModelType;
import com.selene.templating.model.constants.EPageStatus;

/**
 * <b>Selene templating module<b>
 * <p>
 * Mapping object for page
 * 
 * @see TreeNodeModel
 * @author shaobo shih
 * @version 1.0
 */
public class TemplatingPage extends TreeNodeModel<TemplatingPage> {
	private static final long serialVersionUID = -6814818381604387038L;
	private String license;
	private Integer pageModelId;
	private String code;
	private EModelType pageType;
	private Boolean status;
	private EPageStatus pageStatus;
	private String pageHtmlPath;
	private Date publishTime;

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public Integer getPageModelId() {
		return pageModelId;
	}

	public void setPageModelId(Integer pageModelId) {
		this.pageModelId = pageModelId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public EModelType getPageType() {
		return pageType;
	}

	public void setPageType(EModelType pageType) {
		this.pageType = pageType;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public EPageStatus getPageStatus() {
		return pageStatus;
	}

	public void setPageStatus(EPageStatus pageStatus) {
		this.pageStatus = pageStatus;
	}

	public String getPageHtmlPath() {
		return pageHtmlPath;
	}

	public void setPageHtmlPath(String pageHtmlPath) {
		this.pageHtmlPath = pageHtmlPath;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

}
