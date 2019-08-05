package com.selene.dataing.model.service;

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
}
