package com.selene.dataing.provider.impl;

import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.dataing.model.DataingDataTag;
import com.selene.dataing.model.service.DataingDataTagService;
import com.selene.dataing.provider.dao.DataingDataTagMapper;

/**
 * The {@code DataingDataTagService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see DataingDataTagService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(DataingDataTagService.class)
public class DataingDataTagServiceImpl implements DataingDataTagService {

	@Resource
	private DataingDataTagMapper dataTagMapper;

	@Override
	public DataingDataTag find(Integer id) {
		return dataTagMapper.find(id);
	}

	@Override
	public List<DataingDataTag> findByLicense(String license) {
		return dataTagMapper.findByLicense(license);
	}

	@Override
	public List<DataingDataTag> findByLicenseAndParentId(String license, Integer parentId) {
		return dataTagMapper.findByLicenseAndParentId(license, parentId);
	}

	@Override
	public List<DataingDataTag> findByLicenseAndType(String license, Integer type) {
		return dataTagMapper.findByLicenseAndType(license, type);
	}

	@Override
	public List<DataingDataTag> findByLicenseAndName(String license, String name) {
		return dataTagMapper.findByLicenseAndName(license, name);
	}

	@Override
	public int insert(DataingDataTag dataTag) {
		return dataTagMapper.insert(dataTag);
	}

	@Override
	public int update(DataingDataTag dataTag) {
		return dataTagMapper.update(dataTag);
	}

	@Override
	public int delete(Integer id) {
		return dataTagMapper.delete(id);
	}

}
