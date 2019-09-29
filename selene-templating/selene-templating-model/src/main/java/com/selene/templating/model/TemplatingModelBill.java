package com.selene.templating.model;

import com.selene.common.tree.TreeNodeModel;

/**
 * <b>Selene templating module<b>
 * <p>
 * Mapping object for model bill
 * 
 * @see TreeNodeModel
 * @author shaobo shih
 * @version 1.0
 */
public class TemplatingModelBill extends TreeNodeModel<TemplatingModelBill> {
	private static final long serialVersionUID = -465535204496707275L;
	private String license;
	private String code;
	private String orderId;

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
