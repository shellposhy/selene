package com.selene.dataing.provider.impl;

import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.common.constants.util.ERollbackPolicy;
import com.selene.common.result.ListResult;
import com.selene.dataing.model.DataingDataTable;
import com.selene.dataing.model.jdbc.DataingBaseData;
import com.selene.dataing.model.jdbc.DataingDbData;
import com.selene.dataing.model.jdbc.DataingTable;
import com.selene.dataing.model.service.DataingJdbcTemplateService;
import com.selene.dataing.provider.dao.DataingDataTableMapper;
import com.selene.dataing.provider.dao.DataingJdbcTemplateDao;

/**
 * The {@code DataingJdbcTemplateService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see DataingJdbcTemplateService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(DataingJdbcTemplateService.class)
public class DataingJdbcTemplateServiceImpl implements DataingJdbcTemplateService {
	@Resource
	private DataingJdbcTemplateDao jdbcTemplateDao;
	@Resource
	private DataingDataTableMapper dataTableMapper;

	@Override
	public int create(DataingTable table) {
		try {
			jdbcTemplateDao.create(table);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public int drop(String tableName) {
		try {
			jdbcTemplateDao.drop(tableName);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public int alter(DataingTable table) {
		try {
			jdbcTemplateDao.alter(table);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public Integer insert(String tableName, DataingDbData dbData) {
		try {
			return jdbcTemplateDao.insert(tableName, dbData);
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public ListResult<Integer> insert(String tableName, List<DataingDbData> dataList, ERollbackPolicy policy) {
		try {
			return jdbcTemplateDao.insert(tableName, dataList, policy);
		} catch (Exception e) {
			return new ListResult<Integer>();
		}
	}

	@Override
	public ListResult<Integer> insertOnly(String tableName, List<DataingDbData> dataList, ERollbackPolicy policy) {
		try {
			return jdbcTemplateDao.insertOnly(tableName, dataList, policy);
		} catch (Exception e) {
			return new ListResult<Integer>();
		}
	}

	@Override
	public int update(String tableName, DataingDbData doc) {
		try {
			return jdbcTemplateDao.update(tableName, doc);
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public int delete(String tableName, Integer id) {
		try {
			return jdbcTemplateDao.delete(tableName, id);
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public Integer delete(String tableName, String where) {
		try {
			return jdbcTemplateDao.delete(tableName, where);
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public DataingBaseData select(DataingDataTable table, Integer dataId) {
		try {
			return jdbcTemplateDao.select(table, dataId);
		} catch (Exception e) {
			return new DataingBaseData();
		}
	}

	@Override
	public DataingBaseData select(Integer tableId, Integer dataId) {
		return select(dataTableMapper.find(tableId), dataId);
	}

	@Override
	public ListResult<DataingBaseData> select(Integer tableId, String sql, Integer first, Integer size) {
		try {
			return jdbcTemplateDao.select(tableId, sql, first, size);
		} catch (Exception e) {
			return new ListResult<DataingBaseData>();
		}
	}

	@Override
	public int count(String tableName) {
		try {
			return jdbcTemplateDao.count(tableName);
		} catch (Exception e) {
			return -1;
		}
	}

}
