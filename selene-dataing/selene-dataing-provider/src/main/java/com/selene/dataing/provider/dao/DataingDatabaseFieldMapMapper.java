package com.selene.dataing.provider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.selene.common.constants.util.ELibraryType;
import com.selene.dataing.model.DataingDatabaseFieldMap;

/**
 * Completes the mapping between the {@link DataingDatabaseFieldMap} Object and
 * the XML
 * 
 * @see DataingDatabaseFieldMap
 * @author shaobo shih
 * @version 1.0
 */
public interface DataingDatabaseFieldMapMapper {
	public DataingDatabaseFieldMap find(@Param("id") Integer id);

	public List<DataingDatabaseFieldMap> findByDBId(@Param("baseId") Integer baseId);

	public List<DataingDatabaseFieldMap> findByDBIdAndIsDisplay(@Param("baseId") Integer baseId,
			@Param("isDisplay") boolean isDisplay);

	public int countByFieldId(@Param("fieldId") Integer fieldId);

	public List<Integer> findAllBaseId(@Param("type") ELibraryType type);

	public int insert(DataingDatabaseFieldMap databaseFieldMap);

	public int batchInsert(List<DataingDatabaseFieldMap> list);

	public int delete(@Param("id") Integer id);

	public int deleteByDBId(@Param("baseId") Integer baseId);

	public int deleteByDbIdAndFieldId(@Param("baseId") Integer baseId, @Param("fieldId") Integer fieldId);
}
