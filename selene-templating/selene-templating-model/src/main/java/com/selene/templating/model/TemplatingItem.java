package com.selene.templating.model;

import com.selene.common.BaseModel;
import com.selene.templating.model.constants.ECodeListType;
import com.selene.templating.model.constants.ESymbolPosition;
import com.selene.templating.model.constants.ESymbolType;

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
	private String itemCode;
	private String itemName;
	private ECodeListType itemType;
	private String itemMacro;
	private String itemContent;
	private boolean addSymbol;
	private ESymbolPosition symbolPosition;
	private ESymbolType symbolType;
	private Integer lineSize;
	private Integer lengthSize;
	private Integer picWidth;
	private Integer picHeight;

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public ECodeListType getItemType() {
		return itemType;
	}

	public void setItemType(ECodeListType itemType) {
		this.itemType = itemType;
	}

	public String getItemMacro() {
		return itemMacro;
	}

	public void setItemMacro(String itemMacro) {
		this.itemMacro = itemMacro;
	}

	public String getItemContent() {
		return itemContent;
	}

	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}

	public boolean isAddSymbol() {
		return addSymbol;
	}

	public void setAddSymbol(boolean addSymbol) {
		this.addSymbol = addSymbol;
	}

	public ESymbolPosition getSymbolPosition() {
		return symbolPosition;
	}

	public void setSymbolPosition(ESymbolPosition symbolPosition) {
		this.symbolPosition = symbolPosition;
	}

	public ESymbolType getSymbolType() {
		return symbolType;
	}

	public void setSymbolType(ESymbolType symbolType) {
		this.symbolType = symbolType;
	}

	public Integer getLineSize() {
		return lineSize;
	}

	public void setLineSize(Integer lineSize) {
		this.lineSize = lineSize;
	}

	public Integer getLengthSize() {
		return lengthSize;
	}

	public void setLengthSize(Integer lengthSize) {
		this.lengthSize = lengthSize;
	}

	public Integer getPicWidth() {
		return picWidth;
	}

	public void setPicWidth(Integer picWidth) {
		this.picWidth = picWidth;
	}

	public Integer getPicHeight() {
		return picHeight;
	}

	public void setPicHeight(Integer picHeight) {
		this.picHeight = picHeight;
	}

}
