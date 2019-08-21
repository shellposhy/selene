package com.selene.dataing.provider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.selene.dataing.model.DataingDataTag;

/**
 * Completes the mapping between the {@link DataingDataTag} Object and the XML
 * 
 * @see DataingDataTag
 * @author shaobo shih
 * @version 1.0
 */
public interface DataingDataTagMapper {
	public DataingDataTag find(@Param("id") Integer id);

	public List<DataingDataTag> findByLicense(@Param("license") String license);

	public List<DataingDataTag> findByLicenseAndParentId(@Param("license") String license,
			@Param("parentId") Integer parentId);

	public List<DataingDataTag> findByLicenseAndType(@Param("license") String license, @Param("type") Integer type);

	public List<DataingDataTag> findByLicenseAndName(@Param("license") String license, @Param("name") String name);

	public int insert(DataingDataTag dataTag);

	public int update(DataingDataTag dataTag);

	public int delete(@Param("id") Integer id);
}
