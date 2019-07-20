package com.selene.dataing.provider.dao;

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
}
