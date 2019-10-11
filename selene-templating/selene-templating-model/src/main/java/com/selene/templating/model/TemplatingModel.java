package com.selene.templating.model;

import com.selene.common.BaseModel;
import com.selene.common.tree.TreeNodeModel;
import com.selene.templating.model.constants.EModelType;

/**
 * <b>Selene templating module<b>
 * <p>
 * Mapping object for model
 * 
 * @see TreeNodeModel
 * @author shaobo shih
 * @version 1.0
 */
public class TemplatingModel extends BaseModel {
	private static final long serialVersionUID = 7708275186943402287L;
	private String license;
	private Integer billId;
	private String modelName;
	private String modelCode;
	private EModelType /* For enum */ modelType;
	private String modelFile;
	private Integer orderId;

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public Integer getBillId() {
		return billId;
	}

	public void setBillId(Integer billId) {
		this.billId = billId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public EModelType getModelType() {
		return modelType;
	}

	public void setModelType(EModelType modelType) {
		this.modelType = modelType;
	}

	public String getModelFile() {
		return modelFile;
	}

	public void setModelFile(String modelFile) {
		this.modelFile = modelFile;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
}
