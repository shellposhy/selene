package com.selene.dataing.model;

import java.util.Date;

import com.selene.common.constants.util.EDataStatus;
import com.selene.common.tree.TreeNodeModel;

/**
 * <b>Selene dataing module<b>
 * <p>
 * Mapping object for data tag
 * 
 * @see TreeNodeModel
 * @author shaobo shih
 * @version 1.0
 */
public class DataingDataTag extends TreeNodeModel<DataingDataTag> {
	private static final long serialVersionUID = -2644133283991829293L;
	private String license;
	private Integer type;
	private String code;
	private String pathCode;
	private Integer orderId;
	private EDataStatus status;
	private Date createTime;
	private Integer creatorId;
	private Date updateTime;
	private Integer updaterId;
	private boolean forDataNode;

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPathCode() {
		return pathCode;
	}

	public void setPathCode(String pathCode) {
		this.pathCode = pathCode;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public EDataStatus getStatus() {
		return status;
	}

	public void setStatus(EDataStatus status) {
		this.status = status;
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

	public boolean isForDataNode() {
		return forDataNode;
	}

	public void setForDataNode(boolean forDataNode) {
		this.forDataNode = forDataNode;
	}

}
