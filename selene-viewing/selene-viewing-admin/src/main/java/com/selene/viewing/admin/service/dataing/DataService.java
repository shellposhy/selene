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
import com.selene.common.result.ListResult;
import com.selene.common.util.RedisClient;
import com.selene.dataing.model.DataingDataField;
import com.selene.dataing.model.DataingDataModel;
import com.selene.dataing.model.DataingDataModelFieldMap;
import com.selene.dataing.model.enums.EDataFieldType;
import com.selene.dataing.model.service.DataingDataFieldService;
import com.selene.dataing.model.service.DataingDataModelFieldMapService;
import com.selene.dataing.model.service.DataingDataModelService;

@Service
public class DataService {
	private final static Logger LOG = LoggerFactory.getLogger(DataService.class.getName());
	private ServiceConfiger dataingConfiger;
	private Map<String, Object> services = new HashMap<String, Object>();
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
		services.put(DataingDataModelService.class.getName(),
				client.create(DataingDataModelService.class, dataingService));
		services.put(DataingDataModelFieldMapService.class.getName(),
				client.create(DataingDataModelFieldMapService.class, dataingService));
		services.put(DataingDataFieldService.class.getName(),
				client.create(DataingDataFieldService.class, dataingService));
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
		DataingDataFieldService dataFieldService = (DataingDataFieldService) services
				.get(DataingDataFieldService.class.getName());
		DataingDataModelService dataModelService = (DataingDataModelService) services
				.get(DataingDataModelService.class.getName());
		DataingDataModelFieldMapService dataModelFieldMapService = (DataingDataModelFieldMapService) services
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
			
		}
		return 0;
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
