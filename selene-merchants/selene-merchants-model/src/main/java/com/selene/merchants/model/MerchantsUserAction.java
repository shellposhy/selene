package com.selene.merchants.model;

import com.selene.common.BaseModel;
import com.selene.common.tree.TreeNodeModel;
import com.selene.merchants.model.enums.EActionUserType;

/**
 * <b>Selene merchants module<b>
 * <p>
 * Mapping object for merchants organization
 * 
 * @see BaseModel
 * @author shaobo shih
 * @version 1.0
 */
public class MerchantsUserAction extends TreeNodeModel<MerchantsUserAction> {
	private static final long serialVersionUID = 7390790894380261966L;
	private String code;
	private String uri;
	private String iconSkin;
	private EActionUserType type;
	private Integer orderId;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

	public EActionUserType getType() {
		return type;
	}

	public void setType(EActionUserType type) {
		this.type = type;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

}
