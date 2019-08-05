package com.selene.dataing.provider.impl;

import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.common.constants.util.ELibraryType;
import com.selene.dataing.model.DataingDatabaseFieldMap;
import com.selene.dataing.model.service.DataingDatabaseFieldMapService;
import com.selene.dataing.provider.dao.DataingDatabaseFieldMapMapper;

/**
 * The {@code DataingDatabaseFieldMapService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see DataingDatabaseFieldMapService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(DataingDatabaseFieldMapService.class)
public class DataingDatabaseFieldMapServiceImpl implements DataingDatabaseFieldMapService {

	@Resource
	private DataingDatabaseFieldMapMapper databaseFieldMapMapper;

	@Override
	public DataingDatabaseFieldMap find(Integer id) {
		return databaseFieldMapMapper.find(id);
	}

	@Override
	public List<DataingDatabaseFieldMap> findByDBId(Integer baseId) {
		return databaseFieldMapMapper.findByDBId(baseId);
	}

	@Override
	public List<DataingDatabaseFieldMap> findByDBIdAndIsDisplay(Integer baseId, boolean isDisplay) {
		return databaseFieldMapMapper.findByDBIdAndIsDisplay(baseId, isDisplay);
	}

	@Override
	public int countByFieldId(Integer fieldId) {
		return databaseFieldMapMapper.countByFieldId(fieldId);
	}

	@Override
	public List<Integer> findAllBaseId(ELibraryType type) {
		return databaseFieldMapMapper.findAllBaseId(type);
	}

	@Override
	public int insert(DataingDatabaseFieldMap databaseFieldMap) {
		return databaseFieldMapMapper.insert(databaseFieldMap);
	}

	@Override
	public int batchInsert(List<DataingDatabaseFieldMap> list) {
		return databaseFieldMapMapper.batchInsert(list);
	}

	@Override
	public int delete(Integer id) {
		return databaseFieldMapMapper.delete(id);
	}

	@Override
	public int deleteByDBId(Integer baseId) {
		return databaseFieldMapMapper.deleteByDBId(baseId);
	}

	@Override
	public int deleteByDbIdAndFieldId(Integer baseId, Integer fieldId) {
		return databaseFieldMapMapper.deleteByDbIdAndFieldId(baseId, fieldId);
	}

}
