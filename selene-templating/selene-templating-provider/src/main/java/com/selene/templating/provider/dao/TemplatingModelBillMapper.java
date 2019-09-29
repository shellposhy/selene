package com.selene.templating.provider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.selene.templating.model.TemplatingModelBill;

/**
 * Completes the mapping between the {@link TemplatingModelBill} Object and the
 * XML
 * 
 * @see DataingDataField
 * @author shaobo shih
 * @version 1.0
 */
public interface TemplatingModelBillMapper {

	public TemplatingModelBill find(@Param("id") Integer id);

	public List<TemplatingModelBill> findByParentId(@Param("license") String license,
			@Param("parentId") Integer parentId);

	public List<TemplatingModelBill> findByLicense(@Param("license") String license);

	public int insert(TemplatingModelBill modelBill);

	public int update(TemplatingModelBill modelBill);

	public int delete(@Param("id") Integer id);
}
