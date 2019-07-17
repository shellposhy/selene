package com.selene.viewing.admin.service.dataing;

import static cn.com.lemon.base.Strings.isNullOrEmpty;

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
import com.selene.common.constants.ServiceConstants;
import com.selene.common.result.ListResult;
import com.selene.common.util.RedisClient;
import com.selene.dataing.model.DataingDataModel;
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
		// Initialization dataing service
		services.put(DataingDataModelService.class.getName(),
				client.create(DataingDataModelService.class, dataingService));
		services.put(DataingDataModelFieldMapService.class.getName(),
				client.create(DataingDataModelFieldMapService.class, dataingService));
	}

	/**
	 * Query all database model and paging the result
	 * 
	 * @param name
	 * @param first
	 * @param size
	 * @return {@link List}
	 */
	public ListResult<DataingDataModel> findModelByPage(String name, Integer first, Integer size) {
		// Initialize the required services
		DataingDataModelService dataModelService = (DataingDataModelService) services
				.get(DataingDataModelService.class.getName());
		// Business process
		ListResult<DataingDataModel> result = new ListResult<DataingDataModel>();
		if (/* Query all */isNullOrEmpty(name)) {
			result.setData(dataModelService.findByPage(first, size));
			result.setTotal(dataModelService.count(Boolean.TRUE));
		} else {
			result.setData(dataModelService.findNameByPage(name, first, size));
			result.setTotal(dataModelService.countByName(name));
		}
		return result;
	}
}
