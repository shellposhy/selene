package com.selene.dataing.provider.impl;

import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.dataing.model.DataingDataTable;
import com.selene.dataing.model.service.DataingDataTableService;
import com.selene.dataing.provider.dao.DataingDataTableMapper;

/**
 * The {@code DataingDataTableService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see DataingDataTableService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(DataingDataTableService.class)
public class DataingDataTableServiceImpl implements DataingDataTableService {

	@Resource
	private DataingDataTableMapper dataTableMapper;

	@Override
	public List<DataingDataTable> findByBaseId(Integer dbId) {
		return dataTableMapper.findByBaseId(dbId);
	}

	@Override
	public List<DataingDataTable> findByName(String tableName) {
		return dataTableMapper.findByName(tableName);
	}

	@Override
	public DataingDataTable find(Integer id) {
		return dataTableMapper.find(id);
	}

	@Override
	public int insert(DataingDataTable dataTable) {
		int result = dataTableMapper.insert(dataTable);
		return result > 0 ? dataTable.getId() : result;
	}

	@Override
	public int increment(Integer id, Integer num) {
		return dataTableMapper.increment(id, num);
	}

	@Override
	public int decrement(Integer id, Integer num) {
		return dataTableMapper.decrement(id, num);
	}

	@Override
	public int update(DataingDataTable dataTable) {
		return dataTableMapper.update(dataTable);
	}

	@Override
	public int delete(Integer id) {
		return dataTableMapper.delete(id);
	}

}
