package com.selene.workflow.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.selene.logging.model.Logger;
import com.selene.logging.model.LoggerFactory;

public class WorkflowBootstrap {
	private static final Logger LOG = LoggerFactory.getLogger(WorkflowBootstrap.class.getName());

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		LOG.info("Workflow Server is starting!");
		try {
			if (new ClassPathXmlApplicationContext("applicationContext.xml") != null) {
				LOG.info("Workflow Server is started!");
			}
		} catch (Exception e) {
			LOG.error("Workflow Server is started fail!", e);
		}
	}
}
