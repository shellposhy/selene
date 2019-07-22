package com.selene.dataing.model;

import com.selene.common.BaseModel;
import com.selene.common.constants.util.ETaskStatus;
import com.selene.common.constants.util.ETaskType;

/**
 * <b>Selene dataing module<b>
 * <p>
 * Mapping object for data task
 * 
 * @see BaseModel
 * @author shaobo shih
 * @version 1.0
 */
public class DataingDataTask extends BaseModel {
	private static final long serialVersionUID = 3181400548913761007L;
	private String taskName;
	private String taskCode;
	private ETaskType taskType;
	private String taskInfo;
	private String taskTarget;
	private ETaskStatus taskStatus;
	private Integer taskProgress;
	private String taskMemo;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public ETaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(ETaskType taskType) {
		this.taskType = taskType;
	}

	public String getTaskInfo() {
		return taskInfo;
	}

	public void setTaskInfo(String taskInfo) {
		this.taskInfo = taskInfo;
	}

	public String getTaskTarget() {
		return taskTarget;
	}

	public void setTaskTarget(String taskTarget) {
		this.taskTarget = taskTarget;
	}

	public ETaskStatus getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(ETaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Integer getTaskProgress() {
		return taskProgress;
	}

	public void setTaskProgress(Integer taskProgress) {
		this.taskProgress = taskProgress;
	}

	public String getTaskMemo() {
		return taskMemo;
	}

	public void setTaskMemo(String taskMemo) {
		this.taskMemo = taskMemo;
	}

}
