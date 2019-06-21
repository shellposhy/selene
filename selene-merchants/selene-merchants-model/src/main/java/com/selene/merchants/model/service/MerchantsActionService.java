package com.selene.merchants.model.service;

import java.util.List;

import com.selene.merchants.model.MerchantsAction;

/**
 * The {@code MerchantsAction} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface MerchantsActionService {
	public List<MerchantsAction> findByType(Integer type);

	public List<MerchantsAction> findByCode(String code);

	public List<MerchantsAction> findByIds(List<Integer> list);
}
