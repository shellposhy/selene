package com.selene.templating.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.selene.logging.model.Logger;
import com.selene.logging.model.LoggerFactory;

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
			LOG.error("Templating Server is started fail!", e);
		}
	}

}
