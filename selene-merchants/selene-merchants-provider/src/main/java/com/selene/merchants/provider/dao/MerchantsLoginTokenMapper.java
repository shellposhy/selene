package com.selene.merchants.provider.dao;

import org.apache.ibatis.annotations.Param;

import com.selene.merchants.model.MerchantsLoginToken;

/**
 * Completes the mapping between the {@link MerchantsLoginToken} Object and the
 * XML
 * 
 * @see DataingDataField
 * @author shaobo shih
 * @version 1.0
 */
public interface MerchantsLoginTokenMapper {
	public int insert(MerchantsLoginToken merchantsLoginToken);

	public MerchantsLoginToken find(@Param("id") Integer id);

	public MerchantsLoginToken findByUserId(@Param("userId") Integer userId);

	public int update(MerchantsLoginToken merchantsLoginToken);

}
