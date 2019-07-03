package com.selene.merchants.provider.impl;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.merchants.model.MerchantsLoginToken;
import com.selene.merchants.model.service.MerchantsLoginTokenService;
import com.selene.merchants.model.service.MerchantsOrgService;
import com.selene.merchants.provider.dao.MerchantsLoginTokenMapper;

/**
 * The {@code MerchantsLoginTokenService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see MerchantsOrgService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(MerchantsLoginTokenService.class)
public class MerchantsLoginTokenServiceImpl implements MerchantsLoginTokenService {
	@Resource
	private MerchantsLoginTokenMapper loginTokenMapper;

	@Override
	public int insert(MerchantsLoginToken merchantsLoginToken) {
		return loginTokenMapper.insert(merchantsLoginToken);
	}

	@Override
	public MerchantsLoginToken find(Integer id) {
		return loginTokenMapper.find(id);
	}

	@Override
	public MerchantsLoginToken findByUserId(Integer userId) {
		return loginTokenMapper.findByUserId(userId);
	}

	@Override
	public int update(MerchantsLoginToken merchantsLoginToken) {
		return loginTokenMapper.update(merchantsLoginToken);
	}

}
