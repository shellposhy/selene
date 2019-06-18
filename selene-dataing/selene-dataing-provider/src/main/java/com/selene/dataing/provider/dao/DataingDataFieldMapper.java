package com.selene.dataing.provider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.selene.dataing.model.DataingDataField;

/**
 * Completes the mapping between the {@link DataingDataField} Object and the XML
 * 
 * @see DataingDataField
 * @author shaobo shih
 * @version 1.0
 */
public interface DataingDataFieldMapper {

	public List<DataingDataField> findByPage(@Param("name") String name, @Param("first") Integer first,
			@Param("size") Integer size);

	public int count(@Param("name") String name);

	public int insert(DataingDataField dataField);
}
