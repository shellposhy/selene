package com.selene.searching.model.config;

import com.selene.common.constants.ServiceConstants;
import com.selene.searching.model.service.SearchingIndexService;

/**
 * <b>Selene merchants module<b>
 * <p>
 * Scan all merchants services
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class SearchingServiceConfig {
	String SEVICES_KEY = ServiceConstants.SEARCHING_KEY;
	String SEVICES_CLASS_PACKAGE = "com.selene.searching.model.service";
	Class<?>[] SEVICE_CLASSES = { SearchingIndexService.class };
}
