package com.selene.viewing.admin.service.merchants;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nanshan.papaya.rpc.client.Client;
import com.selene.merchants.model.MerchantsAction;
import com.selene.merchants.model.MerchantsUser;
import com.selene.merchants.model.enums.EActionUserType;
import com.selene.merchants.model.service.MerchantsActionService;
import com.selene.merchants.model.service.MerchantsUserRoleService;
import com.selene.merchants.model.service.MerchantsUserService;

import jodd.props.Props;

@Service
public class UserService {
	private final static Logger LOG = LoggerFactory.getLogger(UserService.class.getName());
	@Resource
	private Client client;

	private static final String PATH = UserService.class.getResource("/").getPath();
	private Props props = null;

	@PostConstruct
	public void init() {
		props = new Props();
		try {
			props.load(new File(PATH + "config.properties"));
		} catch (IOException e) {
			LOG.error(PATH + "config.properties not find!");
		}
	}

	@SuppressWarnings("static-access")
	public MerchantsUser findByNameAndPass(String name, String password) {
		MerchantsUserService userService = client.create(MerchantsUserService.class,
				props.getValue("merchants.server.address"));
		return userService.findByNamePassword(name, password, EActionUserType.Admin);
	}

	@SuppressWarnings("static-access")
	public String findMenuTreeByUser(MerchantsUser merchantsUser) {
		MerchantsActionService actionService = client.create(MerchantsActionService.class,
				props.getValue("merchants.server.address"));
		MerchantsUserRoleService userRoleService = client.create(MerchantsUserRoleService.class,
				props.getValue("merchants.server.address"));
		List<Integer> roleIdList = userRoleService.findAdminGroupIdsByUserId(merchantsUser.getId());
		boolean allAction = null != roleIdList && roleIdList.size() > 0 ? true : false;
		List<MerchantsAction> actionList = null;
		if (allAction) {
			actionList = actionService.findByType(Integer.valueOf(EActionUserType.Admin.ordinal()));
		} else {
			actionList = actionService.findByUserIdAndType(merchantsUser.getId(),
					Integer.valueOf(EActionUserType.Admin.ordinal()));
		}
		return actionService.findMenuTreeByUser(actionList, allAction);
	}

}
