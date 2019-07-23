package com.selene.dataing.model.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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

	public DataingDataModelFieldMap find(@Param("id") Integer id);

	public List<DataingDataModelFieldMap> findByModelId(@Param("modelId") Integer modelId);

	public int delete(@Param("id") Integer id);

	public int deleteByModelId(@Param("modelId") Integer modelId);
}
