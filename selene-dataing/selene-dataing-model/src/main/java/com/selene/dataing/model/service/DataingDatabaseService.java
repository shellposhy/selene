package com.selene.dataing.model.service;

import java.util.List;

import com.selene.common.constants.util.ELibraryNodeType;
import com.selene.common.constants.util.ELibraryType;
import com.selene.dataing.model.DataingDatabase;

/**
 * The {@code DataingDatabase} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface DataingDatabaseService {
	public DataingDatabase find(Integer id);

	public List<DataingDatabase> list(String license, String word, ELibraryType type);

	public List<DataingDatabase> findByModelId(String license, Integer modelId);

	public List<DataingDatabase> findByTypeAndNodeType(String license, ELibraryType type, ELibraryNodeType nodeType);

	public List<DataingDatabase> findPageByTypeAndNodeType(String license, ELibraryType type, ELibraryNodeType nodeType,
			Integer first, Integer size);

	public int countByTypeAndNodeType(String license, ELibraryType type, ELibraryNodeType nodeType);

	public List<DataingDatabase> findPageByNameTypeAndNodeType(String license, String word, ELibraryType type,
			ELibraryNodeType nodeType, Integer first, Integer size);

	public int countByNameTypeAndNodeType(String license, String word, ELibraryType type, ELibraryNodeType nodeType);

	public List<DataingDatabase> findByNameType(String license, String word, ELibraryType type);

	public List<DataingDatabase> findEmptyDirectory(String license, ELibraryType type);

	public List<DataingDatabase> findByParentId(String license, ELibraryType type, Integer parentId);

	public String findDataNodeNameByIds(List<Integer> list);

	public List<Integer> findDataNodeByIds(List<Integer> list);

	public int insert(DataingDatabase database);

	public int update(DataingDatabase database);

	public int updateDataUpdateTime(Integer id);

	public int delete(Integer id);
}
