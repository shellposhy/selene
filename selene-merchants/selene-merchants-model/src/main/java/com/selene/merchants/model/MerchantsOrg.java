package com.selene.merchants.model;

import com.selene.common.BaseModel;
import com.selene.common.tree.TreeNodeModel;
import com.selene.merchants.model.enums.EOrgStatus;

/**
 * <b>Selene merchants module<b>
 * <p>
 * Mapping object for merchants organization
 * 
 * @see BaseModel
 * @author shaobo shih
 * @version 1.0
 */
public class MerchantsOrg extends TreeNodeModel<MerchantsOrg> {
	private static final long serialVersionUID = 8057597442846210040L;
	private String code;
	private Integer orderId;
	private EOrgStatus status;
	private boolean inherit;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public EOrgStatus getStatus() {
		return status;
	}

	public void setStatus(EOrgStatus status) {
		this.status = status;
	}

	public boolean isInherit() {
		return inherit;
	}

	public void setInherit(boolean inherit) {
		this.inherit = inherit;
	}

}
