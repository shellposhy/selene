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
}
