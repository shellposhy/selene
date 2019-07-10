package com.selene.viewing.admin.service.merchants;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nanshan.papaya.rpc.client.Client;
import com.selene.common.Node;
import com.selene.common.config.service.ServiceConfiger;
import com.selene.common.constants.CommonConstants;
import com.selene.common.constants.ServiceConstants;
import com.selene.common.result.ListResult;
import com.selene.common.token.login.LoginToken;
import com.selene.common.tree.DefaultTreeNode;
import com.selene.common.util.RedisClient;
import com.selene.merchants.model.MerchantsAction;
import com.selene.merchants.model.MerchantsLoginToken;
import com.selene.merchants.model.MerchantsOrg;
import com.selene.merchants.model.MerchantsRole;
import com.selene.merchants.model.MerchantsUser;
import com.selene.merchants.model.enums.EActionUserType;
import com.selene.merchants.model.service.MerchantsActionService;
import com.selene.merchants.model.service.MerchantsLoginTokenService;
import com.selene.merchants.model.service.MerchantsOrgService;
import com.selene.merchants.model.service.MerchantsRoleService;
import com.selene.merchants.model.service.MerchantsUserRoleService;
import com.selene.merchants.model.service.MerchantsUserService;
import com.selene.viewing.admin.vo.merchants.MerchantsUserVO;

import static cn.com.lemon.base.DateUtil.format;
import static cn.com.lemon.base.Strings.MD5;
import static cn.com.lemon.base.Strings.isNullOrEmpty;

@Service
public class UserService {
	private final static Logger LOG = LoggerFactory.getLogger(UserService.class.getName());
	private ServiceConfiger configer;
	private Map<String, Object> services = new HashMap<String, Object>();
	@Resource
	private Client client;
	@Resource
	private RedisClient redisClient;

	@PostConstruct
	public void init() {
		LOG.info("[selene-viewing-admin] init " + ServiceConfiger.class.getName() + " service!");
		configer = new ServiceConfiger(UserService.class.getResource("/").getPath() + "selene.sevice.properties");
	}

	@SuppressWarnings("static-access")
	public MerchantsOrg findMerchantsOrgById(Integer id) {
		// Initialize the required services
		MerchantsOrgService orgService = (MerchantsOrgService) services.get(MerchantsOrgService.class.getName());
		if (orgService == null) {
			orgService = client.create(MerchantsOrgService.class, configer.value(ServiceConstants.MERCHANTS_KEY));
			services.put(MerchantsOrgService.class.getName(), orgService);
		}
		// business process
		return orgService.find(id);
	}

	@SuppressWarnings("static-access")
	public List<Node<Integer, String>> findOrgRole(Integer userId) {
		// Initialize the required services
		MerchantsRoleService roleService = (MerchantsRoleService) services.get(MerchantsRoleService.class.getName());
		if (roleService == null) {
			roleService = client.create(MerchantsRoleService.class, configer.value(ServiceConstants.MERCHANTS_KEY));
			services.put(MerchantsRoleService.class.getName(), roleService);
		}
		MerchantsOrgService orgService = (MerchantsOrgService) services.get(MerchantsOrgService.class.getName());
		if (orgService == null) {
			orgService = client.create(MerchantsOrgService.class, configer.value(ServiceConstants.MERCHANTS_KEY));
			services.put(MerchantsOrgService.class.getName(), orgService);
		}
		// business process
		List<MerchantsRole> list = roleService.findByUserId(userId, orgService.findOrgLicenseByUserId(userId));
		if (list != null && list.size() > 0) {
			List<Node<Integer, String>> result = new ArrayList<Node<Integer, String>>();
			for (MerchantsRole role : list) {
				Node<Integer, String> node = new Node<Integer, String>();
				node.id = role.getId();
				node.name = role.getName();
				result.add(node);
			}
			return result;
		}
		return null;
	}

	@SuppressWarnings("static-access")
	public ListResult<MerchantsUser> findOrgUserByPage(Integer orgId, String name, Integer firstSize, Integer size) {
		// Initialize the required services
		MerchantsUserService userService = (MerchantsUserService) services.get(MerchantsUserService.class.getName());
		if (userService == null) {
			userService = client.create(MerchantsUserService.class, configer.value(ServiceConstants.MERCHANTS_KEY));
			services.put(MerchantsUserService.class.getName(), userService);
		}
		// business process
		ListResult<MerchantsUser> result = new ListResult<MerchantsUser>();
		if (isNullOrEmpty(name)) {
			result.setData(userService.findByOrgId(orgId, EActionUserType.Admin, firstSize, size));
			result.setTotal(userService.countByOrgId(orgId, EActionUserType.Admin));
		} else {
			result.setData(userService.findByOrgAndName(orgId, name, EActionUserType.Admin, firstSize, size));
			result.setTotal(userService.countByOrgAndName(orgId, name, EActionUserType.Admin));
		}
		return result;
	}

	@SuppressWarnings("static-access")
	public DefaultTreeNode findUserOrgTree(Integer userId) {
		// Initialize the required services
		MerchantsOrgService orgService = (MerchantsOrgService) services.get(MerchantsOrgService.class.getName());
		if (orgService == null) {
			orgService = client.create(MerchantsOrgService.class, configer.value(ServiceConstants.MERCHANTS_KEY));
			services.put(MerchantsOrgService.class.getName(), orgService);
		}
		// business process
		String license = orgService.findOrgLicenseByUserId(userId);
		List<MerchantsOrg> list = /* Super Administrator */ (userId.intValue() == CommonConstants.SUPER_ADMIN)
				? orgService.findAll(Boolean.TRUE) : orgService.findByLicense(license);
		return DefaultTreeNode.parseTree(list);
	}

	@SuppressWarnings("static-access")
	public MerchantsUser findByNameAndPass(String name, String password) {
		// Initialize the required services
		MerchantsUserService userService = (MerchantsUserService) services.get(MerchantsUserService.class.getName());
		if (userService == null) {
			userService = client.create(MerchantsUserService.class, configer.value(ServiceConstants.MERCHANTS_KEY));
			services.put(MerchantsUserService.class.getName(), userService);
		}
		// business process
		return userService.findByNamePassword(name, password, EActionUserType.Admin);
	}

	@SuppressWarnings("static-access")
	public MerchantsUserVO findMenuTreeByUser(MerchantsUserVO userVO) {
		// Initialize the required services
		MerchantsActionService actionService = (MerchantsActionService) services
				.get(MerchantsActionService.class.getName());
		if (actionService == null) {
			actionService = client.create(MerchantsActionService.class, configer.value(ServiceConstants.MERCHANTS_KEY));
			services.put(MerchantsActionService.class.getName(), actionService);
		}
		MerchantsUserRoleService userRoleService = (MerchantsUserRoleService) services
				.get(MerchantsUserRoleService.class.getName());
		if (userRoleService == null) {
			userRoleService = client.create(MerchantsUserRoleService.class,
					configer.value(ServiceConstants.MERCHANTS_KEY));
			services.put(MerchantsUserRoleService.class.getName(), userRoleService);
		}
		// business process
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
		// Initialize the required services
		MerchantsOrgService orgService = (MerchantsOrgService) services.get(MerchantsOrgService.class.getName());
		if (orgService == null) {
			orgService = client.create(MerchantsOrgService.class, configer.value(ServiceConstants.MERCHANTS_KEY));
			services.put(MerchantsOrgService.class.getName(), orgService);
		}
		// business process
		return orgService.findOrgLicenseByUserId(userId);
	}

	@SuppressWarnings("static-access")
	public MerchantsLoginToken token(Integer userId, String appKey, boolean tokenType, boolean refreshType) {
		MerchantsLoginToken loginToken = null;
		// Initialize the required services
		MerchantsLoginTokenService loginTokenService = (MerchantsLoginTokenService) services
				.get(MerchantsLoginTokenService.class.getName());
		if (loginTokenService == null) {
			loginTokenService = client.create(MerchantsLoginTokenService.class,
					configer.value(ServiceConstants.MERCHANTS_KEY));
			services.put(MerchantsLoginTokenService.class.getName(), loginTokenService);
		}
		// business process
		loginToken = loginTokenService.findByUserId(userId);
		// The first login
		if (loginToken == null) {
			// 7 days
			long dateTime = System.currentTimeMillis();
			long expTime = dateTime + CommonConstants.DEFAULT_TOKEN_LONG_TIMEOUT * 1000;
			Date exp = new Date();
			exp.setTime(expTime);
			Date loginTime = new Date();
			loginTime.setTime(dateTime);
			String jwt = new LoginToken().encrypt(appKey, userId.toString(), appKey, exp);
			String payload = jwt.substring(jwt.indexOf(CommonConstants.HIDE_KEY_PREFIX) + 1,
					jwt.lastIndexOf(CommonConstants.HIDE_KEY_PREFIX));
			String signature = jwt.substring(jwt.lastIndexOf(CommonConstants.HIDE_KEY_PREFIX) + 1, jwt.length());
			String token = MD5(payload + format(loginTime, "yyyyMMddHHmmssSSS"));
			loginToken = new MerchantsLoginToken(userId, loginTime, token, token, payload, signature, jwt);
			if (loginTokenService.insert(loginToken) > 0) {
				return loginToken;
			}
		}
		// token check
		else {
			// If refresh token expiration ,regenerating token and refresh token
			if (refreshType) {
				long dateTime = System.currentTimeMillis();
				long expTime = dateTime + CommonConstants.DEFAULT_TOKEN_LONG_TIMEOUT * 1000;
				Date exp = new Date();
				exp.setTime(expTime);
				Date loginTime = new Date();
				loginTime.setTime(dateTime);
				String jwt = new LoginToken().encrypt(appKey, userId.toString(), appKey, exp);
				String payload = jwt.substring(jwt.indexOf(CommonConstants.HIDE_KEY_PREFIX) + 1,
						jwt.lastIndexOf(CommonConstants.HIDE_KEY_PREFIX));
				String signature = jwt.substring(jwt.lastIndexOf(CommonConstants.HIDE_KEY_PREFIX) + 1, jwt.length());
				String token = MD5(payload + format(loginTime, "yyyyMMddHHmmssSSS"));
				// Delete history token and refresh token
				redisClient.delete(loginToken.getToken());
				redisClient.delete(loginToken.getRefreshToken());
				// Set new token and refresh token
				loginToken.setLoginTime(loginTime);
				loginToken.setRedisKey(token);
				loginToken.setToken(token);
				loginToken.setRefreshToken(payload);
				loginToken.setSecretKey(signature);
				loginToken.setJwt(jwt);
				if (loginTokenService.update(loginToken) > 0) {
					return loginToken;
				}
			}
			// If refresh token not expiration ,regenerating token
			else {
				// regenerating token
				if (tokenType) {
					// Update token by current time
					long dateTime = System.currentTimeMillis();
					Date loginTime = new Date();
					loginTime.setTime(dateTime);
					String token = MD5(loginToken.getRefreshToken() + format(loginTime, "yyyyMMddHHmmssSSS"));
					// Delete history token
					redisClient.delete(loginToken.getToken());
					// only update new token
					loginToken.setRedisKey(token);
					loginToken.setToken(token);
					if (loginTokenService.update(loginToken) > 0) {
						return loginToken;
					}
				}
			}
		}
		return null;
	}
}
