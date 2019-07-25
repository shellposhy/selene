package com.selene.dataing.provider.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.common.constants.CommonConstants;
import com.selene.common.constants.util.ELibraryNodeType;
import com.selene.common.constants.util.ELibraryType;
import com.selene.dataing.model.DataingDatabase;
import com.selene.dataing.model.service.DataingDatabaseService;
import com.selene.dataing.provider.dao.DataingDatabaseMapper;

import cn.com.lemon.base.Strings;

/**
 * The {@code DataingDatabaseService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see DataingDatabaseService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(DataingDatabaseService.class)
public class DataingDatabaseServiceImpl implements DataingDatabaseService {

	@Resource
	private DataingDatabaseMapper databaseMapper;

	@Override
	public DataingDatabase find(Integer id) {
		return databaseMapper.find(id);
	}

	@Override
	public List<DataingDatabase> findByModelId(String license, Integer modelId) {
		return databaseMapper.findByModelId(license, modelId);
	}

	@Override
	public List<DataingDatabase> findByTypeAndNodeType(String license, ELibraryType type, ELibraryNodeType nodeType) {
		return databaseMapper.findByTypeAndNodeType(license, type, nodeType);
	}

	@Override
	public List<DataingDatabase> findPageByTypeAndNodeType(String license, ELibraryType type, ELibraryNodeType nodeType,
			Integer first, Integer size) {
		return databaseMapper.findPageByTypeAndNodeType(license, type, nodeType, first, size);
	}

	@Override
	public int countByTypeAndNodeType(String license, ELibraryType type, ELibraryNodeType nodeType) {
		return databaseMapper.countByTypeAndNodeType(license, type, nodeType);
	}

	@Override
	public List<DataingDatabase> findPageByNameTypeAndNodeType(String license, String word, ELibraryType type,
			ELibraryNodeType nodeType, Integer first, Integer size) {
		return databaseMapper.findPageByNameTypeAndNodeType(license, word, type, nodeType, first, size);
	}

	@Override
	public int countByNameTypeAndNodeType(String license, String word, ELibraryType type, ELibraryNodeType nodeType) {
		return databaseMapper.countByNameTypeAndNodeType(license, word, type, nodeType);
	}

	@Override
	public int insert(DataingDatabase database) {
		return databaseMapper.insert(database);
	}

	@Override
	public int update(DataingDatabase database) {
		return databaseMapper.update(database);
	}

	@Override
	public List<DataingDatabase> list(String license, String word, ELibraryType type) {
		List<DataingDatabase> list = new ArrayList<DataingDatabase>();
		int size = CommonConstants.RPC_DEFAULT_SIZE;
		/**
		 * All directory node
		 */
		int directoryTotal = countByTypeAndNodeType(license, type, ELibraryNodeType.Directory);
		if (directoryTotal > 0) {
			int count = directoryTotal % size == 0 ? directoryTotal / size : directoryTotal / size + 1;
			for (/* Add by pageing */int i = 1; i <= count; i++) {
				list.addAll(findPageByTypeAndNodeType(license, type, ELibraryNodeType.Directory, (i - 1) * size, size));
			}
		}
		/** All node */
		if (Strings.isNullOrEmpty(word)) {
			int total = countByTypeAndNodeType(license, type, ELibraryNodeType.Lib);
			if (total > 0) {
				int count = total % size == 0 ? total / size : total / size + 1;
				for (/* Add by pageing */int i = 1; i <= count; i++) {
					list.addAll(findPageByTypeAndNodeType(license, type, ELibraryNodeType.Lib, (i - 1) * size, size));
				}
			}
		} else {
			int total = countByNameTypeAndNodeType(license, word, type, ELibraryNodeType.Lib);
			if (total > 0) {
				int count = total % size == 0 ? total / size : total / size + 1;
				for (/* Add by pageing */int i = 1; i <= count; i++) {
					list.addAll(findPageByNameTypeAndNodeType(license, word, type, ELibraryNodeType.Lib, (i - 1) * size,
							size));
				}
			}
		}
		return list.size() > 0 ? list : null;
	}

	@Override
	public List<DataingDatabase> findEmptyDirectory(String license, ELibraryType type) {
		return databaseMapper.findEmptyDirectory(license, type);
	}
}
