package com.selene.templating.model;

import com.selene.common.BaseModel;

/**
 * <b>Selene templating module<b>
 * <p>
 * Mapping object for page item
 * 
 * @see BaseModel
 * @author shaobo shih
 * @version 1.0
 */
public class TemplatingItem extends BaseModel {
	private static final long serialVersionUID = -7550835401110800082L;
	private Integer modelId;
	private String itemName;
	private Integer itemType;
	private Integer itemContentType;
	private String itemContent;
	private String itemHtml;
	private Integer lineNumber;
	private Integer dataNumber;

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public Integer getItemContentType() {
		return itemContentType;
	}

	public void setItemContentType(Integer itemContentType) {
		this.itemContentType = itemContentType;
	}

	public String getItemContent() {
		return itemContent;
	}

	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}

	public String getItemHtml() {
		return itemHtml;
	}

	public void setItemHtml(String itemHtml) {
		this.itemHtml = itemHtml;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public Integer getDataNumber() {
		return dataNumber;
	}

	public void setDataNumber(Integer dataNumber) {
		this.dataNumber = dataNumber;
	}

}
