package com.selene.dataing.provider.impl;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.dataing.model.jdbc.DataingTable;
import com.selene.dataing.model.service.DataingJdbcTemplateService;
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

}
