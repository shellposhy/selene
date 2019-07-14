package com.selene.merchants.model.service;

import java.util.List;

import com.selene.merchants.model.MerchantsRoleAction;

/**
 * The {@code MerchantsRoleAction} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface MerchantsRoleActionService {
	public int batchInsert(List<MerchantsRoleAction> list);

	public List<Integer> findActionIdsByGroupIdAndType(Integer groupId, Integer type);

	public int deleteByGroupId(Integer groupId);
}
