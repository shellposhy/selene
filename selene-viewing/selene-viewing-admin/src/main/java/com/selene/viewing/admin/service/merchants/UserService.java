package com.selene.viewing.admin.service.merchants;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nanshan.papaya.rpc.client.Client;
import com.selene.common.config.service.ServiceConfiger;
import com.selene.common.constants.ServiceConstants;
import com.selene.merchants.model.MerchantsAction;
import com.selene.merchants.model.MerchantsLoginToken;
import com.selene.merchants.model.MerchantsUser;
import com.selene.merchants.model.enums.EActionUserType;
import com.selene.merchants.model.service.MerchantsActionService;
import com.selene.merchants.model.service.MerchantsLoginTokenService;
import com.selene.merchants.model.service.MerchantsOrgService;
import com.selene.merchants.model.service.MerchantsUserRoleService;
import com.selene.merchants.model.service.MerchantsUserService;
import com.selene.viewing.admin.vo.merchants.MerchantsUserVO;

@Service
public class UserService {
	private final static Logger LOG = LoggerFactory.getLogger(UserService.class.getName());
	private ServiceConfiger configer;
	@Resource
	private Client client;

	@PostConstruct
	public void init() {
		LOG.info("[selene-viewing-admin] init " + ServiceConfiger.class.getName() + " service!");
		configer = new ServiceConfiger(UserService.class.getResource("/").getPath() + "selene.sevice.properties");
	}

	@SuppressWarnings("static-access")
	public MerchantsUser findByNameAndPass(String name, String password) {
		MerchantsUserService userService = client.create(MerchantsUserService.class,
				configer.value(ServiceConstants.MERCHANTS_KEY));
		return userService.findByNamePassword(name, password, EActionUserType.Admin);
	}

	@SuppressWarnings("static-access")
	public MerchantsUserVO findMenuTreeByUser(MerchantsUserVO userVO) {
		MerchantsActionService actionService = client.create(MerchantsActionService.class,
				configer.value(ServiceConstants.MERCHANTS_KEY));
		MerchantsUserRoleService userRoleService = client.create(MerchantsUserRoleService.class,
				configer.value(ServiceConstants.MERCHANTS_KEY));
		List<Integer> roleIdList = userRoleService.findAdminGroupIdsByUserId(userVO.getId());
		boolean allAction = null != roleIdList && roleIdList.size() > 0 ? true : false;
		userVO.setAllAdmin(allAction);
		List<MerchantsAction> actionList = null;
		if (allAction) {
			actionList = actionService.findByType(userVO.getType());
		} else {
			actionList = actionService.findByUserIdAndType(userVO.getId(), userVO.getType());
		}
		userVO.setActionTree(actionService.findMenuTreeByUser(actionList, allAction));
		return userVO;
	}

	@SuppressWarnings("static-access")
	public String findLicenseByUserId(Integer userId) {
		return client.create(MerchantsOrgService.class, configer.value(ServiceConstants.MERCHANTS_KEY))
				.findOrgLicenseByUserId(userId);
	}

	@SuppressWarnings("static-access")
	public MerchantsLoginToken findLoginTokenByUserId(Integer userId) {
		return client.create(MerchantsLoginTokenService.class, configer.value(ServiceConstants.MERCHANTS_KEY))
				.findByUserId(userId);
	}
}
