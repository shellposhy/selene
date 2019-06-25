package com.selene.common;

/**
 * Common mapping processing for underlying business operations.
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface Operator {

	public void operate();

	public String success();

	public String fail();

	public void error();

}
