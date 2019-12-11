package com.selene.viewing.admin.service.config;

import java.util.concurrent.ConcurrentHashMap;

import com.nanshan.papaya.rpc.client.Client;
import com.selene.common.config.service.BaseConfiger;
import com.selene.common.config.service.ServiceConfiger;
import com.selene.logging.model.Logger;
import com.selene.logging.model.LoggerFactory;
import com.selene.viewing.admin.contants.Contants;

import static cn.com.lemon.base.Strings.isNullOrEmpty;

/**
 * Base class for service configuration,
 * <p>
 * retrieves the service based on RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class ServiceConfigure {
	private static final Logger LOG = LoggerFactory.getLogger(ServiceConfigure.class.getName());
	private Client client;
	// Service map
	private final ConcurrentHashMap<String, Object> SERVICES = new ConcurrentHashMap<String, Object>();
	private static final String ROOT_PATH = ServiceConfigure.class.getResource("/").getPath();
	private static final ServiceConfiger SERVICES_CONFIG = new ServiceConfiger(ROOT_PATH + Contants.SERVICE_FILE_NAME);
	private static final BaseConfiger BASE_CONFIG = new BaseConfiger(ROOT_PATH + Contants.CONFIG_FILE_NAME);

	@SuppressWarnings({ "unchecked", "static-access" })
	public <T extends Object> T service(Class<T> clazz, String clazzName, String serviceName) {
		T result = (T) SERVICES.get(clazzName);
		if (null == result) {
			String serverAddress = SERVICES_CONFIG.value(serviceName);
			if (!isNullOrEmpty(serverAddress)) {
				result = getClient().create((Class<T>) clazz, serverAddress);
				if (result != null) {
					LOG.info("[" + serviceName + "][" + serverAddress + "] " + clazzName + " rpc service created!");
					SERVICES.put(clazzName, result);
				}
			}
		}
		return result;
	}

	public String level() {
		return BASE_CONFIG.value("product.level");
	}

	public String license() {
		return BASE_CONFIG.value("product.license");
	}

	// Properties into by spring bean
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
