package com.selene.dataing.provider.impl;

import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.dataing.model.DataingDataModelFieldMap;
import com.selene.dataing.model.service.DataingDataModelFieldMapService;
import com.selene.dataing.provider.dao.DataingDataModelFieldMapMapper;

/**
 * The {@code DataingDataModelFieldMapService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see DataingDataModelFieldMapService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(DataingDataModelFieldMapService.class)
public class DataingDataModelFieldMapServiceImpl implements DataingDataModelFieldMapService {
	@Resource
	private DataingDataModelFieldMapMapper dataModelFieldMapMapper;

	@Override
	public int insert(DataingDataModelFieldMap dataModelFieldMap) {
		return dataModelFieldMapMapper.insert(dataModelFieldMap);
	}

	@Override
	public int batchInsert(List<DataingDataModelFieldMap> list) {
		return dataModelFieldMapMapper.batchInsert(list);
	}

	@Override
	public DataingDataModelFieldMap find(Integer id) {
		return dataModelFieldMapMapper.find(id);
	}

	@Override
	public List<DataingDataModelFieldMap> findByModelId(Integer modelId) {
		return dataModelFieldMapMapper.findByModelId(modelId);
	}

	@Override
	public int delete(DataingDataModelFieldMap dataModelFieldMap) {
		return dataModelFieldMapMapper.delete(dataModelFieldMap);
	}

	@Override
	public int deleteByModelId(Integer modelId) {
		return dataModelFieldMapMapper.deleteByModelId(modelId);
	}

}
