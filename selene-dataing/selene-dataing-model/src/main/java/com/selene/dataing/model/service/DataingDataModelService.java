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

	public List<DataingDataModel> findByType(Integer modelType);

	public List<DataingDataModel> findByPage(Integer first, Integer size);

	public int count(Boolean flag);

	public List<DataingDataModel> findNameByPage(String name, Integer first, Integer size);

	public int countByName(String name);

	public int update(DataingDataModel dataModel);

	public int delete(Integer id);
}
