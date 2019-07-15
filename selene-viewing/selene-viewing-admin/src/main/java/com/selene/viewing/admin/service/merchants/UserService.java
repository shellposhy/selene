package com.selene.viewing.admin.service.merchants;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
import com.selene.common.util.Chineses;
import com.selene.common.util.RedisClient;
import com.selene.merchants.model.Merchants;
import com.selene.merchants.model.MerchantsAction;
import com.selene.merchants.model.MerchantsLoginToken;
import com.selene.merchants.model.MerchantsOrg;
import com.selene.merchants.model.MerchantsOrgRole;
import com.selene.merchants.model.MerchantsRole;
import com.selene.merchants.model.MerchantsRoleAction;
import com.selene.merchants.model.MerchantsUser;
import com.selene.merchants.model.MerchantsUserRole;
import com.selene.merchants.model.enums.EActionUserType;
import com.selene.merchants.model.enums.EOrgStatus;
import com.selene.merchants.model.enums.EOrgType;
import com.selene.merchants.model.enums.EPageType;
import com.selene.merchants.model.service.MerchantsActionService;
import com.selene.merchants.model.service.MerchantsLoginTokenService;
import com.selene.merchants.model.service.MerchantsOrgRoleService;
import com.selene.merchants.model.service.MerchantsOrgService;
import com.selene.merchants.model.service.MerchantsRoleActionService;
import com.selene.merchants.model.service.MerchantsRoleService;
import com.selene.merchants.model.service.MerchantsUserRoleService;
import com.selene.merchants.model.service.MerchantsUserService;
import com.selene.viewing.admin.vo.merchants.MerchantsUserVO;

import cn.com.lemon.common.enums.EGender;

import static cn.com.lemon.base.DateUtil.format;
import static cn.com.lemon.base.Strings.md5;
import static cn.com.lemon.base.Strings.isNullOrEmpty;
import static cn.com.lemon.base.Strings.split;

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
	@SuppressWarnings("static-access")
	public void init() {
		LOG.info("[selene-viewing-admin] init " + ServiceConfiger.class.getName() + " service!");
		// Initialization merchants service registry address
		configer = new ServiceConfiger(UserService.class.getResource("/").getPath() + "selene.sevice.properties");
		String merchantsService = configer.value(ServiceConstants.MERCHANTS_KEY);
		// Initialization merchants service
		services.put(MerchantsOrgService.class.getName(), client.create(MerchantsOrgService.class, merchantsService));
		services.put(MerchantsRoleService.class.getName(), client.create(MerchantsRoleService.class, merchantsService));
		services.put(MerchantsUserService.class.getName(), client.create(MerchantsUserService.class, merchantsService));
		services.put(MerchantsActionService.class.getName(),
				client.create(MerchantsActionService.class, merchantsService));
		services.put(MerchantsUserRoleService.class.getName(),
				client.create(MerchantsUserRoleService.class, merchantsService));
		services.put(MerchantsLoginTokenService.class.getName(),
				client.create(MerchantsLoginTokenService.class, merchantsService));
		services.put(MerchantsOrgRoleService.class.getName(),
				client.create(MerchantsOrgRoleService.class, merchantsService));
		services.put(MerchantsRoleActionService.class.getName(),
				client.create(MerchantsRoleActionService.class, merchantsService));
	}

	/**
	 * Delete the role by id
	 * 
	 * @param id
	 * @return
	 */
	public int deleteRole(Integer id) {
		// Initialize the required services
		MerchantsRoleService /* Role services */ roleService = (MerchantsRoleService) services
				.get(MerchantsRoleService.class.getName());
		MerchantsRoleActionService/* Role action services */ roleActionService = (MerchantsRoleActionService) services
				.get(MerchantsRoleActionService.class.getName());
		// Business process
		if (roleActionService.deleteByGroupId(id) > 0) {
			return roleService.delete(id);
		}
		return 0;
	}

	/**
	 * Make sure the role is in use before deleting it.
	 * 
	 * @param roleId
	 * @return
	 */
	public boolean checkPreDeleteRole(Integer roleId) {
		// Initialize the required services
		MerchantsOrgRoleService /* Role for organization */ orgRoleService = (MerchantsOrgRoleService) services
				.get(MerchantsOrgRoleService.class.getName());
		MerchantsUserRoleService/* Role for user */ userRoleService = (MerchantsUserRoleService) services
				.get(MerchantsUserRoleService.class.getName());
		// Business process
		List<MerchantsOrgRole> roleList = orgRoleService.findByGroupId(roleId);
		if (roleList != null && roleList.size() > 0) {
			return true;
		} else {
			List<Integer> userIdList = userRoleService.findUserIdsByGroupId(roleId);
			if (userIdList != null && userIdList.size() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Query {@code MerchantsRole} by id
	 * 
	 * @param id
	 * @return {@code MerchantsRole}
	 */
	public MerchantsRole findRoleById(Integer id) {
		// Initialize the required services
		MerchantsRoleService /* Role services */ roleService = (MerchantsRoleService) services
				.get(MerchantsRoleService.class.getName());
		// Business process
		return roleService.find(id);
	}

	/**
	 * Query {@code MerchantsRoleAction} action id list by id
	 * 
	 * @param roleId
	 * @return {@code MerchantsRoleAction}
	 */
	public List<Integer> findRoleActionIdByRoleId(Integer roleId) {
		// Initialize the required services
		MerchantsRoleActionService /* Role services */ roleActionService = (MerchantsRoleActionService) services
				.get(MerchantsRoleActionService.class.getName());
		// Business process
		return roleActionService.findActionIdsByGroupIdAndType(roleId, EActionUserType.Admin.ordinal());
	}

	/**
	 * Save merchants role
	 * 
	 * @param merchantsRole
	 *            {@code MerchantsRole}
	 * @return {@code int} if {@code int }>0 true else false
	 */
	public int saveMerchantsRole(MerchantsRole merchantsRole) {
		// Initialize the required services
		MerchantsRoleService /* Role services */ roleService = (MerchantsRoleService) services
				.get(MerchantsRoleService.class.getName());
		MerchantsRoleActionService/* Role action services */ roleActionService = (MerchantsRoleActionService) services
				.get(MerchantsRoleActionService.class.getName());
		// Business process
		List<Integer> newRoleActionIdList = new ArrayList<Integer>();
		String[] newRoleActionIdArray = split(CommonConstants.COMMA_SEPARATOR, merchantsRole.getTreeSelId());
		if (newRoleActionIdArray != null && newRoleActionIdArray.length > 0) {
			for (String /* The current role all action id */ id : newRoleActionIdArray) {
				newRoleActionIdList.add(Integer.valueOf(id));
			}
		}
		int roleId = (null != merchantsRole.getId() ? merchantsRole.getId().intValue() : 0);
		if (/* Update role */merchantsRole.getId() != null) {
			List<Integer> /* Old role action */ oldRoleActionList = roleActionService
					.findActionIdsByGroupIdAndType(merchantsRole.getId(), EActionUserType.Admin.ordinal());
			if (/* Delete old role action */oldRoleActionList != null && oldRoleActionList.size() > 0) {
				if (roleActionService.deleteByGroupId(merchantsRole.getId()) > 0) {
					roleService.update(merchantsRole);
				}
			}
		} else {
			int newRoleId = roleService.insert(merchantsRole);
			roleId = newRoleId;
		}
		if (/* Write new role all action id */newRoleActionIdList.size() > 0) {
			List<MerchantsRoleAction> newRoleAction = new ArrayList<MerchantsRoleAction>();
			for (Integer actionId : newRoleActionIdList) {
				MerchantsRoleAction roleAction = new MerchantsRoleAction();
				roleAction.setActionId(actionId);
				roleAction.setGroupId(roleId);
				roleAction.setType(EActionUserType.Admin.ordinal());
				newRoleAction.add(roleAction);
			}
			return roleActionService.batchInsert(newRoleAction);
		}
		return 0;
	}

	/**
	 * Query all administrator action
	 * 
	 * @param type
	 * @author {@link DefaultTreeNode}
	 */
	public DefaultTreeNode findActionTreeByType(EActionUserType type) {
		// Initialize the required services
		MerchantsActionService actionService = (MerchantsActionService) services
				.get(MerchantsActionService.class.getName());
		// Business process
		List<MerchantsAction> list = actionService.findByType(EActionUserType.Admin.ordinal());
		return DefaultTreeNode.parseTree(list);
	}

	/**
	 * Query all role under the specified license and paging the result
	 * 
	 * @param name
	 * @param firstSize
	 * @param size
	 * @return {@link List}
	 */
	public ListResult<MerchantsRole> findRoleByPage(String name, String license, Integer firstSize, Integer size) {
		// Initialize the required services
		MerchantsRoleService roleService = (MerchantsRoleService) services.get(MerchantsRoleService.class.getName());
		// Business process
		ListResult<MerchantsRole> result = new ListResult<MerchantsRole>();
		if (!isNullOrEmpty(name)) {
			result.setData(roleService.findPageByName(name, license, firstSize, size));
			result.setTotal(roleService.countByName(name, license));
		} else {
			result.setData(roleService.findPageByLicense(license, firstSize, size));
			result.setTotal(roleService.findByLicense(license).size());
		}
		return result;
	}

	/**
	 * Save install first merchants
	 * 
	 * @param merchants
	 *            {@code Merchants}
	 * @return {@code int} if {@code int }>0 true else false
	 */
	public int saveInstall(Merchants merchants) {
		if (!isNullOrEmpty(merchants.getLicense())) {
			// Initialize the required services
			MerchantsRoleService /* Role services */ roleService = (MerchantsRoleService) services
					.get(MerchantsRoleService.class.getName());
			MerchantsOrgService /* Organization services */ orgService = (MerchantsOrgService) services
					.get(MerchantsOrgService.class.getName());
			MerchantsOrgRoleService /* Organization role services */ orgRoleService = (MerchantsOrgRoleService) services
					.get(MerchantsOrgRoleService.class.getName());
			MerchantsUserService /* Organization user services */ merchantsUserService = (MerchantsUserService) services
					.get(MerchantsUserService.class.getName());
			MerchantsUserRoleService/* Organization user role services */ userRoleService = (MerchantsUserRoleService) services
					.get(MerchantsUserRoleService.class.getName());
			// business process
			String newLicense = merchants.getLicense();
			List<MerchantsOrg> /* The new license is using */ checkLicense = orgService.findByLicense(newLicense);
			if (/* When install first,make sure license is legal */checkLicense != null && checkLicense.size() > 0) {
				return 0;
			}
			String roleCodeAndOrgCode = Chineses.lower(merchants.getOrgName());
			MerchantsRole /* First create administrator role */ firstAdminRole = new MerchantsRole(newLicense, "全部权限",
					roleCodeAndOrgCode, true, true, true, EPageType.SysPage, 0, null, 0, "全部权限", 1, new Date(), 1,
					new Date());
			int /**/ newAdminRoleId = roleService.insert(firstAdminRole);
			if (/**/newAdminRoleId > 0) {
				MerchantsOrg /* First create organization */ firstOrg = new MerchantsOrg(false, newLicense,
						EOrgType.valueOf(merchants.getOrgType()), merchants.getOrgName(), roleCodeAndOrgCode, 0, 1,
						EOrgStatus.Normal, /* Not inherit admin role */false, 1, new Date(), 1, new Date());
				int newOrgId = orgService.insert(firstOrg);
				if (/**/newOrgId > 0) {
					MerchantsOrgRole /* First create organization role */ newOrgRole = new MerchantsOrgRole(
							/**/newOrgId, /**/newAdminRoleId);
					if (orgRoleService.insert(newOrgRole) > 0) {
						MerchantsUser /* First create organization admin user */ firstOrgUser = new MerchantsUser(
								EActionUserType.Admin, 0, merchants.getAccount(), merchants.getRealname(),
								merchants.getRealname(), md5(merchants.getPassword()), /**/newOrgId,
								EGender.values()[merchants.getSex()], 1, merchants.getIp(), null, merchants.getPhone(),
								merchants.getEmail(), null, "1", EOrgStatus.Normal, 1, new Date(), 1, new Date());
						int newUserId = merchantsUserService.insert(firstOrgUser);
						if (/**/newUserId > 0) {
							MerchantsUserRole /* First create admin user role */ userRole = new MerchantsUserRole(
									/**/newAdminRoleId, /**/newUserId);
							List<MerchantsUserRole> newUserRoleList = new ArrayList<MerchantsUserRole>();
							newUserRoleList.add(userRole);
							return userRoleService.batchInsert(newUserRoleList);
						}
					}
				}
			}
		}
		return 0;
	}

	/**
	 * Save merchants user
	 * 
	 * @param merchantsUser
	 *            {@code MerchantsUser}
	 * @return {@code int} if {@code int }>0 true else false
	 */
	public int saveMerchantsUser(MerchantsUser merchantsUser) {
		// Initialize the required services
		MerchantsOrgRoleService orgRoleService = (MerchantsOrgRoleService) services
				.get(MerchantsOrgRoleService.class.getName());
		MerchantsUserService merchantsUserService = (MerchantsUserService) services
				.get(MerchantsUserService.class.getName());
		MerchantsUserRoleService userRoleService = (MerchantsUserRoleService) services
				.get(MerchantsUserRoleService.class.getName());
		// business process
		Set<Integer> newRoleSet = new TreeSet<Integer>();
		String[] newRoleArray = split(CommonConstants.COMMA_SEPARATOR, merchantsUser.getTreeSelId());
		if (/* The new role list */newRoleArray != null && newRoleArray.length > 0) {
			for (String newRoleId : newRoleArray) {
				newRoleSet.add(Integer.valueOf(newRoleId));
			}
		}
		MerchantsOrg merchantsOrg = findMerchantsOrgById(merchantsUser.getOrgId());
		if (/* User inherit organization role */merchantsOrg.isInherit()) {
			List<Integer> rogRoleList = orgRoleService.findGroupIdsByOrgId(merchantsUser.getOrgId());
			if (rogRoleList != null && rogRoleList.size() > 0) {
				for (Integer roleId : rogRoleList) {
					newRoleSet.add(roleId);
				}
			}
		}
		List<MerchantsUserRole> userRoleList = new ArrayList<MerchantsUserRole>();
		if (/* Update merchants user */merchantsUser.getId() != null) {
			if (/* Delete old role */userRoleService.deleteByUserId(merchantsUser.getId()) > 0) {
				if (/* Update */merchantsUserService.update(merchantsUser) > 0) {
					if (newRoleSet.size() > 0) {
						for (Integer roleId : newRoleSet) {
							MerchantsUserRole userRole = new MerchantsUserRole();
							userRole.setGroupId(roleId);
							userRole.setUserId(merchantsUser.getId());
							userRoleList.add(userRole);
						}
						return userRoleService.batchInsert(userRoleList);
					}
				}
			}
		} /* New merchants user */else {
			int newUserId = merchantsUserService.insert(merchantsUser);
			if (newUserId > 0) {
				if (newRoleSet.size() > 0) {
					for (Integer roleId : newRoleSet) {
						MerchantsUserRole userRole = new MerchantsUserRole();
						userRole.setGroupId(roleId);
						userRole.setUserId(newUserId);
						userRoleList.add(userRole);
					}
					return userRoleService.batchInsert(userRoleList);
				}
			}
		}
		return 0;
	}

	/**
	 * Delete Merchants organization
	 * 
	 * @param orgId
	 * @return {@code int} if {@code int }>0 true else false
	 */
	public int deleteMerchantsOrg(Integer orgId) {
		if (/* Delete organization */((MerchantsOrgService) services.get(MerchantsOrgService.class.getName()))
				.delete(orgId) > 0) {
			return (/* Delete organization role */(MerchantsOrgRoleService) services
					.get(MerchantsOrgRoleService.class.getName())).deleteByOrgId(orgId);
		}
		return 0;
	}

	/**
	 * Save Merchants organization
	 * 
	 * @param merchantsOrg
	 *            {@code MerchantsOrg}
	 * @return {@code int} if {@code int }>0 true else false
	 */
	public int saveMerchantsOrg(MerchantsOrg merchantsOrg) {
		// Initialize the required services
		MerchantsOrgService orgService = (MerchantsOrgService) services.get(MerchantsOrgService.class.getName());
		MerchantsOrgRoleService orgRoleService = (MerchantsOrgRoleService) services
				.get(MerchantsOrgRoleService.class.getName());
		MerchantsUserService merchantsUserService = (MerchantsUserService) services
				.get(MerchantsUserService.class.getName());
		MerchantsUserRoleService userRoleService = (MerchantsUserRoleService) services
				.get(MerchantsUserRoleService.class.getName());
		// business process
		Set<Integer> newRoleSet = new TreeSet<Integer>();
		String[] newRoleArray = split(CommonConstants.COMMA_SEPARATOR, merchantsOrg.getTreeSelId());
		if (/* The new role list */newRoleArray != null && newRoleArray.length > 0) {
			for (String newRoleId : newRoleArray) {
				newRoleSet.add(Integer.valueOf(newRoleId));
			}
		}
		if (/* The parent role list */merchantsOrg.isInherit() && merchantsOrg.getParentId().intValue() > 0) {
			List<Integer> parentRoleList = orgRoleService.findGroupIdsByOrgId(merchantsOrg.getParentId());
			if (parentRoleList != null && parentRoleList.size() > 0) {
				for (Integer roleId : parentRoleList) {
					newRoleSet.add(roleId);
				}
			}
		}
		if (/* Update the organization */merchantsOrg.getId() != null) {
			int size = countByOrgId(merchantsOrg.getId());
			if (/* Whether the organization has users */size > 0) {
				List<MerchantsUser> /* The current organization users */ userList = merchantsUserService
						.findAllByOrgId(merchantsOrg.getId(), EActionUserType.Admin);
				List<Integer> /* Roles Shared by users and organizations */ shareRoleIdUserAndOrg = merchantsUserService
						.findHaveRoleIdByUserAndOrg(merchantsOrg.getId());
				for/* Delete old user role by organization */ (MerchantsUser merchantsUser : userList) {
					for (Integer roleId : shareRoleIdUserAndOrg) {
						userRoleService.deleteByUserIdAndGroupId(merchantsUser.getId(), roleId);
					}
				}
				List<MerchantsUserRole> newOrgUserRoleList = new ArrayList<MerchantsUserRole>();
				for/* Add new user role by organization */ (MerchantsUser merchantsUser : userList) {
					if (/* The new organization role */newRoleSet.size() > 0) {
						for (Integer roleId : newRoleSet) {
							MerchantsUserRole userRole = new MerchantsUserRole();
							userRole.setUserId(merchantsUser.getId());
							userRole.setGroupId(roleId);
							newOrgUserRoleList.add(userRole);
						}
					}
				}
				userRoleService.batchInsert(newOrgUserRoleList);
			}
			if (/* Delete organization old role */orgRoleService.deleteByOrgId(merchantsOrg.getId()) > 0) {
				List<MerchantsOrgRole> newOrgRoleList = new ArrayList<MerchantsOrgRole>();
				if (/* The new organization role */newRoleSet.size() > 0) {
					for (Integer roleId : newRoleSet) {
						MerchantsOrgRole orgRole = new MerchantsOrgRole();
						orgRole.setOrgId(merchantsOrg.getId());
						orgRole.setGroupId(roleId);
						newOrgRoleList.add(orgRole);
					}
				}
				orgRoleService.batchInsert(newOrgRoleList);
			}
			return orgService.update(merchantsOrg);
		} /* The new organization */else {
			int newOrgId = orgService.insert(merchantsOrg);
			if (newOrgId > 0) {
				if (/* The new organization role */newRoleSet.size() > 0) {
					List<MerchantsOrgRole> orgRoleList = new ArrayList<MerchantsOrgRole>();
					for (Integer roleId : newRoleSet) {
						MerchantsOrgRole orgRole = new MerchantsOrgRole();
						orgRole.setGroupId(roleId);
						orgRole.setOrgId(newOrgId);
						orgRoleList.add(orgRole);
					}
					return orgRoleService.batchInsert(orgRoleList);
				}
			}
		}
		return 0;
	}

	/**
	 * Query subagencies of the current organization
	 * 
	 * @param parentId
	 *            parent organization
	 * @return {@link List}
	 */
	public List<MerchantsOrg> findMerchantsOrgByParentId(Integer parentId) {
		MerchantsOrgService orgService = (MerchantsOrgService) services.get(MerchantsOrgService.class.getName());
		String license = orgService.find(parentId).getLicense();
		return orgService.findByParentId(parentId, license);
	}

	/**
	 * Find the current organization has admin user number
	 * 
	 * @param orgId
	 * @return int
	 */
	public int countByOrgId(Integer orgId) {
		return ((MerchantsUserService) services.get(MerchantsUserService.class.getName())).countByOrgId(orgId,
				EActionUserType.Admin);
	}

	/**
	 * Find Merchants user role id list by ID
	 * 
	 * @param id
	 * @return {@code List}
	 */
	public List<Integer> findMerchantsUserRoleById(Integer id) {
		return ((MerchantsUserRoleService) services.get(MerchantsUserRoleService.class.getName()))
				.findGroupIdsByUserId(id);
	}

	/**
	 * Find Merchants organization role id list by ID
	 * 
	 * @param id
	 * @return {@code List}
	 */
	public List<Integer> findMerchantsOrgRoleById(Integer id) {
		return ((MerchantsOrgRoleService) services.get(MerchantsOrgRoleService.class.getName()))
				.findGroupIdsByOrgId(id);
	}

	/**
	 * Find Merchants organization by ID
	 * 
	 * @param id
	 * @return {@code MerchantsOrg}
	 */
	public MerchantsOrg findMerchantsOrgById(Integer id) {
		return ((MerchantsOrgService) services.get(MerchantsOrgService.class.getName())).find(id);
	}

	/**
	 * Find all role of the organization
	 * 
	 * @param userId
	 *            current login user ID
	 * @return {@link List}
	 */
	public List<Node<Integer, String>> findRoleByLicense(String license) {
		List<MerchantsRole> list = ((MerchantsRoleService) services.get(MerchantsRoleService.class.getName()))
				.findByLicense(license);
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

	/**
	 * Query all users under the specified organization and paging the result
	 * 
	 * @param orgId
	 * @param name
	 * @param firstSize
	 * @param size
	 * @return {@link List}
	 */
	public ListResult<MerchantsUser> findOrgUserByPage(Integer orgId, String name, Integer firstSize, Integer size) {
		// Initialize the required services
		MerchantsUserService userService = (MerchantsUserService) services.get(MerchantsUserService.class.getName());
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

	/**
	 * Queries all institutional trees under the specified user
	 * 
	 * @param userId
	 * @return {@link DefaultTreeNode}
	 */
	public DefaultTreeNode findUserOrgTree(Integer userId) {
		// Initialize the required services
		MerchantsOrgService orgService = (MerchantsOrgService) services.get(MerchantsOrgService.class.getName());
		// business process
		String license = orgService.findOrgLicenseByUserId(userId);
		List<MerchantsOrg> list = /* Super Administrator */ (userId.intValue() == CommonConstants.SUPER_ADMIN)
				? orgService.findAll(Boolean.TRUE) : orgService.findByLicense(license);
		return DefaultTreeNode.parseTree(list);
	}

	/**
	 * Query users by username and password
	 * 
	 * @param name
	 * @param password
	 * @return {@link MerchantsUser}
	 */
	public MerchantsUser findByNameAndPass(String name, String password) {
		// business process
		return ((MerchantsUserService) services.get(MerchantsUserService.class.getName())).findByNamePassword(name,
				password, EActionUserType.Admin);
	}

	/**
	 * Query users by id
	 * 
	 * @param id
	 * @return {@link MerchantsUser}
	 */
	public MerchantsUser find(Integer id) {
		// business process
		return ((MerchantsUserService) services.get(MerchantsUserService.class.getName())).find(id);
	}

	/**
	 * Queries the specified user permission tree and assigns values to the user
	 * 
	 * @param userVO
	 *            {@code MerchantsUserVO}
	 * @return {@link MerchantsUserVO}
	 */
	public MerchantsUserVO findMenuTreeByUser(MerchantsUserVO userVO) {
		// Initialize the required services
		MerchantsActionService actionService = (MerchantsActionService) services
				.get(MerchantsActionService.class.getName());
		MerchantsUserRoleService userRoleService = (MerchantsUserRoleService) services
				.get(MerchantsUserRoleService.class.getName());
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

	/**
	 * Query user license
	 * <p>
	 * the license is the license of the user's organization.
	 * 
	 * @param userId
	 * @return {@link String}
	 */
	public String findLicenseByUserId(Integer userId) {
		// business process
		return ((MerchantsOrgService) services.get(MerchantsOrgService.class.getName())).findOrgLicenseByUserId(userId);
	}

	/**
	 * User login logic processing.
	 * <p>
	 * Token and refresh token are generated when the user logs in successfully.
	 * <p>
	 * When the token expires and the refresh token does not, refresh and
	 * produce a new token.
	 * <p>
	 * When the token and refresh token expires, refresh and produce a new token
	 * and new refresh token.
	 * 
	 * @param userId
	 * @param appKey
	 * @param tokenType
	 *            Whether token expires
	 * @param refreshType
	 *            Whether refreshType token expires
	 * @return {@link MerchantsLoginToken}
	 */
	public MerchantsLoginToken token(Integer userId, String appKey, boolean tokenType, boolean refreshType) {
		MerchantsLoginToken loginToken = null;
		// Initialize the required services
		MerchantsLoginTokenService loginTokenService = (MerchantsLoginTokenService) services
				.get(MerchantsLoginTokenService.class.getName());
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
			String token = md5(payload + format(loginTime, "yyyyMMddHHmmssSSS"));
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
				String token = md5(payload + format(loginTime, "yyyyMMddHHmmssSSS"));
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
					String token = md5(loginToken.getRefreshToken() + format(loginTime, "yyyyMMddHHmmssSSS"));
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
