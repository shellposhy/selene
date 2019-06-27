package com.selene.merchants.model.service;

import java.util.List;

import com.selene.common.tree.DefaultTreeNode;
import com.selene.merchants.model.MerchantsAction;
import com.selene.merchants.model.MerchantsAction.MerchantsActionPropertySetter;

/**
 * The {@code MerchantsAction} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface MerchantsActionService {
	public List<MerchantsAction> findByType(Integer type);

	public List<MerchantsAction> findByCode(String code);

	public List<MerchantsAction> findByUserIdAndType(Integer userId, Integer type);

	public List<MerchantsAction> findByIds(List<Integer> list);

	public String findMenuTreeByUser(List<MerchantsAction> list, Boolean allAction);

	public <T extends DefaultTreeNode> T findTreeByUser(Class<T> clazz, List<MerchantsAction> list, Boolean allAction,
			MerchantsActionPropertySetter<T> propertySetter);
}
