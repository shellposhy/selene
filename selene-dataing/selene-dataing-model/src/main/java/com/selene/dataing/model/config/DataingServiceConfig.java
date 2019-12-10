package com.selene.dataing.model.config;

import com.selene.common.constants.ServiceConstants;
import com.selene.dataing.model.service.DataingDataFieldService;
import com.selene.dataing.model.service.DataingDataModelFieldMapService;
import com.selene.dataing.model.service.DataingDataModelService;
import com.selene.dataing.model.service.DataingDataTableService;
import com.selene.dataing.model.service.DataingDataTagService;
import com.selene.dataing.model.service.DataingDataTaskService;
import com.selene.dataing.model.service.DataingDataTaskSubService;
import com.selene.dataing.model.service.DataingDatabaseFieldMapService;
import com.selene.dataing.model.service.DataingDatabaseService;
import com.selene.dataing.model.service.DataingJdbcTemplateService;

/**
 * <b>Selene dataing module<b>
 * <p>
 * Scan all dataing services
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface DataingServiceConfig {
	String SEVICES_KEY = ServiceConstants.DATAING_KEY;
	String SEVICES_CLASS_PACKAGE = "com.selene.dataing.model.service";
	Class<?>[] SEVICE_CLASSES = { DataingDatabaseFieldMapService.class, DataingDatabaseService.class,
			DataingDataFieldService.class, DataingDataModelFieldMapService.class, DataingDataModelService.class,
			DataingDataTableService.class, DataingDataTagService.class, DataingDataTaskService.class,
			DataingDataTaskSubService.class, DataingJdbcTemplateService.class };
}
