package com.selene.dataing.model.service;

import java.util.List;

import com.selene.common.constants.util.ETaskStatus;
import com.selene.common.constants.util.ETaskType;
import com.selene.dataing.model.DataingDataTask;

/**
 * The {@code DataingDataTask} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface DataingDataTaskService {
	public int insert(DataingDataTask dataTask);

	public List<DataingDataTask> findByStatus(ETaskStatus status);

	public List<DataingDataTask> findByTypeAndStatus(ETaskType type, ETaskStatus status);

	public int update(DataingDataTask dataTask);

	public int delete(Integer id);
}
