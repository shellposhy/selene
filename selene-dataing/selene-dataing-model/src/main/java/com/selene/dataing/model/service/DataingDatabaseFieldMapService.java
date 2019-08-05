package com.selene.dataing.model.service;

import java.util.List;

import com.selene.common.constants.util.ELibraryType;
import com.selene.dataing.model.DataingDatabaseFieldMap;

/**
 * The {@code DataingDatabaseFieldMap} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface DataingDatabaseFieldMapService {
	public DataingDatabaseFieldMap find(Integer id);

	public List<DataingDatabaseFieldMap> findByDBId(Integer baseId);

	public List<DataingDatabaseFieldMap> findByDBIdAndIsDisplay(Integer baseId, boolean isDisplay);

	public int countByFieldId(Integer fieldId);

	public List<Integer> findAllBaseId(ELibraryType type);

	public int insert(DataingDatabaseFieldMap databaseFieldMap);

	public int batchInsert(List<DataingDatabaseFieldMap> list);

	public int delete(Integer id);

	public int deleteByDBId(Integer baseId);

	public int deleteByDbIdAndFieldId(Integer baseId, Integer fieldId);
}
