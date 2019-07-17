package com.selene.dataing.model;

import com.selene.common.BaseModel;
import com.selene.common.constants.util.ELibraryType;

/**
 * <b>Selene dataing module<b>
 * <p>
 * Mapping object for data model
 * 
 * @see BaseModel
 * @author shaobo shih
 * @version 1.0
 */
public class DataingDataModel extends BaseModel {
	private static final long serialVersionUID = -5378593458091481855L;
	private String modelName;
	private String modelCode;
	private ELibraryType modelType;
	private String fieldsName;
	private boolean forSystem;
	private String memo;

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

	public ELibraryType getModelType() {
		return modelType;
	}

	public void setModelType(ELibraryType modelType) {
		this.modelType = modelType;
	}

	public String getFieldsName() {
		return fieldsName;
	}

	public void setFieldsName(String fieldsName) {
		this.fieldsName = fieldsName;
	}

	public boolean isForSystem() {
		return forSystem;
	}

	public void setForSystem(boolean forSystem) {
		this.forSystem = forSystem;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
