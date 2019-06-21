package com.selene.merchants.provider.impl;

import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.merchants.model.MerchantsAction;
import com.selene.merchants.model.service.MerchantsActionService;
import com.selene.merchants.provider.dao.MerchantsActionMapper;

/**
 * The {@code MerchantsActionService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see MerchantsActionService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(MerchantsActionService.class)
public class MerchantsActionServiceImpl implements MerchantsActionService {
	@Resource
	private MerchantsActionMapper merchantsActionMapper;

	@Override
	public List<MerchantsAction> findByType(Integer type) {
		return merchantsActionMapper.findByType(type);
	}

	@Override
	public List<MerchantsAction> findByCode(String code) {
		return merchantsActionMapper.findByCode(code);
	}

	@Override
	public List<MerchantsAction> findByIds(List<Integer> list) {
		return merchantsActionMapper.findByIds(list);
	}

}
