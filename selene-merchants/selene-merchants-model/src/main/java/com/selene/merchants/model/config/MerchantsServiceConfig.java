package com.selene.merchants.model.config;

import com.selene.common.constants.ServiceConstants;
import com.selene.merchants.model.service.MerchantsActionService;
import com.selene.merchants.model.service.MerchantsLoginTokenService;
import com.selene.merchants.model.service.MerchantsOrgRoleService;
import com.selene.merchants.model.service.MerchantsOrgService;
import com.selene.merchants.model.service.MerchantsRoleActionService;
import com.selene.merchants.model.service.MerchantsRoleDataAuthorityService;
import com.selene.merchants.model.service.MerchantsRoleService;
import com.selene.merchants.model.service.MerchantsUserRoleService;
import com.selene.merchants.model.service.MerchantsUserService;

/**
 * <b>Selene merchants module<b>
 * <p>
 * Scan all merchants services
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class MerchantsServiceConfig {
	String SEVICES_KEY = ServiceConstants.MERCHANTS_KEY;
	String SEVICES_CLASS_PACKAGE = "com.selene.merchants.model.service";
	Class<?>[] SEVICE_CLASSES = { MerchantsActionService.class, MerchantsLoginTokenService.class,
			MerchantsOrgRoleService.class, MerchantsOrgService.class, MerchantsRoleActionService.class,
			MerchantsRoleDataAuthorityService.class, MerchantsRoleService.class, MerchantsUserRoleService.class,
			MerchantsUserService.class };
}
