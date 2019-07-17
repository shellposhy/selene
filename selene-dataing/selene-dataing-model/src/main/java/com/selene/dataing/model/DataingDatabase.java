package com.selene.dataing.model;

import java.util.ArrayList;
import java.util.List;

import com.selene.common.BaseModel;
import com.selene.common.Database;

/**
 * <b>Selene dataing module<b>
 * <p>
 * Mapping object for database
 * 
 * @see BaseModel
 * @author shaobo shih
 * @version 1.0
 */
public class DataingDatabase extends Database<DataingDatabase> {
	private static final long serialVersionUID = 2388455131378854343L;
	private List<DataingDataField> fieldList;
	private String fieldValue;

	public List<DataingDataField> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<DataingDataField> fieldList) {
		this.fieldList = fieldList;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public void addField(DataingDataField dataField) {
		if (this.fieldList == null) {
			this.fieldList = new ArrayList<DataingDataField>();
		}
		if (!fieldList.contains(dataField)) {
			fieldList.add(dataField);
		}
	}
}
