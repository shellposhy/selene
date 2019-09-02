package com.selene.dataing.model.service;

import java.util.List;

import com.selene.common.result.ListResult;
import com.selene.dataing.model.DataingDataTable;
import com.selene.dataing.model.enums.ERollbackPolicy;
import com.selene.dataing.model.jdbc.DataingBaseData;
import com.selene.dataing.model.jdbc.DataingDbData;
import com.selene.dataing.model.jdbc.DataingTable;

/**
 * The {@code DataingJdbcTemplateService} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface DataingJdbcTemplateService {
	public int create(DataingTable table);

	public int drop(String tableName);

	public int alter(DataingTable table);

	public Integer insert(String tableName, DataingDbData dbData);

	public ListResult<Integer> insert(String tableName, List<DataingDbData> dataList, ERollbackPolicy policy);

	public ListResult<Integer> insertOnly(String tableName, List<DataingDbData> dataList, ERollbackPolicy policy);

	public int update(String tableName, DataingDbData doc);

	public int delete(String tableName, Integer id);

	public Integer delete(String tableName, String where);

	public DataingBaseData select(DataingDataTable table, Integer dataId);

	public ListResult<DataingBaseData> select(Integer tableId, String sql, Integer first, Integer size);

	public int count(String tableName);
}
