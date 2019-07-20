package com.selene.dataing.model;

import java.util.List;

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
	private String license;
	private String modelName;
	private String modelCode;
	private ELibraryType modelType;
	private String fieldsName;
	private Integer taskId;
	private boolean forSystem;
	private String memo;
	// extension data field
	private List<DataingDataField> modelFieldList;
	private String modelFieldIds;

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
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

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public List<DataingDataField> getModelFieldList() {
		return modelFieldList;
	}

	public void setModelFieldList(List<DataingDataField> modelFieldList) {
		this.modelFieldList = modelFieldList;
	}

	public String getModelFieldIds() {
		return modelFieldIds;
	}

	public void setModelFieldIds(String modelFieldIds) {
		this.modelFieldIds = modelFieldIds;
	}

}
