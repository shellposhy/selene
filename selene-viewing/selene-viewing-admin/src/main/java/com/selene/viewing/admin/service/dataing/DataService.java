package com.selene.viewing.admin.service.dataing;

import static cn.com.lemon.base.Strings.isNullOrEmpty;
import static cn.com.lemon.base.Strings.split;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.lucene.document.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nanshan.papaya.rpc.client.Client;
import com.selene.common.config.service.ServiceConfiger;
import com.selene.common.constants.CommonConstants;
import com.selene.common.constants.FieldsConstants;
import com.selene.common.constants.ServiceConstants;
import com.selene.common.constants.util.EAccessType;
import com.selene.common.constants.util.EDataFieldType;
import com.selene.common.constants.util.EDataStatus;
import com.selene.common.constants.util.ELibraryNodeType;
import com.selene.common.constants.util.ELibraryType;
import com.selene.common.result.ListResult;
import com.selene.common.tree.support.LibraryTreeNode;
import com.selene.common.tree.DefaultTreeNode;
import com.selene.common.tree.DefaultTreeNode.PropertySetter;
import com.selene.common.util.DataUtil;
import com.selene.common.util.RedisClient;
import com.selene.dataing.model.DataingDataField;
import com.selene.dataing.model.DataingDataModel;
import com.selene.dataing.model.DataingDataModelFieldMap;
import com.selene.dataing.model.DataingDataTable;
import com.selene.dataing.model.DataingDataTag;
import com.selene.dataing.model.DataingDatabase;
import com.selene.dataing.model.DataingDatabaseFieldMap;
import com.selene.dataing.model.jdbc.DataingBaseData;
import com.selene.dataing.model.jdbc.DataingDbData;
import com.selene.dataing.model.jdbc.DataingTable;
import com.selene.dataing.model.service.DataingDataFieldService;
import com.selene.dataing.model.service.DataingDataModelFieldMapService;
import com.selene.dataing.model.service.DataingDataModelService;
import com.selene.dataing.model.service.DataingDataTableService;
import com.selene.dataing.model.service.DataingDataTagService;
import com.selene.dataing.model.service.DataingDataTaskService;
import com.selene.dataing.model.service.DataingDataTaskSubService;
import com.selene.dataing.model.service.DataingDatabaseFieldMapService;
import com.selene.dataing.model.service.DataingDatabaseService;
import com.selene.dataing.model.service.DataingJdbcTemplateService;
import com.selene.dataing.model.util.DataingUtil;
import com.selene.viewing.admin.service.searching.SearchingService;

import cn.com.lemon.base.Strings;

@Service
public class DataService {
	private final static Logger LOG = LoggerFactory.getLogger(DataService.class.getName());
	private ServiceConfiger dataingConfiger;
	private Map<String, Object> services = new HashMap<String, Object>();
	private StringBuilder sb = new StringBuilder(100);
	@Resource
	private Client client;
	@Resource
	private RedisClient redisClient;
	@Resource
	private SearchingService searchingService;

	@PostConstruct
	@SuppressWarnings("static-access")
	public void init() {
		LOG.info("[selene-viewing-admin] init " + ServiceConfiger.class.getName() + " service!");
		// Initialization dataing service registry address
		dataingConfiger = new ServiceConfiger(
				DataService.class.getResource("/").getPath() + "selene.sevice.properties");
		String dataingService = dataingConfiger.value(ServiceConstants.DATAING_KEY);
		LOG.info("dataing service address=" + dataingService);
		// Initialization dataing service
		services.put(/* DataModel */DataingDataModelService.class.getName(),
				client.create(DataingDataModelService.class, dataingService));
		services.put(/* DataModelFiled */DataingDataModelFieldMapService.class.getName(),
				client.create(DataingDataModelFieldMapService.class, dataingService));
		services.put(/* DataField */DataingDataFieldService.class.getName(),
				client.create(DataingDataFieldService.class, dataingService));
		services.put(/* DataTag */DataingDataTagService.class.getName(),
				client.create(DataingDataTagService.class, dataingService));
		services.put(/* DataTask */DataingDataTaskService.class.getName(),
				client.create(DataingDataTaskService.class, dataingService));
		services.put(/* DataTaskSub */DataingDataTaskSubService.class.getName(),
				client.create(DataingDataTaskSubService.class, dataingService));
		services.put(/* Database */DataingDatabaseService.class.getName(),
				client.create(DataingDatabaseService.class, dataingService));
		services.put(/* DatabaseFieldMap */DataingDatabaseFieldMapService.class.getName(),
				client.create(DataingDatabaseFieldMapService.class, dataingService));
		services.put(/* DataTable */DataingDataTableService.class.getName(),
				client.create(DataingDataTableService.class, dataingService));
		services.put(/* JdbcTemplate */DataingJdbcTemplateService.class.getName(),
				client.create(DataingJdbcTemplateService.class, dataingService));
	}

	/**
	 * Query {@code DataingBaseData} by {@code Integer} table number and
	 * {@code Integer} data number
	 * 
	 * @param tableId
	 * @param dataId
	 * @return {@code DataingBaseData}
	 */
	public DataingBaseData select(Integer tableId, Integer dataId) {
		// Initialize the required services
		DataingJdbcTemplateService templateService = (DataingJdbcTemplateService) services
				.get(DataingJdbcTemplateService.class.getName());
		// Business process
		return templateService.select(tableId, dataId);
	}

	/**
	 * Query {@code DataingDataTable} by {@code Integer} table number
	 * 
	 * @param id
	 * @return {@code DataingDataTable}
	 */
	public DataingDataTable findTable(Integer id) {
		// Initialize the required services
		DataingDataTableService dataTableService = (DataingDataTableService) services
				.get(DataingDataTableService.class.getName());
		// Business process
		return dataTableService.find(id);
	}

	/**
	 * Save data
	 * 
	 * @param data
	 * @return int
	 */
	public int saveData(DataingBaseData data) {
		// Initialize the required services
		DataingDatabaseService databaseService = (DataingDatabaseService) services
				.get(DataingDatabaseService.class.getName());
		DataingJdbcTemplateService jdbcTemplateService = (DataingJdbcTemplateService) services
				.get(DataingJdbcTemplateService.class.getName());
		DataingDataTableService dataTableService = (DataingDataTableService) services
				.get(DataingDataTableService.class.getName());
		DataingDataFieldService dataFieldService = (DataingDataFieldService) services
				.get(DataingDataFieldService.class.getName());
		// Business process
		if (data.getBaseId() == null) {
			return 0;
		}
		if (/* New date */data.getId() == null) {
			DataingDbData dbData = getDbData(data, dataFieldService.findFieldsByBaseId(data.getBaseId()));
			Integer dataId = jdbcTemplateService.insert(data.getTableName(), dbData);
			if (dataId > 0) {
				data.setId(dataId);
				databaseService./* Update data time */updateDataUpdateTime(data.getBaseId());
				dataTableService.increment(data.getTableId(), 1);
				// Insert search index
				Document doc = DataingUtil.doc(data, dataFieldService.findFieldsByBaseId(data.getBaseId()));
				if (doc != null) {
					searchingService.index(doc, data.getBaseId());
				}
				return 1;
			}
		} else/* Update data */ {
			data.put(FieldsConstants.ID, data.getId());
			List<DataingDataField> updateFields = new ArrayList<DataingDataField>();
			for (DataingDataField dataField : dataFieldService.findFieldsByBaseId(data.getBaseId())) {
				if (data.getLowerFieldSet().contains(dataField.getCode().toLowerCase())) {
					updateFields.add(dataField);
				}
			}
			int result = jdbcTemplateService.update(data.getTableName(), getDbData(data, updateFields));
			if (result > 0) {
				databaseService./* Update data time */updateDataUpdateTime(data.getBaseId());
				// Insert search index
				Document doc = DataingUtil.doc(data, dataFieldService.findFieldsByBaseId(data.getBaseId()));
				if (doc != null) {
					searchingService.update(doc, data.getBaseId(), (String) data.get(FieldsConstants.UUID));
				}
				return 1;
			}
		}
		return 0;
	}

	/**
	 * Data transfer to {@code JdbcTemplate} use
	 * 
	 * @param data
	 * @param fieldList
	 * @return {@code DataingDbData}
	 */
	public DataingDbData getDbData(DataingBaseData data, List<DataingDataField> fieldList) {
		// Business process
		DataingDbData result = new DataingDbData();
		for (DataingDataField dataField : fieldList) {
			Object value = data.get(dataField.getCode());
			if (value == null) {
				result.add(dataField, DataUtil.defaultValue(dataField.getDataType(), dataField.isMand()));
			} else {
				result.add(dataField, value);
			}
		}
		result.setDescription(data.getDescription());
		return result;
	}

	/**
	 * Get {@code DataingDataField} map by {@code Integer} database id
	 * 
	 * @param baseId
	 * @return {@code Map}
	 */
	public Map<String, DataingDataField> findFieldMapByBaseId(Integer baseId) {
		// Initialize the required services
		DataingDataFieldService dataFieldService = (DataingDataFieldService) services
				.get(DataingDataFieldService.class.getName());
		// Business process
		List<DataingDataField> fieldList = dataFieldService.findFieldsByBaseId(baseId);
		Map<String, DataingDataField> result = new LinkedHashMap<String, DataingDataField>();
		for (DataingDataField dataField : fieldList) {
			result.put(dataField.getCode(), dataField);
		}
		return result;
	}

	/**
	 * Get {@code DataingDataTable} by {@code Integer} database id
	 * 
	 * @param baseId
	 * @return {@code DataingDataTable}
	 */
	public DataingDataTable findDataTableByBaseId(Integer baseId) {
		// Initialize the required services
		DataingDatabaseService databaseService = (DataingDatabaseService) services
				.get(DataingDatabaseService.class.getName());
		DataingJdbcTemplateService jdbcTemplateService = (DataingJdbcTemplateService) services
				.get(DataingJdbcTemplateService.class.getName());
		DataingDataTableService dataTableService = (DataingDataTableService) services
				.get(DataingDataTableService.class.getName());
		DataingDataFieldService dataFieldService = (DataingDataFieldService) services
				.get(DataingDataFieldService.class.getName());
		// Business process
		DataingDatabase database = /**/ databaseService.find(baseId);
		String tableName = tableName(database, false);
		Integer total = jdbcTemplateService.count(tableName);
		if (/* Single table maximum data volume for MySQL */total >= CommonConstants.MYSQL_TABLE_MAX_SIZE) {
			database.setFieldList(dataFieldService.findFieldsByBaseId(baseId));
			return /* Create new data table */saveLibraryTable(database);
		} else {
			List<DataingDataTable> tableList = dataTableService.findByName(tableName);
			return tableList != null && tableList.size() > 0 ? tableList.get(0) : null;
		}
	}

	/**
	 * Initialize custom data labels when adding a new merchant.
	 * 
	 * @param license
	 * @return {@code int} if {@code int }>0 true else false
	 */
	public int installTag(String license) {
		// Initialize the required services
		DataingDataTagService /* data tag service */ tagService = (DataingDataTagService) services
				.get(DataingDataTagService.class.getName());
		// Business process
		DataingDataTag dataTag = new DataingDataTag();
		dataTag./* New merchants license */setLicense(license);
		dataTag.setName("自定义数据标签");
		dataTag.setType(1);
		dataTag.setCode("custom");
		dataTag.setPathCode("/custom/");
		dataTag.setParentId(0);
		dataTag.setOrderId(1);
		dataTag.setStatus(EDataStatus.Normal);
		dataTag.setCreatorId(1);
		dataTag.setCreateTime(new Date());
		dataTag.setUpdaterId(1);
		dataTag.setUpdateTime(new Date());
		return tagService.insert(dataTag);
	}

	/**
	 * Delete data tag
	 * 
	 * @param dataTag
	 *            {@code DataingDataTag}
	 * @return {@code int} if {@code int }>0 true else false
	 */
	public int deleteTag(Integer id) {
		// Initialize the required services
		DataingDataTagService /* data tag service */ tagService = (DataingDataTagService) services
				.get(DataingDataTagService.class.getName());
		// Business process
		return tagService.delete(id);
	}

	/**
	 * Save data tag
	 * 
	 * @param dataTag
	 *            {@code DataingDataTag}
	 * @return {@code int} if {@code int }>0 true else false
	 */
	public int saveTag(DataingDataTag dataTag) {
		// Initialize the required services
		DataingDataTagService /* data tag service */ tagService = (DataingDataTagService) services
				.get(DataingDataTagService.class.getName());
		// Business process
		if (dataTag.getId() == null) {
			return tagService.insert(dataTag);
		} else {
			return tagService.update(dataTag);
		}
	}

	/**
	 * Query data tag by {@code Integer} key
	 * 
	 * @param id
	 * @return {@link List}
	 */
	public DataingDataTag findTagById(Integer id) {
		// Initialize the required services
		DataingDataTagService /* data tag service */ tagService = (DataingDataTagService) services
				.get(DataingDataTagService.class.getName());
		// Business process
		return tagService.find(id);
	}

	/**
	 * Query all custom data tag by {@code String} license and {@code Integer }
	 * type
	 * 
	 * @param type
	 * @param license
	 * @return {@link List}
	 */
	public List<DataingDataTag> findTagByType(String license, Integer type) {
		// Initialize the required services
		DataingDataTagService /* data tag service */ tagService = (DataingDataTagService) services
				.get(DataingDataTagService.class.getName());
		// Business process
		return tagService.findByLicenseAndType(license, type);
	}

	/**
	 * Query all data tag by {@code String} license and {@code Integer } parent
	 * 
	 * @param parentId
	 * @param license
	 * @return {@link List}
	 */
	public List<DataingDataTag> findSubTag(String license, Integer parentId) {
		// Initialize the required services
		DataingDataTagService /* data tag service */ tagService = (DataingDataTagService) services
				.get(DataingDataTagService.class.getName());
		// Business process
		return tagService.findByLicenseAndParentId(license, parentId);
	}

	/**
	 * Query all data tag by {@code String} license and {@code String } word
	 * 
	 * @param word
	 * @param license
	 * @return {@link List}
	 */
	public List<DataingDataTag> findTag(String license, String word) {
		// Initialize the required services
		DataingDataTagService /* data tag service */ tagService = (DataingDataTagService) services
				.get(DataingDataTagService.class.getName());
		// Business process
		if (isNullOrEmpty(word)) {
			return tagService.findByLicense(license);
		} else {
			return tagService.findByLicenseAndName(license, word);
		}
	}

	/**
	 * Delete database library
	 * 
	 * @param id
	 * @return {@code int} if {@code int }>0 true else false
	 */
	public int deleteLibrary(Integer id) {
		// Initialize the required services
		DataingDatabaseService databaseService = (DataingDatabaseService) services
				.get(DataingDatabaseService.class.getName());
		DataingDataTableService dataTableService = (DataingDataTableService) services
				.get(DataingDataTableService.class.getName());
		DataingDatabaseFieldMapService databaseFieldMapService = (DataingDatabaseFieldMapService) services
				.get(DataingDatabaseFieldMapService.class.getName());
		DataingJdbcTemplateService jdbcTemplateService = (DataingJdbcTemplateService) services
				.get(DataingJdbcTemplateService.class.getName());
		// Business process
		List<DataingDataTable>/* Delete data and data table */ tableList = dataTableService.findByBaseId(id);
		if (tableList != null && tableList.size() > 0) {
			for (DataingDataTable dataTable : tableList) {
				if (jdbcTemplateService.drop(dataTable.getName()) > 0) {
					if (!(dataTableService.delete(dataTable.getId()) > 0)) {
						return 0;
					}
				}
			}
		}
		if (/* Delete database fields */databaseFieldMapService.deleteByDBId(id) > 0) {
			return /* Delete database */databaseService.delete(id);
		}
		return 0;
	}

	/**
	 * Check before deleting the database.
	 * 
	 * @param id
	 * @return
	 */
	public boolean checkPreDeleteLibrary(Integer id) {
		// Initialize the required services
		DataingDataTableService dataTableService = (DataingDataTableService) services
				.get(DataingDataTableService.class.getName());
		// Business process
		List<DataingDataTable>/* Data table */ tableList = dataTableService.findByBaseId(id);
		if (tableList != null && tableList.size() > 0) {
			for (DataingDataTable dataTable : tableList) {
				if (dataTable.getRowCount() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Save database library
	 * 
	 * @param database
	 *            {@code DataingDatabase}
	 * @return {@code int} if {@code int }>0 true else false
	 */
	public int saveLibrary(DataingDatabase database) {
		// Initialize the required services
		DataingDatabaseService databaseService = (DataingDatabaseService) services
				.get(DataingDatabaseService.class.getName());
		DataingDataFieldService dataFieldService = (DataingDataFieldService) services
				.get(DataingDataFieldService.class.getName());
		DataingDataTableService dataTableService = (DataingDataTableService) services
				.get(DataingDataTableService.class.getName());
		DataingDatabaseFieldMapService databaseFieldMapService = (DataingDatabaseFieldMapService) services
				.get(DataingDatabaseFieldMapService.class.getName());
		DataingJdbcTemplateService jdbcTemplateService = (DataingJdbcTemplateService) services
				.get(DataingJdbcTemplateService.class.getName());
		// Business process
		DataingDatabase /* Is exist parent and set path code */ parent = databaseService.find(database.getParentId());
		if (parent != null) {
			database.setPathCode(parent.getPathCode() + database.getCode() + "/");
		} else {
			database.setPathCode("/" + database.getCode() + "/");
		}
		List<DataingDataField> /* Database all data fields */ allFields = new ArrayList<DataingDataField>();
		allFields.addAll(/* Must need fields */dataFieldService.findByType(EDataFieldType.Required.ordinal()));
		allFields.addAll(/* Model need fields */
				dataFieldService.findFieldsByModelId(database.getModelId()));
		database.setFieldList(allFields);
		if (/* New database */database.getId() == null) {
			int newBaseId = databaseService.insert(database);
			if (/* Create new database */newBaseId > 0) {
				database.setId(newBaseId);
				if (/* Save database field */saveLibraryFields(database) > 0) {
					DataingDataTable dataTable = saveLibraryTable(database);
					if (dataTable != null && dataTable.getId() != null) {
						database.setTables(database.getTables() + 1);
						database.setUpdateTime(new Date());
						return databaseService.update(database);
					}
				}
			}
		} else/* Update database */ {
			DataingDatabase /* Pre-modification database */ old = databaseService.find(database.getId());
			List<DataingDataField> /* Pre-modification database fields */ oldFieldList = dataFieldService
					.findFieldsByBaseId(database.getId());
			database.setTables(old.getTables());
			database.setStatus(old.getStatus());
			if (/* Update process */databaseService.update(database) > 0) {
				List<DataingDataTable> oldTableList = dataTableService.findByBaseId(database.getId());
				DataingTable newTable = new DataingTable();
				newTable./* New field */setFieldList(database.getFieldList());
				if (oldFieldList.size() > 0) {
					List<String> oldTableNameList = new ArrayList<String>();
					for (DataingDataTable dataTable : oldTableList) {
						oldTableNameList.add(dataTable.getName());
					}
					newTable./* Alter old name */setNames(oldTableNameList);
				}
				newTable./* Add field */setAdds(dataFieldService.compare(database.getFieldList(), oldFieldList));
				newTable./* Drop field */setDrops(dataFieldService.compare(oldFieldList, database.getFieldList()));
				if (/* Delete old fields */databaseFieldMapService.deleteByDBId(database.getId()) > 0) {
					if (/* Save database field */saveLibraryFields(database) > 0) {
						return /* Alter all database data registry */jdbcTemplateService.alter(newTable);
					}
				}
			}
		}
		return 0;
	}

	/**
	 * Save database library data field {@code DataingDataField}
	 * 
	 * @param database
	 *            {@code DataingDatabase}
	 * @return {@code int} if {@code int }>0 true else false
	 */
	public int saveLibraryFields(DataingDatabase database) {
		// Initialize the required services
		DataingDatabaseFieldMapService databaseFieldMapService = (DataingDatabaseFieldMapService) services
				.get(DataingDatabaseFieldMapService.class.getName());
		// Business process
		if (database != null && database.getFieldList() != null && database.getFieldList().size() > 0) {
			String[] /* Fields for display */ newShowFields = Strings.isNullOrEmpty(database.getDataFieldsStr()) ? null
					: split(CommonConstants.COMMA_SEPARATOR, database.getDataFieldsStr());
			List<DataingDatabaseFieldMap> databaseFieldList = new ArrayList<DataingDatabaseFieldMap>();
			for (DataingDataField dataField : database.getFieldList()) {
				DataingDatabaseFieldMap databaseFieldMap = new DataingDatabaseFieldMap();
				databaseFieldMap.setBaseId(database.getId());
				databaseFieldMap.setFieldId(dataField.getId());
				databaseFieldMap.setType(database.getType());
				databaseFieldMap.setDisplay(false);
				if (/* Contain display field */newShowFields != null && newShowFields.length > 0) {
					if (Arrays.binarySearch(newShowFields, String.valueOf(dataField.getId())) >= 0) {
						databaseFieldMap.setDisplay(true);
					}
				}
				databaseFieldList.add(databaseFieldMap);
			}
			return databaseFieldMapService.batchInsert(databaseFieldList);
		}
		return 0;
	}

	/**
	 * Save database library data table and create data table
	 * {@code DataingDataTable}
	 * 
	 * @param database
	 *            {@code DataingDatabase}
	 * @return {@code int} if {@code int }>0 true else false
	 */
	public DataingDataTable saveLibraryTable(DataingDatabase database) {
		// Initialize the required services
		DataingJdbcTemplateService jdbcTemplateService = (DataingJdbcTemplateService) services
				.get(DataingJdbcTemplateService.class.getName());
		DataingDataTableService dataTableService = (DataingDataTableService) services
				.get(DataingDataTableService.class.getName());
		// Business process
		String tableName = tableName(database, true);
		DataingTable table = new DataingTable(tableName, database.getFieldList());
		if (/* Create database for real data repository */jdbcTemplateService.create(table) > 0) {
			DataingDataTable dataTable = new DataingDataTable(database.getId(), tableName, 0);
			int newTableId = dataTableService.insert(dataTable);
			if (/* Create data table for current database */newTableId > 0) {
				dataTable/* Set new table id */.setId(newTableId);
				return dataTable;
			}
		}
		return null;
	}

	/**
	 * Gets the database name of the real data repository for the
	 * {@code DataingDatabase}.
	 * <P>
	 * The name pattern for data_i_0
	 * 
	 * @param database
	 * @param isFirstCreate
	 * @return {@link String}
	 */
	public String tableName(DataingDatabase database, boolean isFirstCreate) {
		String result = "data_" + database.getId() + "_";
		int tables = database.getTables();
		if (!isFirstCreate) {
			if (tables > 0) {
				tables--;
			} else {
				tables = 0;
			}
		}
		return result + tables;
	}

	/**
	 * Delete database directory
	 * 
	 * @param id
	 * @return {@code int} if {@code int }>0 true else false
	 */
	public int deleteDirectory(Integer id) {
		// Initialize the required services
		DataingDatabaseService databaseService = (DataingDatabaseService) services
				.get(DataingDatabaseService.class.getName());
		// Business process
		return databaseService.delete(id);
	}

	/**
	 * Save database directory
	 * 
	 * @param database
	 *            {@code DataingDatabase}
	 * @return {@code int} if {@code int }>0 true else false
	 */
	public int saveDirectory(DataingDatabase database) {
		// Initialize the required services
		DataingDatabaseService databaseService = (DataingDatabaseService) services
				.get(DataingDatabaseService.class.getName());
		// Business process
		DataingDatabase parent = databaseService.find(database.getParentId());
		if (parent != null) {
			database.setPathCode(parent.getPathCode() + database.getCode() + "/");
		} else {
			database.setPathCode("/" + database.getCode() + "/");
		}
		if (/* New directory */database.getId() == null) {
			return databaseService.insert(database);
		} else {
			DataingDatabase old = databaseService.find(database.getId());
			database.setTables(old.getTables());
			database.setStatus(old.getStatus());
			return databaseService.update(database);
		}
	}

	/**
	 * Query the database by parent id.
	 * 
	 * @param license
	 * @param type
	 * @param parentId
	 * @return {@link List}
	 */
	public List<DataingDatabase> findBaseByParentId(String license, ELibraryType type, Integer parentId) {
		// Initialize the required services
		DataingDatabaseService databaseService = (DataingDatabaseService) services
				.get(DataingDatabaseService.class.getName());
		// Business process
		return databaseService.findByParentId(license, type, parentId);
	}

	/**
	 * Query the database by id.
	 * 
	 * @param id
	 * @return {@link DataingDatabase}
	 */
	public DataingDatabase findDatabase(Integer id) {
		// Initialize the required services
		DataingDatabaseService databaseService = (DataingDatabaseService) services
				.get(DataingDatabaseService.class.getName());
		// Business process
		return databaseService.find(id);
	}

	/**
	 * Get database name
	 * 
	 * @param list
	 * @return {@code String}
	 */
	public String findDataBaseName(List<Integer> list) {
		// Initialize the required services
		DataingDatabaseService databaseService = (DataingDatabaseService) services
				.get(DataingDatabaseService.class.getName());
		// Business process
		return databaseService.findDataNodeNameByIds(list);
	}

	/**
	 * Get database node
	 * 
	 * @param list
	 * @return {@code List}
	 */
	public List<Integer> findDataBaseNodes(List<Integer> list) {
		// Initialize the required services
		DataingDatabaseService databaseService = (DataingDatabaseService) services
				.get(DataingDatabaseService.class.getName());
		// Business process
		return databaseService.findDataNodeByIds(list);
	}

	/**
	 * Query the database list by not child.
	 * 
	 * @param license
	 * @param type
	 * @return {@link List}
	 */
	public List<DataingDatabase> emptyTreeList(String license, ELibraryType type) {
		// Initialize the required services
		DataingDatabaseService databaseService = (DataingDatabaseService) services
				.get(DataingDatabaseService.class.getName());
		// Business process
		List<DataingDatabase> list = databaseService.findEmptyDirectory(license, type);
		return list;
	}

	/**
	 * Query the database list by word
	 * 
	 * @param license
	 * @param word
	 * @param type
	 * @return
	 */
	public List<DataingDatabase> databaseList(String license, String word, ELibraryType type) {
		// Initialize the required services
		DataingDatabaseService databaseService = (DataingDatabaseService) services
				.get(DataingDatabaseService.class.getName());
		// Business process
		return databaseService.findByNameType(license, Strings.nullToEmpty(word), type);
	}

	/**
	 * Get the database {@code DefaultTreeNode} tree.
	 * 
	 * @param license
	 * @param type
	 * @return {@link DefaultTreeNode}
	 */
	public DefaultTreeNode tree(String license, ELibraryType type) {
		// Initialize the required services
		DataingDatabaseService databaseService = (DataingDatabaseService) services
				.get(DataingDatabaseService.class.getName());
		// Business process
		List<DataingDatabase> list = databaseService.list(license, Strings.nullToEmpty(null), type);
		return DefaultTreeNode.parseTree(list);
	}

	/**
	 * Create the database tree.
	 * 
	 * @param license
	 * @param word
	 * @param type
	 * @return {@link LibraryTreeNode}
	 */
	public LibraryTreeNode tree(String license, String word, ELibraryType type) {
		// Initialize the required services
		DataingDatabaseService databaseService = (DataingDatabaseService) services
				.get(DataingDatabaseService.class.getName());
		// Business process
		List<DataingDatabase> list = databaseService.list(license, Strings.nullToEmpty(word), type);
		return LibraryTreeNode.parseTree(LibraryTreeNode.class, list,
				new PropertySetter<LibraryTreeNode, DataingDatabase>() {
					@Override
					public void setProperty(LibraryTreeNode node, DataingDatabase entity) {
						if (null != entity) {
							if (ELibraryNodeType.Lib.equals(entity.getNodeType())) {
								node.setDir(false);
								node.checked = true;
							} else {
								node.setDir(true);
							}
						}
					}
				});
	}

	/**
	 * Save data model
	 * 
	 * @param dataModel
	 *            {@code DataingDataModel}
	 * @return {@code int} if {@code int }>0 true else false
	 */
	public int saveDataModel(DataingDataModel dataModel) {
		// Initialize the required services
		DataingDataFieldService /* DataField */ dataFieldService = (DataingDataFieldService) services
				.get(DataingDataFieldService.class.getName());
		DataingDataModelService /* DataModel */ dataModelService = (DataingDataModelService) services
				.get(DataingDataModelService.class.getName());
		DataingDataModelFieldMapService/* DataModelFiled */ dataModelFieldMapService = (DataingDataModelFieldMapService) services
				.get(DataingDataModelFieldMapService.class.getName());
		// Business process
		String[] /* New model fields */ newFieldIdArray = split(CommonConstants.COMMA_SEPARATOR,
				dataModel.getModelFieldIds());
		StringBuffer /* New field name */ newFieldNames = new StringBuffer(500);
		if (newFieldIdArray != null && newFieldIdArray.length > 0) {
			for (String fieldId : newFieldIdArray) {
				newFieldNames.append(dataFieldService.find(Integer.valueOf(fieldId)).getName())
						.append(CommonConstants.COMMA_SEPARATOR);
			}
			newFieldNames/* Delete the last char */.deleteCharAt(newFieldNames.length() - 1);
		}
		dataModel.setFieldsName(newFieldNames.toString());
		if (/* New data model */dataModel.getId() == null) {
			int result = dataModelService.insert(dataModel);
			if (/* Save data model success and save field map */result > 0) {
				if (newFieldIdArray != null && newFieldIdArray.length > 0) {
					List<DataingDataModelFieldMap> newDieldMapList = new ArrayList<DataingDataModelFieldMap>();
					for (String fieldId : newFieldIdArray) {
						DataingDataModelFieldMap modelFieldMap = new DataingDataModelFieldMap(result,
								Integer.valueOf(fieldId));
						newDieldMapList.add(modelFieldMap);
					}
					return dataModelFieldMapService.batchInsert(newDieldMapList);
				}
			}
		} /* Update data model */else {
			List<DataingDataModelFieldMap> /* Delete model old field list */ oldModelFieldList = dataModelFieldMapService
					.findByModelId(dataModel.getId());
			if (oldModelFieldList != null && oldModelFieldList.size() > 0) {
				if (!(/* Delete old field */dataModelFieldMapService.deleteByModelId(dataModel.getId()) > 0)) {
					return 0;
				}
			}
			if (/* Update data model */dataModelService.update(dataModel) > 0) {
				if (newFieldIdArray != null && newFieldIdArray.length > 0) {
					List<DataingDataModelFieldMap> newDieldMapList = new ArrayList<DataingDataModelFieldMap>();
					for (String fieldId : newFieldIdArray) {
						DataingDataModelFieldMap modelFieldMap = new DataingDataModelFieldMap(dataModel.getId(),
								Integer.valueOf(fieldId));
						newDieldMapList.add(modelFieldMap);
					}
					return dataModelFieldMapService.batchInsert(newDieldMapList);
				}
			}
		}
		return 0;
	}

	/**
	 * Delete the model by id
	 * 
	 * @param id
	 * @return
	 */
	public int deleteModel(Integer id) {
		// Initialize the required services
		DataingDataModelService /* DataModel */ dataModelService = (DataingDataModelService) services
				.get(DataingDataModelService.class.getName());
		DataingDataModelFieldMapService/* DataModelFiled */ dataModelFieldMapService = (DataingDataModelFieldMapService) services
				.get(DataingDataModelFieldMapService.class.getName());
		// Business process
		List<DataingDataModelFieldMap> /* Delete model old field list */ oldModelFieldList = dataModelFieldMapService
				.findByModelId(id);
		if (oldModelFieldList != null && oldModelFieldList.size() > 0) {
			if (!(/* Delete old field */dataModelFieldMapService.deleteByModelId(id) > 0)) {
				return 0;
			}
		}
		return dataModelService.delete(id);
	}

	/**
	 * Make sure the model is in use before deleting it.
	 * 
	 * @param modelId
	 * @param license
	 * @return
	 */
	public boolean checkPreDeleteModel(String license, Integer modelId) {
		// Initialize the required services
		DataingDatabaseService databaseService = (DataingDatabaseService) services
				.get(DataingDatabaseService.class.getName());
		// Business process
		List<?> baseList = databaseService.findByModelId(license, modelId);
		return baseList != null && baseList.size() > 0 ? true : false;
	}

	/**
	 * Query {@code DataingDataModel} by model id
	 * 
	 * @param id
	 * @return {@link DataingDataModel}
	 */
	public DataingDataModel findModelById(Integer id) {
		// Initialize the required services
		DataingDataModelService /* DataModel */ dataModelService = (DataingDataModelService) services
				.get(DataingDataModelService.class.getName());
		DataingDataModelFieldMapService/* DataModelFiled */ dataModelFieldMapService = (DataingDataModelFieldMapService) services
				.get(DataingDataModelFieldMapService.class.getName());
		// Business process
		DataingDataModel dataModel = dataModelService.find(id);
		List<DataingDataModelFieldMap> modelFieldMap = dataModelFieldMapService.findByModelId(id);
		if (modelFieldMap != null && modelFieldMap.size() > 0) {
			sb.setLength(0);
			for (DataingDataModelFieldMap fieldMap : modelFieldMap) {
				sb.append(fieldMap.getFieldId()).append(CommonConstants.COMMA_SEPARATOR);
			}
			sb.deleteCharAt(sb.length() - 1);
			dataModel.setModelFieldIds(sb.toString());
		}
		return dataModel;
	}

	/**
	 * Query {@code DataingDataField} by type and database ids
	 * 
	 * @param type
	 * @param list
	 * @return {@link List}
	 */
	public List<DataingDataField> findFieldsInBasesByAccess(EAccessType type, List<Integer> list) {
		// Initialize the required services
		DataingDataFieldService dataFieldService = (DataingDataFieldService) services
				.get(DataingDataFieldService.class.getName());
		// Business process
		return dataFieldService.findFieldsInBasesByAccess(type.ordinal(), list, list.size());
	}

	/**
	 * Query can show data fields by {@code DataingDataModel}
	 * 
	 * @param modelId
	 * @return {@link List}
	 */
	public List<DataingDataField> findDisplayFieldsByModelId(Integer modelId) {
		// Initialize the required services
		DataingDataFieldService dataFieldService = (DataingDataFieldService) services
				.get(DataingDataFieldService.class.getName());
		// Business process
		return dataFieldService.findDisplayFieldsByModelId(modelId);
	}

	/**
	 * Query can select data fields when a data template is added
	 * 
	 * @param type
	 * @return {@link List}
	 */
	public List<DataingDataField> findFieldByType(EDataFieldType type) {
		// Initialize the required services
		DataingDataFieldService dataFieldService = (DataingDataFieldService) services
				.get(DataingDataFieldService.class.getName());
		// Business process
		return dataFieldService.findByType(type.ordinal());
	}

	/**
	 * Query can select data fields when a data template is added
	 * 
	 * @param type
	 * @return {@link List}
	 */
	public List<DataingDataField> findFieldsByBaseId(Integer baseId) {
		// Initialize the required services
		DataingDataFieldService dataFieldService = (DataingDataFieldService) services
				.get(DataingDataFieldService.class.getName());
		// Business process
		return dataFieldService.findFieldsByBaseId(baseId);
	}

	/**
	 * Query can show data fields when a data list
	 * 
	 * @param type
	 * @return {@link List}
	 */
	public List<DataingDataField> findDisplayFieldsByBaseId(Integer baseId) {
		// Initialize the required services
		DataingDataFieldService dataFieldService = (DataingDataFieldService) services
				.get(DataingDataFieldService.class.getName());
		// Business process
		return dataFieldService.findDisplayFieldsByBaseId(baseId);
	}

	/**
	 * Query all database model by {@code ELibraryType}
	 * 
	 * @param modelType
	 * @param license
	 * @return {@link List}
	 */
	public List<DataingDataModel> findModelByType(ELibraryType modelType, String license) {
		// Initialize the required services
		DataingDataModelService dataModelService = (DataingDataModelService) services
				.get(DataingDataModelService.class.getName());
		// Business process
		return dataModelService.findByType(modelType.ordinal(), license);
	}

	/**
	 * Query all database model and paging the result
	 * 
	 * @param name
	 * @param first
	 * @param size
	 * @return {@link List}
	 */
	public ListResult<DataingDataModel> findModelByPage(String license, String name, Integer first, Integer size) {
		// Initialize the required services
		DataingDataModelService dataModelService = (DataingDataModelService) services
				.get(DataingDataModelService.class.getName());
		// Business process
		ListResult<DataingDataModel> result = new ListResult<DataingDataModel>();
		if (/* Query all */isNullOrEmpty(name)) {
			result.setData(dataModelService.findByPage(license, first, size));
			result.setTotal(dataModelService.count(license));
		} else {
			result.setData(dataModelService.findNameByPage(license, name, first, size));
			result.setTotal(dataModelService.countByName(license, name));
		}
		return result;
	}
}
