package com.selene.logging.model.log4j;


import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.LogManager;

import com.selene.logging.model.Level;
import com.selene.logging.model.Logger;
import com.selene.logging.model.LoggerAdapter;

import java.io.File;
import java.util.Enumeration;

public class Log4jLoggerAdapter implements LoggerAdapter {

    private File file;

    @SuppressWarnings("unchecked")
    public Log4jLoggerAdapter() {
        try {
            org.apache.log4j.Logger logger = LogManager.getRootLogger();
            if (logger != null) {
                Enumeration<Appender> appenders = logger.getAllAppenders();
                if (appenders != null) {
                    while (appenders.hasMoreElements()) {
                        Appender appender = appenders.nextElement();
                        if (appender instanceof FileAppender) {
                            FileAppender fileAppender = (FileAppender) appender;
                            String filename = fileAppender.getFile();
                            file = new File(filename);
                            break;
                        }
                    }
                }
            }
        } catch (Throwable t) {
        }
    }

    private static org.apache.log4j.Level toLog4jLevel(Level level) {
        if (level == Level.ALL) {
            return org.apache.log4j.Level.ALL;
        }
        if (level == Level.TRACE) {
            return org.apache.log4j.Level.TRACE;
        }
        if (level == Level.DEBUG) {
            return org.apache.log4j.Level.DEBUG;
        }
        if (level == Level.INFO) {
            return org.apache.log4j.Level.INFO;
        }
        if (level == Level.WARN) {
            return org.apache.log4j.Level.WARN;
        }
        if (level == Level.ERROR) {
            return org.apache.log4j.Level.ERROR;
        }
        // if (level == Level.OFF)
        return org.apache.log4j.Level.OFF;
    }

    private static Level fromLog4jLevel(org.apache.log4j.Level level) {
        if (level == org.apache.log4j.Level.ALL) {
            return Level.ALL;
        }
        if (level == org.apache.log4j.Level.TRACE) {
            return Level.TRACE;
        }
        if (level == org.apache.log4j.Level.DEBUG) {
            return Level.DEBUG;
        }
        if (level == org.apache.log4j.Level.INFO) {
            return Level.INFO;
        }
        if (level == org.apache.log4j.Level.WARN) {
            return Level.WARN;
        }
        if (level == org.apache.log4j.Level.ERROR) {
            return Level.ERROR;
        }
        // if (level == org.apache.log4j.Level.OFF)
        return Level.OFF;
    }

    @Override
    public Logger getLogger(Class<?> key) {
        return new Log4jLogger(LogManager.getLogger(key));
    }

    @Override
    public Logger getLogger(String key) {
        return new Log4jLogger(LogManager.getLogger(key));
    }

    @Override
    public Level getLevel() {
        return fromLog4jLevel(LogManager.getRootLogger().getLevel());
    }

    @Override
    public void setLevel(Level level) {
        LogManager.getRootLogger().setLevel(toLog4jLevel(level));
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public void setFile(File file) {

    }

}
