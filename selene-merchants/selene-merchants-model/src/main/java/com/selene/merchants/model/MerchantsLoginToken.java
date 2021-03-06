package com.selene.merchants.model;

import java.util.Date;

import com.selene.common.BaseModel;

/**
 * <b>Selene merchants module<b>
 * <p>
 * Mapping object for merchants login token
 * 
 * @see BaseModel
 * @author shaobo shih
 * @version 1.0
 */
public class MerchantsLoginToken extends BaseModel {
	private static final long serialVersionUID = 6516795800459896209L;
	private Integer userId;
	private Date loginTime;
	private String redisKey;
	private String token;
	private String refreshToken;
	private String secretKey;
	private String jwt;

	public MerchantsLoginToken() {
	}

	public MerchantsLoginToken(Integer userId, Date loginTime, String redisKey, String token, String refreshToken,
			String secretKey, String jwt) {
		this.userId = userId;
		this.loginTime = loginTime;
		this.redisKey = redisKey;
		this.token = token;
		this.refreshToken = refreshToken;
		this.secretKey = secretKey;
		this.jwt = jwt;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getRedisKey() {
		return redisKey;
	}

	public void setRedisKey(String redisKey) {
		this.redisKey = redisKey;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

}
