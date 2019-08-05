package com.selene.dataing.provider.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.selene.common.constants.CommonFields;
import com.selene.dataing.model.DataingDataField;
import com.selene.dataing.model.enums.EDataType;
import com.selene.dataing.model.jdbc.DataingTable;

/**
 * Operate on the data based on Spring JDBC
 * 
 * @see JdbcTemplate
 * @see DataSource
 * @author shaobo shih
 * @version 1.0
 */
public class DataingJdbcTemplateDao {
	private static final Logger LOG = LoggerFactory.getLogger(DataingJdbcTemplateDao.class.getName());
	private JdbcTemplate jdbcTemplate;

	/**
	 * Create new table
	 * 
	 * @param table
	 * @return
	 */
	public void create(DataingTable table) {
		LOG.info("Dataing create new table[" + table.getName() + "]");
		StringBuilder sql = new StringBuilder("create table ").append(table.getName()).append(" (");
		List<String> uniqList = new ArrayList<String>();
		for (DataingDataField dataField : table.getFieldList()) {
			sql.append(this.getFieldSql(dataField)).append(",");
			if (dataField.isUniq() && !CommonFields.ID.equals(dataField.getCode())) {
				uniqList.add(dataField.getCode());
			}
		}
		sql.append("PRIMARY KEY (").append(CommonFields.ID).append(")");
		for (String c : uniqList) {
			sql.append(",UNIQUE KEY AK_UNIQUE_KEY_").append(c).append(" (").append(c).append(")");
		}
		sql.append(")ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		jdbcTemplate.execute(sql.toString());
	}

	/**
	 * Drop table
	 * 
	 * @param tableName
	 * @return
	 */
	public void drop(String tableName) {
		LOG.info("Dataing drop  table[" + tableName + "]");
		jdbcTemplate.execute("drop table if exists " + tableName);
	}

	/**
	 * Alter the table
	 * 
	 * @param table
	 * @return
	 */
	public void alter(DataingTable table) {
		LOG.info("Dataing alter  table[" + table.getName() + "]");
		StringBuilder sql1 = new StringBuilder();
		List<DataingDataField> fieldList = null;
		// Delete the field
		fieldList = table.getDrops();
		if (fieldList != null) {
			for (int i = 0; i < fieldList.size(); i++) {
				if (i > 0) {
					sql1.append(",");
				}
				sql1.append("Drop ").append(fieldList.get(i).getCode());
			}
		}
		// Change the field
		fieldList = table.getChanges();
		if (fieldList != null) {
			for (int i = 0; i < table.getChanges().size(); i++) {
				if (sql1.length() > 0) {
					sql1.append(",");
				}
				sql1.append("CHANGE ").append(table.getChanges().get(i).getOldCode()).append(" ")
						.append(getFieldSql(table.getChanges().get(i)));
			}
		}
		// Add the new field
		fieldList = table.getAdds();
		for (int i = 0; i < fieldList.size(); i++) {
			if (sql1.length() > 0) {
				sql1.append(",");
			}
			sql1.append("ADD ").append(getFieldSql(fieldList.get(i)));
		}
		StringBuilder sql = new StringBuilder();
		for (int i = 0; i < table.getNames().size(); i++) {
			sql.append("ALTER TABLE ").append(table.getNames().get(i)).append(" ").append(sql1).append(";");

		}
		String ss = sql.toString();
		LOG.info("Dataing alter  table SQL[" + ss + "]");
		if (null != ss && !ss.isEmpty()) {
			jdbcTemplate.execute(ss);
		}
	}

	/* ========private utilities======== */
	private String getFieldSql(DataingDataField df) {
		StringBuilder sql = new StringBuilder();
		sql.append(df.getCode()).append(" ").append(df.getDataType().getMysqlDataType());
		if (EDataType.UUID.equals(df.getDataType())) {
			return sql.toString();
		}
		Integer i = df.getLeng();
		if (i != null && i > 0) {
			sql.append("(").append(i);
			i = df.getPrec();
			if (i != null && i > 0) {
				sql.append(",").append(i);
			}
			sql.append(")");
		}
		if (df.isNosg()) {
			sql.append(" unsigned");
		}
		if (df.isMand()) {
			sql.append(" NOT NULL");
		}
		if (EDataType.IntAutoIncrement.equals(df.getDataType())) {
			sql.append(" AUTO_INCREMENT");
		}
		return sql.toString();
	}

	// Property Injection
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
}
