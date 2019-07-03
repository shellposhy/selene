package com.selene.common.config;

/**
 * Base interface based on properties processing
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface Configer {

	public String[] keys();

	public String value(String key);

	public String[] match(String divisor, String... keys);
}
