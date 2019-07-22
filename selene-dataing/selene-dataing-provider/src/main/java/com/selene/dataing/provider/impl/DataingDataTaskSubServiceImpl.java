package com.selene.dataing.provider.impl;

import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.common.constants.util.ETaskStatus;
import com.selene.common.constants.util.ETaskSubType;
import com.selene.dataing.model.DataingDataTaskSub;
import com.selene.dataing.model.service.DataingDataTaskSubService;
import com.selene.dataing.provider.dao.DataingDataTaskSubMapper;

/**
 * The {@code DataingDataTaskSubService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see DataingDataTaskSubService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(DataingDataTaskSubService.class)
public class DataingDataTaskSubServiceImpl implements DataingDataTaskSubService {

	@Resource
	private DataingDataTaskSubMapper dataTaskSubMapper;

	@Override
	public int insert(DataingDataTaskSub dataTaskSub) {
		return dataTaskSubMapper.insert(dataTaskSub);
	}

	@Override
	public List<DataingDataTaskSub> findByTaskId(Integer taskId) {
		return dataTaskSubMapper.findByTaskId(taskId);
	}

	@Override
	public List<DataingDataTaskSub> findByTaskIdAndStatus(Integer taskId, ETaskStatus status) {
		return dataTaskSubMapper.findByTaskIdAndStatus(taskId, status.ordinal());
	}

	@Override
	public List<DataingDataTaskSub> findByTaskIdAndTypeAndStatus(Integer taskId, ETaskSubType type,
			ETaskStatus status) {
		return dataTaskSubMapper.findByTaskIdAndTypeAndStatus(taskId, type.ordinal(), status.ordinal());
	}

	@Override
	public int update(DataingDataTaskSub dataTaskSub) {
		return dataTaskSubMapper.update(dataTaskSub);
	}

	@Override
	public int delete(Integer id) {
		return dataTaskSubMapper.delete(id);
	}

}
