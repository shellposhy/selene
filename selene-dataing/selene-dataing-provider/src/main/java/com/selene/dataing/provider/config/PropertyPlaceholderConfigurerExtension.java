package com.selene.dataing.provider.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import static com.selene.common.util.Protocols.ip;
import static cn.com.lemon.base.Strings.isNullOrEmpty;

/**
 * Modify the properties file contents before starting the service.
 * 
 * @author shellpo shih
 * @version 1.0
 */
public class PropertyPlaceholderConfigurerExtension extends PropertyPlaceholderConfigurer {
	private static final Logger LOG = LoggerFactory.getLogger(PropertyPlaceholderConfigurerExtension.class.getName());
	private static final String SERVER_IP_AND_PORT_KEY = "dataing.server.address";

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		String serverIPAndPort = props.getProperty(SERVER_IP_AND_PORT_KEY);
		LOG.info("Old server address=" + serverIPAndPort);
		String port = serverIPAndPort.split(":")[1];
		String ip = ip();
		LOG.info("New server address=" + ip + ":" + port);
		if (!isNullOrEmpty(serverIPAndPort)) {
			// props.setProperty(SERVER_IP_AND_PORT_KEY, isNullOrEmpty(ip) ?
			// serverIPAndPort : ip + ":" + port);
			props.setProperty(SERVER_IP_AND_PORT_KEY, serverIPAndPort);
		}
		super.processProperties(beanFactoryToProcess, props);
	}
}
