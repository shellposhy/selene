package com.selene.dataing.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Bootstrap {
	private static final Logger LOG = LoggerFactory.getLogger(Bootstrap.class.getName());

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		LOG.info("Protal Server is starting!");
		try {
			if (new ClassPathXmlApplicationContext("applicationContext.xml") != null) {
				LOG.info("Protal Server is started!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Protal Server is started fail!");
		}
	}

}
