package com.selene.merchants.model;

/**
 * <b>Selene merchants module<b>
 * <p>
 * Mapping object for merchants install
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class Merchants {
	private String orgName;
	private int orgType;
	private String license;
	private String account;
	private String realname;
	private int sex;
	private String phoneNumber;
	private String accountPassword;
	private String ipAddress;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public int getOrgType() {
		return orgType;
	}

	public void setOrgType(int orgType) {
		this.orgType = orgType;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAccountPassword() {
		return accountPassword;
	}

	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
