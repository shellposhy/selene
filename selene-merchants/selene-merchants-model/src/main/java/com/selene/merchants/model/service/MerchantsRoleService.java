package com.selene.merchants.model.service;

import java.util.List;

import com.selene.merchants.model.MerchantsRole;

/**
 * The {@code MerchantsRole} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface MerchantsRoleService {
	public int insert(MerchantsRole role);

	public MerchantsRole find(Integer id);

	public List<MerchantsRole> findByName(String name, String license);

	public List<MerchantsRole> findByCode(String code, String license);

	public List<MerchantsRole> findByUserId(Integer userId, String license);

	public List<MerchantsRole> findPageByName(String name, String license, Integer first, Integer size);

	public int countByName(String name, String license);

	public int batchDelete(List<Integer> list);

	public int delete(Integer id);

	public int update(MerchantsRole role);
}
