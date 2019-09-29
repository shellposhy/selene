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
	public List<TemplatingModel> findModelByLicenseAndBillId(String license, Integer billId) {
		// Initialize the required services
		TemplatingModelService modelService = (TemplatingModelService) services
				.get(TemplatingModelService.class.getName());
		// Business process
		return modelService.findByLicenseAndBillId(license, billId);
	}

	/* Templating model bill process */
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
