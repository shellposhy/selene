package com.selene.merchants.provider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.selene.merchants.model.MerchantsOrg;

/**
 * Completes the mapping between the {@link MerchantsOrg} Object and the XML
 * 
 * @see DataingDataField
 * @author shaobo shih
 * @version 1.0
 */
public interface MerchantsOrgMapper {
	public int insert(MerchantsOrg org);

	public MerchantsOrg find(@Param("id") Integer id);

	public List<MerchantsOrg> findByParentId(@Param("parentId") Integer parentId);

	public List<MerchantsOrg> findByCodeAndParentId(@Param("parentId") Integer parentId, @Param("code") String code);

	public int update(MerchantsOrg org);

	public int delete(@Param("id") Integer id);
}
