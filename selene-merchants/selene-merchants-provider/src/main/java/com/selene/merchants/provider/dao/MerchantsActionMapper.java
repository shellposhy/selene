package com.selene.merchants.provider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.selene.merchants.model.MerchantsAction;

/**
 * Completes the mapping between the {@link MerchantsAction} Object and the XML
 * 
 * @see DataingDataField
 * @author shaobo shih
 * @version 1.0
 */
public interface MerchantsActionMapper {
	public List<MerchantsAction> findByType(@Param("type") Integer type);

	public List<MerchantsAction> findByCode(@Param("code") String code);

	public List<MerchantsAction> findByUserIdAndType(@Param("userId") Integer userId, @Param("type") Integer type);

	public List<MerchantsAction> findByIds(List<Integer> list);
}
