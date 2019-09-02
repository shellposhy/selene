package com.selene.dataing.model.service;

import java.util.List;

import com.selene.dataing.model.DataingDataModelFieldMap;

/**
 * The {@code DataingDataModelFieldMap} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface DataingDataModelFieldMapService {
	public int insert(DataingDataModelFieldMap dataModelFieldMap);

	public int batchInsert(List<DataingDataModelFieldMap> list);

	public DataingDataModelFieldMap find(Integer id);

	public List<DataingDataModelFieldMap> findByModelId(Integer modelId);

	public int delete(Integer id);

	public int deleteByModelId(Integer modelId);
}
