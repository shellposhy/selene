package com.selene.merchants.model;

import java.util.Date;

import com.selene.common.BaseModel;
import com.selene.merchants.model.enums.EPageType;

/**
 * <b>Selene merchants module<b>
 * <p>
 * Mapping object for merchants role
 * 
 * @see BaseModel
 * @author shaobo shih
 * @version 1.0
 */
public class MerchantsRole extends BaseModel {
	private static final long serialVersionUID = 7390790894380261966L;
	private String license;
	private String name;
	private String code;
	private boolean allDataAuthority;
	private boolean allAdminAuthority;
	private boolean allFrontAuthority;
	private EPageType defaultPageType;
	private Integer defaultPageId;
	private String defaultPageUrl;
	private Integer secretLevel;
	private String memo;
	// Expand field for org role list string
	private String treeSelId;

	public MerchantsRole() {
	}

	public MerchantsRole(String license, String name, String code, boolean allDataAuthority, boolean allAdminAuthority,
			boolean allFrontAuthority, EPageType defaultPageType, Integer defaultPageId, String defaultPageUrl,
			Integer secretLevel, String memo, Integer creatorId, Date createTime, Integer updaterId, Date updateTime) {
		this.license = license;
		this.name = name;
		this.code = code;
		this.allDataAuthority = allDataAuthority;
		this.allAdminAuthority = allAdminAuthority;
		this.allFrontAuthority = allFrontAuthority;
		this.defaultPageType = defaultPageType;
		this.defaultPageId = defaultPageId;
		this.defaultPageUrl = defaultPageUrl;
		this.secretLevel = secretLevel;
		this.memo = memo;
		this.creatorId = creatorId;
		this.createTime = createTime;
		this.updaterId = updaterId;
		this.updateTime = updateTime;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isAllDataAuthority() {
		return allDataAuthority;
	}

	public void setAllDataAuthority(boolean allDataAuthority) {
		this.allDataAuthority = allDataAuthority;
	}

	public boolean isAllAdminAuthority() {
		return allAdminAuthority;
	}

	public void setAllAdminAuthority(boolean allAdminAuthority) {
		this.allAdminAuthority = allAdminAuthority;
	}

	public boolean isAllFrontAuthority() {
		return allFrontAuthority;
	}

	public void setAllFrontAuthority(boolean allFrontAuthority) {
		this.allFrontAuthority = allFrontAuthority;
	}

	public EPageType getDefaultPageType() {
		return defaultPageType;
	}

	public void setDefaultPageType(EPageType defaultPageType) {
		this.defaultPageType = defaultPageType;
	}

	public Integer getDefaultPageId() {
		return defaultPageId;
	}

	public void setDefaultPageId(Integer defaultPageId) {
		this.defaultPageId = defaultPageId;
	}

	public String getDefaultPageUrl() {
		return defaultPageUrl;
	}

	public void setDefaultPageUrl(String defaultPageUrl) {
		this.defaultPageUrl = defaultPageUrl;
	}

	public Integer getSecretLevel() {
		return secretLevel;
	}

	public void setSecretLevel(Integer secretLevel) {
		this.secretLevel = secretLevel;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getTreeSelId() {
		return treeSelId;
	}

	public void setTreeSelId(String treeSelId) {
		this.treeSelId = treeSelId;
	}

}
