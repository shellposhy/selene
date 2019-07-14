package com.selene.merchants.provider.impl;

import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.merchants.model.MerchantsRoleAction;
import com.selene.merchants.model.service.MerchantsRoleActionService;
import com.selene.merchants.provider.dao.MerchantsRoleActionMapper;

/**
 * The {@code MerchantsRoleActionService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see MerchantsRoleActionService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(MerchantsRoleActionService.class)
public class MerchantsRoleActionServiceImpl implements MerchantsRoleActionService {
	@Resource
	private MerchantsRoleActionMapper merchantsRoleActionMapper;

	@Override
	public int batchInsert(List<MerchantsRoleAction> list) {
		return merchantsRoleActionMapper.batchInsert(list);
	}

	@Override
	public List<Integer> findActionIdsByGroupIdAndType(Integer groupId, Integer type) {
		return merchantsRoleActionMapper.findActionIdsByGroupIdAndType(groupId, type);
	}

	@Override
	public int deleteByGroupId(Integer groupId) {
		return merchantsRoleActionMapper.deleteByGroupId(groupId);
	}

}
