package com.selene.merchants.model;

import com.selene.common.BaseModel;

/**
 * <b>Selene merchants module<b>
 * <p>
 * Mapping object for merchants user
 * 
 * @see BaseModel
 * @author shaobo shih
 * @version 1.0
 */
public class MerchantsUserRole extends BaseModel {
	private static final long serialVersionUID = 9052401417235831800L;
	private Integer groupId;
	private Integer userId;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
