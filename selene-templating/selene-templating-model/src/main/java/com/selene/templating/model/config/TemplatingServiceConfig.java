package com.selene.templating.model.config;

import com.selene.common.constants.ServiceConstants;
import com.selene.templating.model.service.TemplatingContentService;
import com.selene.templating.model.service.TemplatingItemService;
import com.selene.templating.model.service.TemplatingModelBillService;
import com.selene.templating.model.service.TemplatingModelService;
import com.selene.templating.model.service.TemplatingPageService;

/**
 * <b>Selene merchants module<b>
 * <p>
 * Scan all merchants services
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class TemplatingServiceConfig {
	String SEVICES_KEY = ServiceConstants.TEMPLATING_KEY;
	String SEVICES_CLASS_PACKAGE = "com.selene.templating.model.service";
	Class<?>[] SEVICE_CLASSES = { TemplatingContentService.class, TemplatingItemService.class,
			TemplatingModelBillService.class, TemplatingModelService.class, TemplatingPageService.class };
}
