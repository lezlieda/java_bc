package edu.school21.sockets.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class SocketLogger {
    private final Logger logger = LogManager.getLogger(SocketLogger.class);
    private static SocketLogger instance = null;

    public static SocketLogger getInstance() {
        if (instance == null) {
            instance = new SocketLogger();
        }
        return instance;
    }

    public void info(String message) {
        logger.info(message);
    }

    public void info(String message, Object... params) {
        logger.info(message, params);
    }

    public void error(String message, Throwable t) {
        logger.error(message, t);
    }

}
