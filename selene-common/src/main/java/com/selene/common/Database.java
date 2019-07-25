package com.selene.common;

import java.util.Date;

import com.selene.common.constants.util.EDataStatus;
import com.selene.common.constants.util.ELibraryNodeType;
import com.selene.common.constants.util.ELibraryType;
import com.selene.common.tree.TreeNodeModel;

/**
 * Database base class
 * 
 * @see TreeNodeModel
 * @author shaobo shih
 * @version 1.0
 */
public class Database<T extends Database<T>> extends TreeNodeModel<T> {
	private static final long serialVersionUID = -3471570371542285596L;
	private String license;
	private String code;
	private String pathCode;
	private ELibraryType type;
	private ELibraryNodeType nodeType;
	private Integer modelId;
	private Integer subjectType;
	private Integer subjectId;
	private EDataStatus status;
	private Integer orderId;
	private Integer taskId;
	private Date dataUpdateTime;
	private int tables;

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
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

	public Integer getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public EDataStatus getStatus() {
		return status;
	}

	public void setStatus(EDataStatus status) {
		this.status = status;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
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
