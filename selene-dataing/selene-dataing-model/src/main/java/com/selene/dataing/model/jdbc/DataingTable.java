package com.selene.dataing.model.jdbc;

import java.util.List;

import com.selene.dataing.model.DataingDataField;

public class DataingTable {
	private String name;
	private List<DataingDataField> fieldList;
	private List<String> names;
	private List<DataingDataField> adds;
	private List<DataingDataField> drops;
	private List<DataingDataField> changes;

	public DataingTable(String name, List<DataingDataField> fieldList) {
		this.name = name;
		this.fieldList = fieldList;
	}

	public DataingTable() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DataingDataField> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<DataingDataField> fieldList) {
		this.fieldList = fieldList;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public List<DataingDataField> getAdds() {
		return adds;
	}

	public void setAdds(List<DataingDataField> adds) {
		this.adds = adds;
	}

	public List<DataingDataField> getDrops() {
		return drops;
	}

	public void setDrops(List<DataingDataField> drops) {
		this.drops = drops;
	}

	public List<DataingDataField> getChanges() {
		return changes;
	}

	public void setChanges(List<DataingDataField> changes) {
		this.changes = changes;
	}

}
