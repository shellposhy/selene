package com.selene.common.constants.util;

/**
 * Enumeration of mysql data field types
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum EDataType {
	Bool("bool"), Char("char"), Varchar("varchar"), Short("tinyint"), Int("int"), Long("bigint"), Float(
			"float"), Double("double"), Numeric("numeric"), Date("date"), Time("time"), DateTime("datetime"), Blob(
					"blob"), MediumBlob("mediumblob"), IntAutoIncrement("int"), UUID("char(36) character set ascii");

	public String title;

	private EDataType(String title) {
		this.title = title;
	}

	public String getMysqlDataType() {
		return this.title;
	}
}
