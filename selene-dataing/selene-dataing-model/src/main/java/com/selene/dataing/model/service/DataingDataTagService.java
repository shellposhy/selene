package com.selene.dataing.model.service;

import java.util.List;

import com.selene.dataing.model.DataingDataTag;

/**
 * The {@code DataingDataTag} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface DataingDataTagService {
	public DataingDataTag find(Integer id);

	public List<DataingDataTag> findByLicense(String license);

	public List<DataingDataTag> findByLicenseAndParentId(String license, Integer parentId);

	public List<DataingDataTag> findByLicenseAndType(String license, Integer type);

	public List<DataingDataTag> findByLicenseAndName(String license, String name);

	public int insert(DataingDataTag dataTag);

	public int update(DataingDataTag dataTag);

	public int delete(Integer id);
}
