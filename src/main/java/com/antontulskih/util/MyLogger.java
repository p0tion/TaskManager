package com.antontulskih.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Tulskih Anton
 * @{NAME} 25.08.2015
 */
public class MyLogger {
    Logger logger;

    public MyLogger(Class clazz) {
        logger = LogManager.getLogger(clazz);
    }

    public void debug(final String message) {
        if (logger.isDebugEnabled()) {
            logger.debug(message);
        }
    }

    public void trace(final String message) {
        if (logger.isTraceEnabled()) {
            logger.trace(message);
        }
    }

    public void error(final String message, final Throwable throwable) {
        if (logger.isErrorEnabled()) {
            logger.error(message, throwable);
        }
    }
}

