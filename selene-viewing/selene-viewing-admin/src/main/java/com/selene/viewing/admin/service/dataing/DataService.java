package com.selene.viewing.admin.service.dataing;

import static cn.com.lemon.base.Strings.isNullOrEmpty;
import static cn.com.lemon.base.Strings.split;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nanshan.papaya.rpc.client.Client;
import com.selene.common.config.service.ServiceConfiger;
import com.selene.common.constants.CommonConstants;
import com.selene.common.constants.ServiceConstants;
import com.selene.common.constants.util.ELibraryNodeType;
import com.selene.common.constants.util.ELibraryType;
import com.selene.common.result.ListResult;
import com.selene.common.tree.support.LibraryTreeNode;
import com.selene.common.tree.DefaultTreeNode.PropertySetter;
import com.selene.common.util.RedisClient;
import com.selene.dataing.model.DataingDataField;
import com.selene.dataing.model.DataingDataModel;
import com.selene.dataing.model.DataingDataModelFieldMap;
import com.selene.dataing.model.DataingDatabase;
import com.selene.dataing.model.enums.EDataFieldType;
import com.selene.dataing.model.service.DataingDataFieldService;
import com.selene.dataing.model.service.DataingDataModelFieldMapService;
import com.selene.dataing.model.service.DataingDataModelService;
import com.selene.dataing.model.service.DataingDataTaskService;
import com.selene.dataing.model.service.DataingDataTaskSubService;
import com.selene.dataing.model.service.DataingDatabaseService;

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

	@PostConstruct
	@SuppressWarnings("static-access")
	public void init() {
		LOG.info("[selene-viewing-admin] init " + ServiceConfiger.class.getName() + " service!");
		// Initialization dataing service registry address
		dataingConfiger = new ServiceConfiger(
				DataService.class.getResource("/").getPath() + "selene.sevice.properties");
		String dataingService = dataingConfiger.value(ServiceConstants.DATAING_KEY);
		LOG.info("merchants service address=" + dataingService);
		// Initialization dataing service
		services.put(/* DataModel */DataingDataModelService.class.getName(),
				client.create(DataingDataModelService.class, dataingService));
		services.put(/* DataModelFiled */DataingDataModelFieldMapService.class.getName(),
				client.create(DataingDataModelFieldMapService.class, dataingService));
		services.put(/* DataField */DataingDataFieldService.class.getName(),
				client.create(DataingDataFieldService.class, dataingService));
		services.put(/* DataTask */DataingDataTaskService.class.getName(),
				client.create(DataingDataTaskService.class, dataingService));
		services.put(/* DataTaskSub */DataingDataTaskSubService.class.getName(),
				client.create(DataingDataTaskSubService.class, dataingService));
		services.put(/* Database */DataingDatabaseService.class.getName(),
				client.create(DataingDatabaseService.class, dataingService));
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
