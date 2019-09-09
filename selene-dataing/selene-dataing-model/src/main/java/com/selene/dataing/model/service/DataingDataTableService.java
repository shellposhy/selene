package com.selene.dataing.model.service;

import java.util.List;

import com.selene.dataing.model.DataingDataTable;

/**
 * The {@code DataingDataTable} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface DataingDataTableService {
	public List<DataingDataTable> findByBaseId(Integer dbId);

	public List<DataingDataTable> findByName(String tableName);

	public DataingDataTable find(Integer id);

	public int insert(DataingDataTable dataTable);

	public int increment(Integer id, Integer num);

	public int decrement(Integer id, Integer num);

	public int update(DataingDataTable dataTable);

	public int delete(Integer id);
}
