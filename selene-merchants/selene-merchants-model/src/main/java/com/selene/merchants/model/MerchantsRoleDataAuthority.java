package com.selene.merchants.model;

import java.util.Date;

import com.selene.common.BaseModel;
import com.selene.common.tree.TreeNodeModel;

/**
 * <b>Selene merchants module<b>
 * <p>
 * Mapping object for merchants role data authority
 * 
 * @see BaseModel
 * @author shaobo shih
 * @version 1.0
 */
public class MerchantsRoleDataAuthority extends TreeNodeModel<MerchantsRoleDataAuthority> {
	private static final long serialVersionUID = -8888222162754135863L;
	private Integer groupId;
	/** {@code ELibraryType} */
	private Integer objectType;
	private Integer objectId;
	private String allowActionType;
	private boolean allDataTime;
	private Date startDataTime;
	private Date endDateTime;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getObjectType() {
		return objectType;
	}

	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}

	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	public String getAllowActionType() {
		return allowActionType;
	}

	public void setAllowActionType(String allowActionType) {
		this.allowActionType = allowActionType;
	}

	public boolean isAllDataTime() {
		return allDataTime;
	}

	public void setAllDataTime(boolean allDataTime) {
		this.allDataTime = allDataTime;
	}

	public Date getStartDataTime() {
		return startDataTime;
	}

	public void setStartDataTime(Date startDataTime) {
		this.startDataTime = startDataTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

}
