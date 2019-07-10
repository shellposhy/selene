package com.selene.merchants.model.service;

import java.util.List;

import com.selene.merchants.model.MerchantsUser;
import com.selene.merchants.model.enums.EActionUserType;

/**
 * The {@code MerchantsUser} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface MerchantsUserService {
	public int insert(MerchantsUser user);

	public MerchantsUser find(Integer id);

	public MerchantsUser findByName(String name, EActionUserType type);

	public MerchantsUser findByNamePassword(String name, String pass, EActionUserType type);

	public List<MerchantsUser> findByOrgId(Integer orgId, EActionUserType type, Integer firstSize, Integer size);

	public int countByOrgId(Integer orgId, EActionUserType type);

	public List<MerchantsUser> findByOrgAndName(Integer orgId, String name, EActionUserType type, Integer firstSize,
			Integer size);

	public int countByOrgAndName(Integer orgId, String name, EActionUserType type);

	public int update(MerchantsUser user);

	public int petrify(MerchantsUser user);
}
