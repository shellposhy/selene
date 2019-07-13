package com.selene.merchants.model;

import java.util.Date;

import com.selene.common.BaseModel;
import com.selene.common.tree.TreeNodeModel;
import com.selene.merchants.model.enums.EOrgStatus;
import com.selene.merchants.model.enums.EOrgType;

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
	private boolean superStatus;
	private String license;
	private EOrgType orgType;
	private String code;
	private Integer orderId;
	private EOrgStatus status;
	private boolean inherit;
	// Expand field for org role list string
	private String treeSelId;

	public MerchantsOrg() {
	}

	public MerchantsOrg(boolean superStatus, String license, EOrgType orgType, String name, String code,
			Integer parentId, Integer orderId, EOrgStatus status, boolean inherit, Integer creatorId, Date createTime,
			Integer updaterId, Date updateTime) {
		this.superStatus = superStatus;
		this.license = license;
		this.orgType = orgType;
		this.name = name;
		this.code = code;
		this.parentId = parentId;
		this.orderId = orderId;
		this.status = status;
		this.inherit = inherit;
		this.creatorId = creatorId;
		this.createTime = createTime;
		this.updaterId = updaterId;
		this.updateTime = updateTime;
	}

	public boolean isSuperStatus() {
		return superStatus;
	}

	public void setSuperStatus(boolean superStatus) {
		this.superStatus = superStatus;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public EOrgType getOrgType() {
		return orgType;
	}

	public void setOrgType(EOrgType orgType) {
		this.orgType = orgType;
	}

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

	public String getTreeSelId() {
		return treeSelId;
	}

	public void setTreeSelId(String treeSelId) {
		this.treeSelId = treeSelId;
	}

}
