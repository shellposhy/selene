package com.selene.dataing.provider.dao;

import com.selene.dataing.model.DataingDatabase;

/**
 * Completes the mapping between the {@link DataingDatabase} Object and the XML
 * 
 * @see DataingDataField
 * @author shaobo shih
 * @version 1.0
 */
public interface DataingDatabaseMapper {
	public int insert(DataingDatabase database);
}
