package com.selene.dataing.provider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.selene.dataing.model.DataingDataTask;

/**
 * Completes the mapping between the {@link DataingDataTask} Object and the XML
 * 
 * @see DataingDataTable
 * @author shaobo shih
 * @version 1.0
 */
public interface DataingDataTaskMapper {
	public int insert(DataingDataTask dataTask);

	public List<DataingDataTask> findByStatus(@Param("status") Integer status);

	public List<DataingDataTask> findByTypeAndStatus(@Param("type") Integer type, @Param("status") Integer status);

	public int update(DataingDataTask dataTask);

	public int delete(@Param("id") Integer id);
}
