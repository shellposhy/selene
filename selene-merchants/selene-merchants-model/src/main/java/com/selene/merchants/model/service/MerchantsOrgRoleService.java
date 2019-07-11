package com.selene.merchants.model.service;

import java.util.List;

import com.selene.merchants.model.MerchantsOrgRole;

/**
 * The {@code MerchantsOrgRole} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface MerchantsOrgRoleService {
	public int insert(MerchantsOrgRole orgRole);

	public int batchInsert(List<MerchantsOrgRole> list);

	public List<Integer> findGroupIdsByOrgId(Integer orgId);

	public int deleteByOrgId(Integer orgId);

	public int deleteByGroupId(Integer groupId);
}
