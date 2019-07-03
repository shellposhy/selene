package com.selene.viewing.admin.vo.merchants;

import java.util.Date;

import com.selene.merchants.model.MerchantsUser;

/**
 * <b>Selene viewing module<b>
 * <p>
 * view object for merchants user
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class MerchantsUserVO {
	// base info
	private Integer id;
	private Integer type;
	private int userType;
	private String name;
	private String realName;
	private String nickName;
	private Integer orgId;
	private String sex;
	private String phoneNumber;
	private String email;
	private String pic;
	private Integer status;
	// extend info
	private String actionTree;
	private boolean allAdmin;
	private String license;
	// token info
	private Date loginTime;
	private String redisKey;
	private String token;
	private String refreshToken;

	public MerchantsUserVO() {
	}

	public MerchantsUserVO(MerchantsUser merchantsUser) {
		if (merchantsUser != null) {
			this.id = merchantsUser.getId();
			this.type = merchantsUser.getType().ordinal();
			this.userType = merchantsUser.getUserType();
			this.name = merchantsUser.getName();
			this.realName = merchantsUser.getRealName();
			this.nickName = merchantsUser.getNickName();
			this.orgId = merchantsUser.getOrgId();
			this.sex = merchantsUser.getSex().getValue();
			this.phoneNumber = merchantsUser.getPhoneNumber();
			this.email = merchantsUser.getEmail();
			this.pic = merchantsUser.getPic();
			this.status = merchantsUser.getStatus().ordinal();
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getActionTree() {
		return actionTree;
	}

	public void setActionTree(String actionTree) {
		this.actionTree = actionTree;
	}

	public boolean isAllAdmin() {
		return allAdmin;
	}

	public void setAllAdmin(boolean allAdmin) {
		this.allAdmin = allAdmin;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
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

}
