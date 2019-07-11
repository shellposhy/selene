package com.selene.merchants.model;

import com.selene.common.BaseModel;
import com.selene.merchants.model.enums.EActionUserType;
import com.selene.merchants.model.enums.EOrgStatus;

import cn.com.lemon.common.enums.EGender;

/**
 * <b>Selene merchants module<b>
 * <p>
 * Mapping object for merchants user
 * 
 * @see BaseModel
 * @author shaobo shih
 * @version 1.0
 */
public class MerchantsUser extends BaseModel {
	private static final long serialVersionUID = 7771717553120210729L;
	private EActionUserType type;
	private int userType;
	private String name;
	private String realName;
	private String nickName;
	private String userPassword;
	private Integer orgId;
	private EGender sex;
	private Integer orderId;
	private String ipAddress;
	private String idCard;
	private String phoneNumber;
	private String email;
	private String position;
	private String pic;
	private EOrgStatus status;
	// Expand field for org role list string
	private String treeSelId;
	private String oldPassword;

	public EActionUserType getType() {
		return type;
	}

	public void setType(EActionUserType type) {
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

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public EGender getSex() {
		return sex;
	}

	public void setSex(EGender sex) {
		this.sex = sex;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
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

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public EOrgStatus getStatus() {
		return status;
	}

	public void setStatus(EOrgStatus status) {
		this.status = status;
	}

	public String getTreeSelId() {
		return treeSelId;
	}

	public void setTreeSelId(String treeSelId) {
		this.treeSelId = treeSelId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

}
