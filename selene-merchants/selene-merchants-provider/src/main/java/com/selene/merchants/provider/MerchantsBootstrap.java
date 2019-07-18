package com.selene.merchants.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MerchantsBootstrap {
	private static final Logger LOG = LoggerFactory.getLogger(MerchantsBootstrap.class.getName());

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		LOG.info("Merchants Server is starting!");
		try {
			if (new ClassPathXmlApplicationContext("applicationContext.xml") != null) {
				LOG.info("Merchants Server is started!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Merchants Server is started fail!");
		}
	}
}
