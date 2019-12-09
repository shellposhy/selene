package com.selene.searching.provider;

import com.selene.logging.model.Logger;
import com.selene.logging.model.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SearchingBootstrap {
	private static final Logger LOG = LoggerFactory.getLogger(SearchingBootstrap.class.getName());

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		LOG.info("Searching Server is starting!");
		try {
			if (new ClassPathXmlApplicationContext("applicationContext.xml") != null) {
				LOG.info("Searching Server is started!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Searching Server is started fail!");
		}
	}

}
