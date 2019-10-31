package com.selene.templating.model;

import com.selene.common.BaseModel;
import com.selene.templating.model.constants.EFilterDir;
import com.selene.templating.model.constants.EFilterStatus;
import com.selene.templating.model.constants.EFilterType;

/**
 * <b>Selene templating module<b>
 * <p>
 * Mapping object for content
 * 
 * @see TemplatingContent
 * @author shaobo shih
 * @version 1.0
 */
public class TemplatingContent extends BaseModel {
	private static final long serialVersionUID = 1L;
	private Integer pageId;
	private Integer itemId;
	private String contentThumb;
	private String contentName;
	private String contentSummary;
	private Integer contentType;
	private Integer baseId;
	private EFilterStatus filterStatus;
	private EFilterDir filterDir;
	private EFilterType filterType;
	private String filterValue;
	private String filterCondition;
	// extend
	private String itemCode;

	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getContentThumb() {
		return contentThumb;
	}

	public void setContentThumb(String contentThumb) {
		this.contentThumb = contentThumb;
	}

	public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public String getContentSummary() {
		return contentSummary;
	}

	public void setContentSummary(String contentSummary) {
		this.contentSummary = contentSummary;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public Integer getBaseId() {
		return baseId;
	}

	public void setBaseId(Integer baseId) {
		this.baseId = baseId;
	}

	public EFilterStatus getFilterStatus() {
		return filterStatus;
	}

	public void setFilterStatus(EFilterStatus filterStatus) {
		this.filterStatus = filterStatus;
	}

	public EFilterDir getFilterDir() {
		return filterDir;
	}

	public void setFilterDir(EFilterDir filterDir) {
		this.filterDir = filterDir;
	}

	public EFilterType getFilterType() {
		return filterType;
	}

	public void setFilterType(EFilterType filterType) {
		this.filterType = filterType;
	}

	public String getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}

	public String getFilterCondition() {
		return filterCondition;
	}

	public void setFilterCondition(String filterCondition) {
		this.filterCondition = filterCondition;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

}
