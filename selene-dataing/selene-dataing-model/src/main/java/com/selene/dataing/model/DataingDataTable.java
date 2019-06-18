package com.selene.dataing.model;

import com.selene.common.BaseModel;

/**
 * <b>Selene dataing module<b>
 * <p>
 * Mapping object for database table
 * 
 * @see BaseModel
 * @author shaobo shih
 * @version 1.0
 */
public class DataingDataTable extends BaseModel {
	private static final long serialVersionUID = 7823002623814480399L;
	private Integer baseId;
	private String name;
	private Integer rowCount;

	public Integer getBaseId() {
		return baseId;
	}

	public void setBaseId(Integer baseId) {
		this.baseId = baseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRowCount() {
		return rowCount;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

}
