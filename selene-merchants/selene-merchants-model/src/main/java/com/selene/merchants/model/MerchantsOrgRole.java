package com.selene.merchants.model;

import com.selene.common.BaseModel;

/**
 * <b>Selene merchants module<b>
 * <p>
 * Mapping object for merchants organization role
 * 
 * @see BaseModel
 * @author shaobo shih
 * @version 1.0
 */
public class MerchantsOrgRole extends BaseModel {
	private static final long serialVersionUID = -7582607956975675359L;
	private Integer orgId;
	private Integer groupId;

	public MerchantsOrgRole() {
	}

	public MerchantsOrgRole(Integer orgId, Integer groupId) {
		this.orgId = orgId;
		this.groupId = groupId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

}
