package com.selene.dataing.model.jdbc;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.selene.common.constants.FieldsConstants;
import com.selene.common.constants.util.EDataStatus;

/**
 * Basic data objects, providing a database object and Java object
 * transformation channel.
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class DataingBaseData {
	private Integer id;
	private Integer tableId;
	private Integer baseId;
	private EDataStatus dataStatus;
	private List<Integer> refBaseIdList;
	private Map<String, Object> lowerFieldMap;
	private String description;
	private String ver;

	public DataingBaseData() {
		lowerFieldMap = new ConcurrentHashMap<String, Object>();
	}

	public void put(String field, Object value) {
		if (FieldsConstants.BASE_ID.equalsIgnoreCase(field)) {
			this.baseId = (Integer) value;
		} else if (FieldsConstants.TABLE_ID.equalsIgnoreCase(field)) {
			this.tableId = (Integer) value;
		} else if (FieldsConstants.ID.equalsIgnoreCase(field)) {
			this.id = Integer.parseInt(value.toString());
		}
		lowerFieldMap.put(field.toLowerCase(), value);
	}

	public Object get(String field) {
		return lowerFieldMap.get(field);
	}

	public void remove(String field) {
		lowerFieldMap.remove(field.toLowerCase());
	}

	public Collection<Object> getValueSet() {
		return lowerFieldMap.values();
	}

	public int fieldCount() {
		return lowerFieldMap.size();
	}

	public Short getDataStatusValue() {
		return (Short) this.get(FieldsConstants.DATA_STATUS);
	}

	/* ========getter and setter======== */
	public Integer getId() {
		if (this.id == null) {
			String idStr = (String) this.get(FieldsConstants.ID);
			if (idStr != null) {
				this.id = Integer.valueOf(idStr);
			} else {
				return null;
			}
		}
		return this.id;
	}

	public void setId(Integer id) {
		lowerFieldMap.put(FieldsConstants.ID.toLowerCase(), id);
		this.id = id;
	}

	public EDataStatus getDataStatus() {
		if (dataStatus == null) {
			Short tmp = getDataStatusValue();
			if (tmp == null) {
				return null;
			} else {
				return EDataStatus.values()[tmp];
			}
		}
		return dataStatus;
	}

	public void setDataStatus(EDataStatus dataStatus) {
		put(FieldsConstants.DATA_STATUS, (short) dataStatus.ordinal());
		this.dataStatus = dataStatus;
	}

	public Integer getTableId() {
		return tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

	public Integer getBaseId() {
		return baseId;
	}

	public void setBaseId(Integer baseId) {
		this.baseId = baseId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public void setLowerFieldMap(Map<String, Object> lowerFieldMap) {
		this.lowerFieldMap = lowerFieldMap;
	}

	public Set<String> getLowerFieldSet() {
		return lowerFieldMap.keySet();
	}

	public List<Integer> getRefBaseIdList() {
		return refBaseIdList;
	}

	public void setRefBaseIdList(List<Integer> refBaseIdList) {
		this.refBaseIdList = refBaseIdList;
	}
}
