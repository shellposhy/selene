package com.selene.templating.provider.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.selene.common.constants.DatabaseConstants;

import static com.selene.common.util.Protocols.ip;
import static cn.com.lemon.base.Strings.isNullOrEmpty;
import static cn.com.lemon.util.security.ContentEncryptUtil.decrypt;

/**
 * Modify the properties file contents before starting the service.
 * 
 * @author shellpo shih
 * @version 1.0
 */
public class PropertyPlaceholderConfigurerExtension extends PropertyPlaceholderConfigurer {
	private static final Logger LOG = LoggerFactory.getLogger(PropertyPlaceholderConfigurerExtension.class.getName());
	private static final String SERVER_IP_AND_PORT_KEY = "templating.server.address";

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		// Server IP and port
		String serverIPAndPort = props.getProperty(SERVER_IP_AND_PORT_KEY);
		LOG.info("Old server address=" + serverIPAndPort);
		String port = serverIPAndPort.split(":")[1];
		String ip = ip();
		LOG.info("New server address=" + ip + ":" + port);
		if (!isNullOrEmpty(serverIPAndPort)) {
			props.setProperty(SERVER_IP_AND_PORT_KEY, serverIPAndPort);
		}
		// Database user name
		String userName = props.getProperty(DatabaseConstants.DATAING_USERNAME_KEY);
		if (!isNullOrEmpty(userName)) {
			props.setProperty(DatabaseConstants.DATAING_USERNAME_KEY, decrypt(userName, DatabaseConstants.SELENE_AUTH));
		}
		// Database password
		String userPass = props.getProperty(DatabaseConstants.DATAING_PASSWORD_KEY);
		if (!isNullOrEmpty(userPass)) {
			props.setProperty(DatabaseConstants.DATAING_PASSWORD_KEY, decrypt(userPass, DatabaseConstants.SELENE_AUTH));
		}
		super.processProperties(beanFactoryToProcess, props);
	}
}
