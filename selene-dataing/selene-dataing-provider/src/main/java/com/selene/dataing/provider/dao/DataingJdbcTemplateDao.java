package com.selene.dataing.provider.dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.selene.common.constants.FieldsConstants;
import com.selene.common.constants.util.EDataType;
import com.selene.common.constants.util.ERollbackPolicy;
import com.selene.common.result.ListResult;
import com.selene.dataing.model.DataingDataField;
import com.selene.dataing.model.DataingDataTable;
import com.selene.dataing.model.jdbc.DataingBaseData;
import com.selene.dataing.model.jdbc.DataingDbData;
import com.selene.dataing.model.jdbc.DataingTable;

import static cn.com.lemon.base.DateUtil.format;
import static cn.com.lemon.base.Strings.blob;

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
			if (dataField.isUniq() && !FieldsConstants.ID.equals(dataField.getCode())) {
				uniqList.add(dataField.getCode());
			}
		}
		sql.append("PRIMARY KEY (").append(FieldsConstants.ID).append(")");
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

	/**
	 * Insert single data
	 * 
	 * @param tableName
	 * @param doc
	 * @return primary key
	 */
	public Integer insert(final String tableName, final DataingDbData dbData) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement statement = null;
				if (dbData.hasBlob()) {
					statement = getPreparedInsert(connection, tableName, dbData);
					try {
						setPreparedInsert(statement, dbData);
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
				} else {
					String sql = getStaticInsertSql(tableName, new DataingDbData[] { dbData });
					statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				}
				return statement;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	/**
	 * Insert multiple data
	 * 
	 * @param tableName
	 * @param dataList
	 * @param policy
	 * @return {@code ListResult}
	 */
	public ListResult<Integer> insert(final String tableName, final List<DataingDbData> dataList,
			final ERollbackPolicy policy) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		ListResult<Integer> result = new ListResult<Integer>();
		List<Integer> idList = new ArrayList<Integer>(dataList.size());
		result.setData(idList);
		int total = 0;
		for (final DataingDbData data : dataList) {
			int insertCount = jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					if (/* Need to roll back */policy == ERollbackPolicy.Rollback) {
						conn.setAutoCommit(false);
					}
					PreparedStatement st = getPreparedInsert(conn, tableName, dataList.get(0));
					try {
						setPreparedInsert(st, data);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					if (!conn.getAutoCommit()) {
						conn.commit();
					}
					return st;
				}
			}, keyHolder);
			if (policy == ERollbackPolicy.Ignore) {
				total += insertCount;
				if (insertCount != 0)
					idList.add(keyHolder.getKey().intValue());
				else
					idList.add(0);
			} else {
				if (insertCount != 0)
					idList.add(keyHolder.getKey().intValue());
			}
		}
		if (policy == ERollbackPolicy.Ignore) {
			result.setTotal(total);
		} else {
			result.setTotal(idList.size());
		}
		return result;
	}

	/**
	 * Only insert multiple data,not return primary key.
	 * 
	 * @param tableName
	 * @param dataList
	 * @param errPolicy
	 * @return
	 */
	public ListResult<Integer> insertOnly(final String tableName, final List<DataingDbData> dataList,
			final ERollbackPolicy policy) {
		ListResult<Integer> result = new ListResult<Integer>();
		int total = 0;
		for (final DataingDbData data : dataList) {
			int insertCount = jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					if (/* Need to roll back */policy == ERollbackPolicy.Rollback) {
						conn.setAutoCommit(false);
					}
					PreparedStatement st = getPreparedInsert(conn, tableName, dataList.get(0));
					try {
						setPreparedInsert(st, data);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					if (!conn.getAutoCommit()) {
						conn.commit();
					}
					return st;
				}
			});
			total += insertCount;
		}
		result.setTotal(total);
		return result;
	}

	/**
	 * Update single data
	 * 
	 * @param tableName
	 * @param doc
	 * @return {@code int}
	 */
	public int update(final String tableName, final DataingDbData doc) {
		return jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement st = null;
				if (doc.hasBlob()) {
					st = getPreparedUpdate(conn, tableName, doc);
					setPreparedUpdate(st, doc);
				} else {
					String sql = getStaticUpdateSql(tableName, doc);
					st = conn.prepareStatement(sql);
				}
				return st;
			}
		});
	}

	/**
	 * Delete single date
	 * 
	 * @param tableName
	 * @param id
	 * @return {@code int}
	 */
	public int delete(final String tableName, final Integer id) {
		return jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				StringBuilder sql = new StringBuilder("delete from ").append(tableName).append(" where ")
						.append(FieldsConstants.ID).append("=").append(id);
				String delete = sql.toString();
				PreparedStatement st = null;
				st = conn.prepareStatement(delete);
				return st;
			}
		});
	}

	/**
	 * Delete date by condition
	 * 
	 * @param tableName
	 * @param where
	 * @return {@code int}
	 */
	public Integer delete(final String tableName, final String where) {
		return jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				StringBuilder sql = new StringBuilder("delete from ").append(tableName).append(" where ").append(where);
				String delete = sql.toString();
				PreparedStatement st = null;
				st = conn.prepareStatement(delete);
				return st;
			}
		});
	}

	/**
	 * Query single data by table name and primary key
	 * 
	 * @param table
	 * @param dataId
	 * @return {@code DataingBaseData}
	 */
	public DataingBaseData select(final DataingDataTable table, Integer dataId) {
		StringBuilder sql = new StringBuilder("select * from ").append(table.getName()).append(" where ")
				.append(FieldsConstants.ID).append("=").append(dataId);
		String select = sql.toString();
		final DataingBaseData[] pdList = new DataingBaseData[1];
		jdbcTemplate.query(select, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				try {
					pdList[0] = getResultData(rs, table.getId());
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		return pdList[0];
	}

	/**
	 * Query list data by table name and sql for paging
	 * 
	 * @param tableId
	 * @param sql
	 * @param first
	 * @param size
	 * @return {@code ListResult}
	 */
	public ListResult<DataingBaseData> select(final Integer tableId, String sql, Integer first, Integer size) {
		final ListResult<DataingBaseData> result = new ListResult<DataingBaseData>();
		StringBuilder sb = null;
		String select = null;
		int total = 0;
		final boolean[] useLimit = new boolean[1];
		useLimit[0] = false;
		if (first == null) {
			total = 1;
		} else {
			useLimit[0] = true;
			sb = new StringBuilder("select count(*) ").append(sql.substring(sql.indexOf("from")));
			select = sb.toString();
			total/* Total for search */ = jdbcTemplate.queryForObject(sql, Integer.class);
			result.setTotal(total);
		}
		if (total > 0) {
			sb = new StringBuilder(sql);
			if (useLimit[0]) {
				sb.append(" limit ").append(first).append(",").append(size);
			}
			select = sb.toString();
			jdbcTemplate.query(select, new RowCallbackHandler() {
				public void processRow(ResultSet rs) throws SQLException {
					DataingBaseData data = null;
					List<DataingBaseData> list = new LinkedList<DataingBaseData>();
					try {
						list.add(getResultData(rs, tableId));
						while (rs.next()) {
							data = getResultData(rs, tableId);
							list.add(data);
						}
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					result.setData(list);
					if (!useLimit[0]) {
						result.setTotal(list.size());
					}
				}
			});
		}
		return result;
	}

	/**
	 * Count for data table
	 * 
	 * @param tableName
	 * @return {@code int}
	 */
	public int count(String tableName) {
		int result = 0;
		StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM ").append(tableName);
		try {
			result = jdbcTemplate.queryForObject(sb.toString(), Integer.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/* ========private utilities======== */
	private DataingBaseData getResultData(ResultSet rs, Integer tableId)
			throws SQLException, ParseException, UnsupportedEncodingException, IOException {
		ResultSetMetaData md = rs.getMetaData();
		DataingBaseData pd = new DataingBaseData();
		pd.setTableId(tableId);
		for (int i = 1; i <= md.getColumnCount(); i++) {
			String label = md.getColumnLabel(i);
			Object value = null;
			switch (md.getColumnType(i)) {
			case Types.INTEGER:
				value = rs.getInt(i);
				break;
			case Types.BIGINT:
				value = rs.getLong(i);
				break;
			case Types.TINYINT:
				value = rs.getShort(i);
				break;
			case Types.LONGVARBINARY:
			case Types.BLOB:
				value = rs.getBlob(i) == null ? null : blob(rs.getBlob(i));
				break;
			case Types.DATE:
			case Types.TIME:
			case Types.TIMESTAMP:
				value = rs.getDate(i) == null ? null : new Date(rs.getTimestamp(i).getTime());
				break;
			case Types.CHAR:
			case Types.VARCHAR:
				value = rs.getString(i);
				break;
			case Types.DOUBLE:
				value = rs.getDouble(i);
				break;
			case Types.FLOAT:
				value = rs.getFloat(i);
				break;
			case Types.BOOLEAN:
			case Types.BIT:
				value = rs.getBoolean(i);
				break;
			}
			pd.put(label, value);
		}
		return pd;
	}

	private PreparedStatement getPreparedUpdate(Connection conn, String tableName, DataingDbData doc)
			throws SQLException {
		StringBuilder update = new StringBuilder("update ").append(tableName).append(" set ");
		StringBuilder where = new StringBuilder();
		int i = 0;
		for (Map.Entry<DataingDataField, Object> en : doc.getEntryList()) {
			DataingDataField df = en.getKey();
			if (FieldsConstants.ID.equals(df.getCode())) {
				if (where.length() > 0) {
					where.append(" and ");
				}
				where.append(df.getCode()).append("=?");
				if (FieldsConstants.ID.equals(df.getCode())) {
					continue;
				}
			}
			if (i > 0) {
				update.append(",");
			}
			update.append(df.getCode()).append("=?");
			i++;
		}
		update.append(" where ").append(where).append(";");
		return conn.prepareStatement(update.toString());
	}

	private void setPreparedUpdate(PreparedStatement st, DataingDbData doc) throws SQLException {
		int i = 1, n = 0;
		Object[] wheres = new Object[2];
		for (Map.Entry<DataingDataField, Object> en : doc.getEntryList()) {
			DataingDataField df = en.getKey();
			if (FieldsConstants.ID.equals(df.getCode())) {
				wheres[n] = en.getValue();
				n++;
				continue;
			}
			st.setObject(i, en.getValue());
			i++;
		}
		st.setObject(i, wheres[0]);
	}

	private String getStaticUpdateSql(String tableName, DataingDbData doc) {
		StringBuilder update = new StringBuilder("update ").append(tableName).append(" set ");
		StringBuilder where = new StringBuilder();
		int i = 0;
		for (Map.Entry<DataingDataField, Object> en : doc.getEntryList()) {
			DataingDataField df = en.getKey();
			if (FieldsConstants.ID.equals(df.getCode())) {
				if (where.length() > 0) {
					where.append(" and ");
				}
				where.append(df.getCode()).append("=").append(en.getValue());
				continue;
			}
			if (i > 0) {
				update.append(",");
			}
			update.append(df.getCode()).append("=");
			switchDataType(update, en);
			i++;
		}
		update.append(" where ").append(where).append(";");
		return update.toString();
	}

	private PreparedStatement getPreparedInsert(Connection conn, String tableName, DataingDbData doc)
			throws SQLException {
		StringBuilder insert = new StringBuilder("insert into ").append(tableName).append("(");
		StringBuilder values = new StringBuilder("values(");
		int i = 0;
		for (Map.Entry<DataingDataField, Object> entry : doc.getEntryList()) {
			DataingDataField df = entry.getKey();
			if (FieldsConstants.ID.equals(df.getCode())) {
				continue;
			}
			if (i > 0) {
				insert.append(",");
				values.append(",");
			}
			insert.append(df.getCode());
			values.append("?");
			i++;
		}
		insert.append(") ").append(values).append(");");
		String sql = insert.toString();
		return conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	}

	private void setPreparedInsert(PreparedStatement st, DataingDbData doc)
			throws SQLException, UnsupportedEncodingException {
		int i = 1;
		StringBuilder sb = new StringBuilder(" set ");
		Object value = null;
		String fieldCode = null;
		for (Map.Entry<DataingDataField, Object> en : doc.getEntryList()) {
			DataingDataField df = en.getKey();
			fieldCode = df.getCode();
			value = en.getValue();
			if (FieldsConstants.ID.equals(fieldCode)) {
				continue;
			} else if (value instanceof String
					&& (df.getDataType() == EDataType.Blob || df.getDataType() == EDataType.MediumBlob)) {
				sb.append(i).append("=<Blob>, ");
				st.setBlob(i, new ByteArrayInputStream(((String) value).getBytes("utf-8")));
				i++;
				continue;
			}
			sb.append(i).append("=").append(value).append(", ");
			st.setObject(i, value);
			i++;
		}
	}

	private String getStaticInsertSql(String tableName, DataingDbData[] docs) {
		StringBuilder insert = new StringBuilder("insert into ").append(tableName).append("(");
		StringBuilder values = new StringBuilder("values(");
		int i = 0;
		if (docs.length == 1) {
			for (Map.Entry<DataingDataField, Object> en : docs[0].getEntryList()) {
				DataingDataField df = en.getKey();
				if (FieldsConstants.ID.equals(df.getCode())) {
					continue;
				}
				if (i > 0) {
					insert.append(",");
					values.append(",");
				}
				insert.append(df.getCode());
				switchDataType(values, en);
				i++;
			}

		} else {
			for (Map.Entry<DataingDataField, Object> en : docs[0].getEntryList()) {
				DataingDataField df = en.getKey();
				if (FieldsConstants.ID.equals(df.getCode())) {
					continue;
				}
				if (i > 0) {
					insert.append(",");
				}
				insert.append(df.getCode());
			}
			for (int j = 0; j < docs.length; j++) {
				if (j > 0) {
					values.append("),(");
				}
				for (Map.Entry<DataingDataField, Object> en : docs[j].getEntryList()) {
					DataingDataField df = en.getKey();
					if (FieldsConstants.ID.equals(df.getCode())) {
						continue;
					}
					if (i > 0) {
						values.append(",");
					}
					switchDataType(values, en);
					i++;
				}
			}
		}

		insert.append(") ").append(values).append(");");
		return insert.toString();
	}

	private void switchDataType(StringBuilder values, Map.Entry<DataingDataField, Object> en) {
		DataingDataField df = en.getKey();
		Object value = en.getValue();
		if (value == null) {
			values.append("null");
		} else {
			switch (df.getDataType()) {
			case Char:
			case Varchar:
			case UUID:
				values.append("'").append(en.getValue()).append("'");
				break;
			case Date:
				values.append("'").append(format((Date) en.getValue(), "yyyy-MM-dd")).append("'");
				break;
			case DateTime:
				values.append("'").append(format((Date) en.getValue(), "yyyy-MM-dd HH:mm:ss")).append("'");
				break;
			case Time:
				values.append("'").append(format((Date) en.getValue(), "yyyy-MM-dd")).append("'");
				break;
			case Short:
			case Int:
			case Long:
			case Float:
			case Double:
			case Numeric:
			case Bool:
			default:
				values.append(en.getValue());
				break;
			}
		}
	}

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
