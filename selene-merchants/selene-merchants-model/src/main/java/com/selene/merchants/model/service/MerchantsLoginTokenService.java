package com.selene.merchants.model.service;

import com.selene.merchants.model.MerchantsLoginToken;

/**
 * The {@code MerchantsLoginToken} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface MerchantsLoginTokenService {
	public int insert(MerchantsLoginToken merchantsLoginToken);

	public MerchantsLoginToken find(Integer id);

	public MerchantsLoginToken findByUserId(Integer userId);

	public int update(MerchantsLoginToken merchantsLoginToken);
}
