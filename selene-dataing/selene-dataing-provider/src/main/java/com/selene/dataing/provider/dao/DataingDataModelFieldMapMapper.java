package com.selene.dataing.provider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.selene.dataing.model.DataingDataModelFieldMap;

public interface DataingDataModelFieldMapMapper {
	public int insert(DataingDataModelFieldMap dataModelFieldMap);

	public int batchInsert(List<DataingDataModelFieldMap> list);

	public DataingDataModelFieldMap find(@Param("id") Integer id);

	public List<DataingDataModelFieldMap> findByModelId(@Param("modelId") Integer modelId);

	public int delete(Integer id);

	public int deleteByModelId(@Param("modelId") Integer modelId);
}
