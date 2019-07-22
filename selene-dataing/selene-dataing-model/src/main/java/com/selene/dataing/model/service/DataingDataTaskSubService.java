package com.selene.dataing.model.service;

import java.util.List;

import com.selene.common.constants.util.ETaskStatus;
import com.selene.common.constants.util.ETaskSubType;
import com.selene.dataing.model.DataingDataTaskSub;

/**
 * The {@code DataingDataTaskSub} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface DataingDataTaskSubService {
	public int insert(DataingDataTaskSub dataTaskSub);

	public List<DataingDataTaskSub> findByTaskId(Integer taskId);

	public List<DataingDataTaskSub> findByTaskIdAndStatus(Integer taskId, ETaskStatus status);

	public List<DataingDataTaskSub> findByTaskIdAndTypeAndStatus(Integer taskId, ETaskSubType type, ETaskStatus status);

	public int update(DataingDataTaskSub dataTaskSub);

	public int delete(Integer id);
}
