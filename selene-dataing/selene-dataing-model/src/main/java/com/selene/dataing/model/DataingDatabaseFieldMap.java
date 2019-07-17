package com.selene.dataing.model;

import com.selene.common.BaseModel;
import com.selene.common.constants.util.ELibraryType;

/**
 * <b>Selene dataing module<b>
 * <p>
 * Mapping object for database field
 * 
 * @see BaseModel
 * @author shaobo shih
 * @version 1.0
 */
public class DataingDatabaseFieldMap extends BaseModel {
	private static final long serialVersionUID = -6001466060415140577L;
	private Integer baseId;
	private Integer fieldId;
	private ELibraryType type;
	private boolean isDisplay;

	public Integer getBaseId() {
		return baseId;
	}

	public void setBaseId(Integer baseId) {
		this.baseId = baseId;
	}

	public Integer getFieldId() {
		return fieldId;
	}

	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}

	public ELibraryType getType() {
		return type;
	}

	public void setType(ELibraryType type) {
		this.type = type;
	}

	public boolean isDisplay() {
		return isDisplay;
	}

	public void setDisplay(boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

}
