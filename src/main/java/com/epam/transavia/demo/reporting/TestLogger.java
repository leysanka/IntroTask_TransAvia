package com.epam.transavia.demo.reporting;


import static org.apache.log4j.Logger.getLogger;

public class TestLogger {

   // private static Logger testLogger = LogManager.getLogger("Test");
    //public static Logger testLogger = getRootLogger();
   public static org.apache.log4j.Logger customlogger = getLogger(TestLogger.class);
    public static void info(String message) {
        customlogger.info(message);
    }

    public static void warn(String message) {
        customlogger.warn(message);
    }

    public static void debug(String message) {
        customlogger.debug(message);
    }

    public static void error(String message) {
        customlogger.error(message);
    }


}
