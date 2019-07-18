package com.selene.dataing.model.service;

import java.util.List;

import com.selene.dataing.model.DataingDataModel;

/**
 * The {@code DataingDataModel} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface DataingDataModelService {
	public int insert(DataingDataModel dataModel);

	public DataingDataModel find(Integer id);

	public List<DataingDataModel> findByType(Integer modelType, String license);

	public List<DataingDataModel> findByPage(String license, Integer first, Integer size);

	public int count(String license);

	public List<DataingDataModel> findNameByPage(String license, String name, Integer first, Integer size);

	public int countByName(String license, String name);

	public int update(DataingDataModel dataModel);

	public int delete(Integer id);
}
