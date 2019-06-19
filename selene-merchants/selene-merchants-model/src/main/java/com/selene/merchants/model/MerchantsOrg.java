package com.selene.merchants.model;

import java.util.Date;

import com.selene.common.BaseModel;
import com.selene.common.tree.TreeNodeModel;
import com.selene.merchants.model.enums.EOrgStatus;

/**
 * <b>Selene dataing module<b>
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
	private Date createTime;
	private Integer creatorId;
	private Date updateTime;
	private Integer updaterId;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(Integer updaterId) {
		this.updaterId = updaterId;
	}

}
