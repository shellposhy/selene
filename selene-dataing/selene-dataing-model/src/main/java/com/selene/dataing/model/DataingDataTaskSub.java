package com.selene.dataing.model;

import com.selene.common.BaseModel;
import com.selene.common.constants.util.ETaskStatus;

/**
 * <b>Selene dataing module<b>
 * <p>
 * Mapping object for data sub task
 * 
 * @see BaseModel
 * @author shaobo shih
 * @version 1.0
 */
public class DataingDataTaskSub extends BaseModel {
	private static final long serialVersionUID = 3181400548913761007L;
	private Integer taskId;
	private String subtaskName;
	private Integer subtaskType;
	private String subtaskTarget;
	private ETaskStatus subtaskStatus;
	private Integer subtaskProgress;
	private String subtaskMemo;

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getSubtaskName() {
		return subtaskName;
	}

	public void setSubtaskName(String subtaskName) {
		this.subtaskName = subtaskName;
	}

	public Integer getSubtaskType() {
		return subtaskType;
	}

	public void setSubtaskType(Integer subtaskType) {
		this.subtaskType = subtaskType;
	}

	public String getSubtaskTarget() {
		return subtaskTarget;
	}

	public void setSubtaskTarget(String subtaskTarget) {
		this.subtaskTarget = subtaskTarget;
	}

	public ETaskStatus getSubtaskStatus() {
		return subtaskStatus;
	}

	public void setSubtaskStatus(ETaskStatus subtaskStatus) {
		this.subtaskStatus = subtaskStatus;
	}

	public Integer getSubtaskProgress() {
		return subtaskProgress;
	}

	public void setSubtaskProgress(Integer subtaskProgress) {
		this.subtaskProgress = subtaskProgress;
	}

	public String getSubtaskMemo() {
		return subtaskMemo;
	}

	public void setSubtaskMemo(String subtaskMemo) {
		this.subtaskMemo = subtaskMemo;
	}

}
