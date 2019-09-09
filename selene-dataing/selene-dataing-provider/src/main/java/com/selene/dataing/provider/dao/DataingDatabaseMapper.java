package com.selene.dataing.provider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.selene.common.constants.util.ELibraryNodeType;
import com.selene.common.constants.util.ELibraryType;
import com.selene.dataing.model.DataingDatabase;

/**
 * Completes the mapping between the {@link DataingDatabase} Object and the XML
 * 
 * @see DataingDataField
 * @author shaobo shih
 * @version 1.0
 */
public interface DataingDatabaseMapper {
	public DataingDatabase find(@Param("id") Integer id);

	public List<DataingDatabase> findByModelId(@Param("license") String license, @Param("modelId") Integer modelId);

	// Query by type and node type
	public List<DataingDatabase> findByTypeAndNodeType(@Param("license") String license,
			@Param("type") ELibraryType type, @Param("nodeType") ELibraryNodeType nodeType);

	public List<DataingDatabase> findPageByTypeAndNodeType(@Param("license") String license,
			@Param("type") ELibraryType type, @Param("nodeType") ELibraryNodeType nodeType,
			@Param("first") Integer first, @Param("size") Integer size);

	public int countByTypeAndNodeType(@Param("license") String license, @Param("type") ELibraryType type,
			@Param("nodeType") ELibraryNodeType nodeType);

	public List<DataingDatabase> findPageByNameTypeAndNodeType(@Param("license") String license,
			@Param("word") String word, @Param("type") ELibraryType type, @Param("nodeType") ELibraryNodeType nodeType,
			@Param("first") Integer first, @Param("size") Integer size);

	public int countByNameTypeAndNodeType(@Param("license") String license, @Param("word") String word,
			@Param("type") ELibraryType type, @Param("nodeType") ELibraryNodeType nodeType);

	public List<DataingDatabase> findByNameType(@Param("license") String license, @Param("word") String word,
			@Param("type") ELibraryType type);

	public List<DataingDatabase> findEmptyDirectory(@Param("license") String license, @Param("type") ELibraryType type);

	public List<DataingDatabase> findByParentId(@Param("license") String license, @Param("type") ELibraryType type,
			@Param("parentId") Integer parentId);

	public int insert(DataingDatabase database);

	public int update(DataingDatabase database);

	public int updateDataUpdateTime(@Param("id") Integer id);

	public int delete(@Param("id") Integer id);
}
