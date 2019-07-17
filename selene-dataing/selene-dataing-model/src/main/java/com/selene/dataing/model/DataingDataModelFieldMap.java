package com.selene.dataing.model;

import com.selene.common.BaseModel;

/**
 * <b>Selene dataing module<b>
 * <p>
 * Mapping object for data model field
 * 
 * @see BaseModel
 * @author shaobo shih
 * @version 1.0
 */
public class DataingDataModelFieldMap extends BaseModel {
	private static final long serialVersionUID = 1L;
	private Integer modelId;
	private Integer fieldId;

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public Integer getFieldId() {
		return fieldId;
	}

	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}

}
