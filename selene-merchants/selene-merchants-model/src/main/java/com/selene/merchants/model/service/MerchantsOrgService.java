package com.selene.merchants.model.service;

import java.util.List;

import com.selene.merchants.model.MerchantsOrg;

/**
 * The {@code MerchantsOrg} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface MerchantsOrgService {
	public int insert(MerchantsOrg org);

	public MerchantsOrg find(Integer id);

	public List<MerchantsOrg> findByParentId(Integer parentId);

	public List<MerchantsOrg> findByCodeAndParentId(Integer parentId, String code);

	public int update(MerchantsOrg org);

	public int delete(Integer id);
}
