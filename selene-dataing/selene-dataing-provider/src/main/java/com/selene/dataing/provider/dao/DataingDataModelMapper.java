package com.selene.dataing.provider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.selene.dataing.model.DataingDataModel;

public interface DataingDataModelMapper {
	public int insert(DataingDataModel dataModel);

	public DataingDataModel find(@Param("id") Integer id);

	public List<DataingDataModel> findByType(@Param("modelType") Integer modelType, @Param("license") String license);

	public List<DataingDataModel> findByPage(@Param("license") String license, @Param("first") Integer first,
			@Param("size") Integer size);

	public int count(@Param("license") String license);

	public List<DataingDataModel> findNameByPage(@Param("license") String license, @Param("name") String name,
			@Param("first") Integer first, @Param("size") Integer size);

	public int countByName(@Param("license") String license, @Param("name") String name);

	public int update(DataingDataModel dataModel);

	public int delete(@Param("id") Integer id);
}
