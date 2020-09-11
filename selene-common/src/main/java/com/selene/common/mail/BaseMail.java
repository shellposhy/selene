package com.selene.common.mail;

import jodd.mail.MailException;

public abstract class BaseMail {

	public abstract boolean send() throws MailException;

	public abstract boolean receive() throws MailException;

}