package com.selene.merchants.model.service;

import java.util.List;

import com.selene.merchants.model.MerchantsUser;

/**
 * The {@code MerchantsUser} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface MerchantsUserService {
	public int insert(MerchantsUser user);

	public MerchantsUser find(Integer id);

	public MerchantsUser findByName(String name);

	public MerchantsUser findByNamePassword(String name, String pass);

	public List<MerchantsUser> findByOrgId(Integer orgId);

	public int countByOrgAndName(Integer orgId, String name);

	public int findByOrgAndName(Integer orgId, String name, Integer firstSize, Integer size);

	public int update(MerchantsUser user);

	public int petrify(MerchantsUser user);
}
