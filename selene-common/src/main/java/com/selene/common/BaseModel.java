package com.selene.common;

import java.io.Serializable;
import java.util.Date;

/**
 * Base model for database mapping object
 * 
 * @author shaobo shih
 * @version 1.0
 */
public abstract class BaseModel implements Serializable {
	private static final long serialVersionUID = -7648946031981422557L;
	protected Integer id;
	protected Integer creatorId;
	protected Date createTime;
	protected Integer updaterId;
	protected Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(Integer updaterId) {
		this.updaterId = updaterId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
