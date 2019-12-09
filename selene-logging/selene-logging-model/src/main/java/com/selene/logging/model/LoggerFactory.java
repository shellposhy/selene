package com.selene.logging.model;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.selene.common.constants.CommonConstants;
import com.selene.logging.model.jcl.JclLoggerAdapter;
import com.selene.logging.model.jdk.JdkLoggerAdapter;
import com.selene.logging.model.log4j.Log4jLoggerAdapter;
import com.selene.logging.model.slf4j.Slf4jLoggerAdapter;
import com.selene.logging.model.support.FailsafeLogger;

/**
 * <b>Selene logging module<b>
 * <p>
 * Mapping object for logger factory
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class LoggerFactory {

	private static final ConcurrentMap<String, FailsafeLogger> LOGGERS = new ConcurrentHashMap<>();
	private static volatile LoggerAdapter LOGGER_ADAPTER;

	static {
		String logger = CommonConstants.SELENE_LOGGER;
		switch (logger) {
		case "slf4j":
			setLoggerAdapter(new Slf4jLoggerAdapter());
			break;
		case "jcl":
			setLoggerAdapter(new JclLoggerAdapter());
			break;
		case "log4j":
			setLoggerAdapter(new Log4jLoggerAdapter());
			break;
		case "jdk":
			setLoggerAdapter(new JdkLoggerAdapter());
			break;
		default:
			List<Class<? extends LoggerAdapter>> candidates = Arrays.asList(Log4jLoggerAdapter.class,
					Slf4jLoggerAdapter.class, JclLoggerAdapter.class, JdkLoggerAdapter.class);
			for (Class<? extends LoggerAdapter> clazz : candidates) {
				try {
					setLoggerAdapter(clazz.newInstance());
					break;
				} catch (Throwable ignored) {
				}
			}
		}
	}

	private LoggerFactory() {
	}

	/**
	 * Set logger provider
	 *
	 * @param loggerAdapter
	 *            logger provider
	 */
	public static void setLoggerAdapter(LoggerAdapter loggerAdapter) {
		if (loggerAdapter != null) {
			/*Logger logger = loggerAdapter.getLogger(LoggerFactory.class.getName());*/
			/*logger.info("using logger: " + loggerAdapter.getClass().getName());*/
			LoggerFactory.LOGGER_ADAPTER = loggerAdapter;
			for (Map.Entry<String, FailsafeLogger> entry : LOGGERS.entrySet()) {
				entry.getValue().setLogger(LOGGER_ADAPTER.getLogger(entry.getKey()));
			}
		}
	}

	/**
	 * Get logger provider
	 *
	 * @param key
	 *            the returned logger will be named after clazz
	 * @return logger
	 */
	public static Logger getLogger(Class<?> key) {
		FailsafeLogger logger = LOGGERS.get(key.getName());
		if (logger == null) {
			logger = new FailsafeLogger(LOGGER_ADAPTER.getLogger(key.getName()));
			LOGGERS.put(key.getName(), logger);
		}
		return logger;
	}

	/**
	 * Get logger provider
	 *
	 * @param key
	 *            the returned logger will be named after key
	 * @return logger provider
	 */
	public static Logger getLogger(String key) {
		FailsafeLogger logger = LOGGERS.get(key);
		if (logger == null) {
			logger = new FailsafeLogger(LOGGER_ADAPTER.getLogger(key));
			LOGGERS.put(key, logger);
		}
		return logger;
	}

	/**
	 * Get logging level
	 *
	 * @return logging level
	 */
	public static Level getLevel() {
		return LOGGER_ADAPTER.getLevel();
	}

	/**
	 * Set the current logging level
	 *
	 * @param level
	 *            logging level
	 */
	public static void setLevel(Level level) {
		LOGGER_ADAPTER.setLevel(level);
	}

	/**
	 * Get the current logging file
	 *
	 * @return current logging file
	 */
	public static File getFile() {
		return LOGGER_ADAPTER.getFile();
	}

}
