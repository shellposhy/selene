package com.selene.templating.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TemplatingBootstrap {
	private static final Logger LOG = LoggerFactory.getLogger(TemplatingBootstrap.class.getName());

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		LOG.info("Templating Server is starting!");
		try {
			if (new ClassPathXmlApplicationContext("applicationContext.xml") != null) {
				LOG.info("Templating Server is started!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Templating Server is started fail!");
		}
	}

}