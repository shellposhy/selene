package com.selene.dataing.model;

import com.selene.common.BaseModel;
import com.selene.dataing.model.enums.EAccessType;
import com.selene.dataing.model.enums.EDataFieldType;
import com.selene.dataing.model.enums.EDataType;
import com.selene.dataing.model.enums.ELuceneIndexType;

/**
 * <b>Selene dataing module<b>
 * <p>
 * Mapping object for database field database
 * 
 * @see BaseModel
 * @author shaobo shih
 * @version 1.0
 */
public class DataingDataField extends BaseModel {
	private static final long serialVersionUID = 5343183210152494952L;
	private Integer componentId;
	private String name;
	private String code;
	private String codeName;
	private EDataType dataType;
	private boolean nosg;
	private Integer leng;
	private Integer prec;
	private boolean mand;
	private boolean uniq;
	private boolean multiValue;
	private boolean useEnum;
	private ELuceneIndexType indexType;
	private boolean indexStore;
	private boolean required;
	private EDataFieldType type;
	private Integer orderId;
	private EAccessType accessType;
	private boolean forDisplay;
	private boolean forOrder;
	private String memo;
	// extension data field
	private String oldCode;

	public Integer getComponentId() {
		return componentId;
	}

	public void setComponentId(Integer componentId) {
		this.componentId = componentId;
	}

	public String getName() {
		return name;
	}

	public String getFullName() {
		StringBuilder fullName = new StringBuilder();
		fullName.append(name).append(" | ").append(dataType.title);
		switch (this.dataType) {
		case Date:
		case Time:
		case DateTime:
			break;
		default:
			if (!leng.equals(0))
				fullName.append("(" + leng + ")");
			break;
		}
		return fullName.toString();
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

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public EDataType getDataType() {
		return dataType;
	}

	public void setDataType(EDataType dataType) {
		this.dataType = dataType;
	}

	public boolean isNosg() {
		return nosg;
	}

	public void setNosg(boolean nosg) {
		this.nosg = nosg;
	}

	public Integer getLeng() {
		return leng;
	}

	public void setLeng(Integer leng) {
		this.leng = leng;
	}

	public Integer getPrec() {
		return prec;
	}

	public void setPrec(Integer prec) {
		this.prec = prec;
	}

	public boolean isMand() {
		return mand;
	}

	public void setMand(boolean mand) {
		this.mand = mand;
	}

	public boolean isUniq() {
		return uniq;
	}

	public void setUniq(boolean uniq) {
		this.uniq = uniq;
	}

	public boolean isMultiValue() {
		return multiValue;
	}

	public void setMultiValue(boolean multiValue) {
		this.multiValue = multiValue;
	}

	public boolean isUseEnum() {
		return useEnum;
	}

	public void setUseEnum(boolean useEnum) {
		this.useEnum = useEnum;
	}

	public ELuceneIndexType getIndexType() {
		return indexType;
	}

	public void setIndexType(ELuceneIndexType indexType) {
		this.indexType = indexType;
	}

	public boolean isIndexStore() {
		return indexStore;
	}

	public void setIndexStore(boolean indexStore) {
		this.indexStore = indexStore;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public EDataFieldType getType() {
		return type;
	}

	public void setType(EDataFieldType type) {
		this.type = type;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public EAccessType getAccessType() {
		return accessType;
	}

	public void setAccessType(EAccessType accessType) {
		this.accessType = accessType;
	}

	public boolean isForDisplay() {
		return forDisplay;
	}

	public void setForDisplay(boolean forDisplay) {
		this.forDisplay = forDisplay;
	}

	public boolean isForOrder() {
		return forOrder;
	}

	public void setForOrder(boolean forOrder) {
		this.forOrder = forOrder;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOldCode() {
		return oldCode;
	}

	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}

}
