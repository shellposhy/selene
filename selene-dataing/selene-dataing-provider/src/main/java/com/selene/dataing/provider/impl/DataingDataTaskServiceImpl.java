package com.selene.dataing.provider.impl;

import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.common.constants.util.ETaskStatus;
import com.selene.common.constants.util.ETaskType;
import com.selene.dataing.model.DataingDataTask;
import com.selene.dataing.model.service.DataingDataTaskService;
import com.selene.dataing.provider.dao.DataingDataTaskMapper;

/**
 * The {@code DataingDataTaskService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see DataingDataTaskService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(DataingDataTaskService.class)
public class DataingDataTaskServiceImpl implements DataingDataTaskService {

	@Resource
	private DataingDataTaskMapper dataTaskMapper;

	@Override
	public int insert(DataingDataTask dataTask) {
		return dataTaskMapper.insert(dataTask);
	}

	@Override
	public List<DataingDataTask> findByStatus(ETaskStatus status) {
		return dataTaskMapper.findByStatus(status.ordinal());
	}

	@Override
	public List<DataingDataTask> findByTypeAndStatus(ETaskType type, ETaskStatus status) {
		return dataTaskMapper.findByTypeAndStatus(type.ordinal(), status.ordinal());
	}

	@Override
	public int update(DataingDataTask dataTask) {
		return dataTaskMapper.update(dataTask);
	}

	@Override
	public int delete(Integer id) {
		return dataTaskMapper.delete(id);
	}

}
