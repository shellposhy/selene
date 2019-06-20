package com.selene.merchants.model;

import com.selene.common.BaseModel;

/**
 * <b>Selene merchants module<b>
 * <p>
 * Mapping object for merchants role action
 * 
 * @see BaseModel
 * @author shaobo shih
 * @version 1.0
 */
public class MerchantsRoleAction extends BaseModel {
	private static final long serialVersionUID = -969974465143541862L;
	private Integer groupId;
	private Integer actionId;
	private Integer type;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getActionId() {
		return actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
