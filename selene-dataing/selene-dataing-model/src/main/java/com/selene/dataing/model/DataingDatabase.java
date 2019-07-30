package com.selene.dataing.model;

import java.util.Set;
import java.util.TreeSet;

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
	private Set<DataingDataField> fieldList;
	private String fieldValue;
	private String dataFieldsStr;

	public Set<DataingDataField> getFieldList() {
		return fieldList;
	}

	public void setFieldList(Set<DataingDataField> fieldList) {
		this.fieldList = fieldList;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getDataFieldsStr() {
		return dataFieldsStr;
	}

	public void setDataFieldsStr(String dataFieldsStr) {
		this.dataFieldsStr = dataFieldsStr;
	}

	public void addField(DataingDataField dataField) {
		if (this.fieldList == null) {
			this.fieldList = new TreeSet<DataingDataField>();
		}
		if (!fieldList.contains(dataField)) {
			fieldList.add(dataField);
		}
	}
}
