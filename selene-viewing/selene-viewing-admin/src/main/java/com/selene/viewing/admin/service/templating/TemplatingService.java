package com.selene.viewing.admin.service.templating;

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
import com.selene.common.util.RedisClient;
import com.selene.templating.model.TemplatingModel;
import com.selene.templating.model.TemplatingModelBill;
import com.selene.templating.model.service.TemplatingModelBillService;
import com.selene.templating.model.service.TemplatingModelService;

@Service
public class TemplatingService {
	private final static Logger LOG = LoggerFactory.getLogger(TemplatingService.class.getName());
	private ServiceConfiger templatingConfiger;
	private Map<String, Object> services = new HashMap<String, Object>();
	@Resource
	private Client client;
	@Resource
	private RedisClient redisClient;

	@PostConstruct
	@SuppressWarnings("static-access")
	public void init() {
		LOG.info("[selene-viewing-admin] init " + ServiceConfiger.class.getName() + " service!");
		// Initialization templating service registry address
		templatingConfiger = new ServiceConfiger(
				TemplatingService.class.getResource("/").getPath() + "selene.sevice.properties");
		String templatingService = templatingConfiger.value(ServiceConstants.TEMPLATING_KEY);
		LOG.info("templating service address=" + templatingService);
		// Initialization searching service
		services.put(TemplatingModelBillService.class.getName(),
				client.create(TemplatingModelBillService.class, templatingService));
		services.put(TemplatingModelService.class.getName(),
				client.create(TemplatingModelService.class, templatingService));
	}

	/* Templating model process */

	/**
	 * Save {@code TemplatingModel}
	 * 
	 * @param modelBill
	 * @return
	 */
	public int saveModel(TemplatingModel model) {
		// Initialize the required services
		TemplatingModelService modelService = (TemplatingModelService) services
				.get(TemplatingModelService.class.getName());
		// Business process
		if (model.getId() == null) {
			int result = modelService.insert(model);
			model.setId(result);
			return result;
		} else {
			return modelService.update(model);
		}
	}

	/**
	 * Find {@code TemplatingModel} by primary key
	 * 
	 * @param id
	 * @return {@code TemplatingModel}
	 */
	public TemplatingModel findModelById(Integer id) {
		// Initialize the required services
		TemplatingModelService modelService = (TemplatingModelService) services
				.get(TemplatingModelService.class.getName());
		// Business process
		return modelService.find(id);
	}

	/**
	 * Query {@code TemplatingModel} by {@code String} and {@code Integer}
	 * 
	 * @param license
	 * @param billId
	 * @return
	 */
	public List<TemplatingModel> findModelByLicenseAndBillId(String license, Integer billId) {
		// Initialize the required services
		TemplatingModelService modelService = (TemplatingModelService) services
				.get(TemplatingModelService.class.getName());
		// Business process
		return modelService.findByLicenseAndBillId(license, billId);
	}

	/* Templating model bill process */
	/**
	 * Save {@code TemplatingModelBill}
	 * 
	 * @param modelBill
	 * @return
	 */
	public int saveModelBill(TemplatingModelBill modelBill) {
		// Initialize the required services
		TemplatingModelBillService modelBillService = (TemplatingModelBillService) services
				.get(TemplatingModelBillService.class.getName());
		// Business process
		if (modelBill.getId() == null) {
			int result = modelBillService.insert(modelBill);
			modelBill.setId(result);
			return result;
		} else {
			return modelBillService.update(modelBill);
		}
	}

	/**
	 * Find {@code TemplatingModelBill} by primary key
	 * 
	 * @param id
	 * @return {@code TemplatingModelBill}
	 */
	public TemplatingModelBill findModelBillById(Integer id) {
		// Initialize the required services
		TemplatingModelBillService modelBillService = (TemplatingModelBillService) services
				.get(TemplatingModelBillService.class.getName());
		// Business process
		return modelBillService.find(id);
	}

	/**
	 * Query all {@code TemplatingModelBill} by {@code String} license
	 * 
	 * @param license
	 * @return {@code List}
	 */
	public List<TemplatingModelBill> findModelBillByLicense(String license) {
		// Initialize the required services
		TemplatingModelBillService modelBillService = (TemplatingModelBillService) services
				.get(TemplatingModelBillService.class.getName());
		// Business process
		return modelBillService.findByLicense(license);
	}
}