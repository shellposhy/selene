package com.selene.dataing.model.jdbc;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.selene.dataing.model.DataingDataField;
import com.selene.dataing.model.enums.EDataType;

/**
 * The data{@code Object} change to database data {@code Object}
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class DataingDbData {
	private List<Map.Entry<DataingDataField, Object>> entryList;
	private boolean hasBlob;
	private Integer ver;
	private String description;

	public DataingDbData() {
		entryList = new ArrayList<Map.Entry<DataingDataField, Object>>();
		hasBlob = false;
	}

	public void add(DataingDataField dataField, Object value) {
		EDataType dataType = dataField.getDataType();
		if (EDataType.Blob.equals(dataType) || EDataType.MediumBlob.equals(dataType)) {
			hasBlob = true;
		}
		entryList.add(new AbstractMap.SimpleEntry<DataingDataField, Object>(dataField, value));
	}

	public boolean hasBlob() {
		return hasBlob;
	}

	public int fieldCount() {
		return entryList.size();
	}

	/* ========getter and setter======== */
	public List<Entry<DataingDataField, Object>> getEntryList() {
		return entryList;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}
}
