package com.selene.dataing.model.base;

import java.util.Date;

import com.selene.common.tree.TreeNodeModel;
import com.selene.dataing.model.enums.ELibraryNodeType;
import com.selene.dataing.model.enums.ELibraryType;
import com.selene.dataing.model.enums.EDataStatus;

/**
 * Database base class
 * 
 * @see TreeNodeModel
 * @author shaobo shih
 * @version 1.0
 */
public class BaseDatabase<T extends BaseDatabase<T>> extends TreeNodeModel<T> {
	private static final long serialVersionUID = 8890931730090282329L;
	private String code;
	private String pathCode;
	private ELibraryType type;
	private ELibraryNodeType nodeType;
	private Integer modelId;
	private EDataStatus status;
	private int orderId;
	private Integer taskId;
	private Date dataUpdateTime;
	private int tables;

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

	public ELibraryType getType() {
		return type;
	}

	public void setType(ELibraryType type) {
		this.type = type;
	}

	public ELibraryNodeType getNodeType() {
		return nodeType;
	}

	public void setNodeType(ELibraryNodeType nodeType) {
		this.nodeType = nodeType;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public EDataStatus getStatus() {
		return status;
	}

	public void setStatus(EDataStatus status) {
		this.status = status;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Date getDataUpdateTime() {
		return dataUpdateTime;
	}

	public void setDataUpdateTime(Date dataUpdateTime) {
		this.dataUpdateTime = dataUpdateTime;
	}

	public int getTables() {
		return tables;
	}

	public void setTables(int tables) {
		this.tables = tables;
	}

}
