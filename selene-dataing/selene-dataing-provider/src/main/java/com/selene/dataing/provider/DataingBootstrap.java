package com.selene.dataing.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DataingBootstrap {
	private static final Logger LOG = LoggerFactory.getLogger(DataingBootstrap.class.getName());

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		LOG.info("Dataing Server is starting!");
		try {
			if (new ClassPathXmlApplicationContext("applicationContext.xml") != null) {
				LOG.info("Dataing Server is started!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Dataing Server is started fail!");
		}
	}

}
