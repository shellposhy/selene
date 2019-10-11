package com.selene.templating.model;

import com.selene.common.tree.TreeNodeModel;
import com.selene.templating.model.constants.EModelType;

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
	private EModelType type;
	private String code;
	private String pathCode;
	private Integer orderId;

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public EModelType getType() {
		return type;
	}

	public void setType(EModelType type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPathCode() {
		return pathCode;
	}

	public void setPathCode(String pathCode) {
		this.pathCode = pathCode;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

}
