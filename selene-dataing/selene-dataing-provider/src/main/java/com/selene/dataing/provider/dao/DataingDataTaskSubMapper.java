package com.selene.dataing.provider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.selene.dataing.model.DataingDataTaskSub;

/**
 * Completes the mapping between the {@link DataingDataTaskSub} Object and the
 * XML
 * 
 * @see DataingDataTable
 * @author shaobo shih
 * @version 1.0
 */
public interface DataingDataTaskSubMapper {
	public int insert(DataingDataTaskSub dataTaskSub);

	public List<DataingDataTaskSub> findByTaskId(@Param("taskId") Integer taskId);

	public List<DataingDataTaskSub> findByTaskIdAndStatus(@Param("taskId") Integer taskId,
			@Param("status") Integer status);

	public List<DataingDataTaskSub> findByTaskIdAndTypeAndStatus(@Param("taskId") Integer taskId,
			@Param("type") Integer type, @Param("status") Integer status);

	public int update(DataingDataTaskSub dataTaskSub);

	public int delete(@Param("id") Integer id);
}
