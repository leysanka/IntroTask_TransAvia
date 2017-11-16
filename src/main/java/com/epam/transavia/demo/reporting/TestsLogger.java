package com.epam.transavia.demo.reporting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestsLogger {

    private static Logger testLogger = LogManager.getLogger("Test");

    public static void info(String message) {
        testLogger.info(message);
    }

    public static void warn(String message) {
        testLogger.warn(message);
    }

    public static void error(String message) {
        testLogger.error(message);
    }


}
