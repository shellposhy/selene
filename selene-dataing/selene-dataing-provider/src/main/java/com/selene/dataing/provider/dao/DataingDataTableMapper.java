package com.selene.dataing.provider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.selene.dataing.model.DataingDataTable;

/**
 * Completes the mapping between the {@link DataingDataTable} Object and the XML
 * 
 * @see DataingDataTable
 * @author shaobo shih
 * @version 1.0
 */
public interface DataingDataTableMapper {
	public List<DataingDataTable> findByBaseId(@Param("dbId") Integer dbId);

	public List<DataingDataTable> findByName(@Param("tableName") String tableName);

	public DataingDataTable find(@Param("id") Integer id);

	public int insert(DataingDataTable dataTable);

	public int update(DataingDataTable dataTable);

	public int delete(@Param("id") Integer id);

	public int increaseRowCount(@Param("num") Integer num);

	public int decreaseRowCount(@Param("num") Integer num);
}
