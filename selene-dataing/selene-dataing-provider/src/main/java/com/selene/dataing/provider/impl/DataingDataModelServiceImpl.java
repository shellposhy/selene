package com.selene.dataing.provider.impl;

import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.dataing.model.DataingDataModel;
import com.selene.dataing.model.service.DataingDataModelService;
import com.selene.dataing.provider.dao.DataingDataModelMapper;

/**
 * The {@code DataingDataModelService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see DataingDataModelService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(DataingDataModelService.class)
public class DataingDataModelServiceImpl implements DataingDataModelService {

	@Resource
	private DataingDataModelMapper dataModelMapper;

	@Override
	public int insert(DataingDataModel dataModel) {
		return dataModelMapper.insert(dataModel);
	}

	@Override
	public DataingDataModel find(Integer id) {
		return dataModelMapper.find(id);
	}

	@Override
	public List<DataingDataModel> findByType(Integer modelType) {
		return dataModelMapper.findByType(modelType);
	}

	@Override
	public List<DataingDataModel> findByPage(Integer first, Integer size) {
		return dataModelMapper.findByPage(first, size);
	}

	@Override
	public int count(Boolean flag) {
		return dataModelMapper.count(Boolean.TRUE);
	}

	@Override
	public List<DataingDataModel> findNameByPage(String name, Integer first, Integer size) {
		return dataModelMapper.findNameByPage(name, first, size);
	}

	@Override
	public int countByName(String name) {
		return dataModelMapper.countByName(name);
	}

	@Override
	public int update(DataingDataModel dataModel) {
		return dataModelMapper.update(dataModel);
	}

	@Override
	public int delete(Integer id) {
		return dataModelMapper.delete(id);
	}

}
