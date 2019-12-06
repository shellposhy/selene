package com.selene.logging.model;

/**
 * <b>Selene logging module<b>
 * <p>
 * Mapping object for log interface
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface Logger {

	/* Logs a message with trace log level. */
	public void trace(String msg);

	public void trace(Throwable e);

	public void trace(String msg, Throwable e);

	/* Logs a message with debug log level. */
	public void debug(String msg);

	public void debug(Throwable e);

	public void debug(String msg, Throwable e);

	/* Logs a message with info log level. */
	public void info(String msg);

	public void info(Throwable e);

	public void info(String msg, Throwable e);

	/* Logs a message with warn log level. */
	public void warn(String msg);

	public void warn(Throwable e);

	public void warn(String msg, Throwable e);

	/* Logs a message with error log level. */
	public void error(String msg);

	public void error(Throwable e);

	public void error(String msg, Throwable e);

	/* Is trace logging currently enabled? */
	public boolean isTraceEnabled();

	/* Is debug logging currently enabled? */
	public boolean isDebugEnabled();

	/* Is info logging currently enabled? */
	public boolean isInfoEnabled();

	/* Is warn logging currently enabled? */
	public boolean isWarnEnabled();

	/* Is error logging currently enabled? */
	public boolean isErrorEnabled();

}