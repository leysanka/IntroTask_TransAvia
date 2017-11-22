package com.epam.transavia.demo.reporting;

import com.epam.reportportal.message.ReportPortalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
/*import static org.apache.log4j.Logger.getRootLogger;*/


public class TestLogger {

    private static Logger testLogger = LogManager.getLogger("Test");
    //private static org.apache.log4j.Logger testLogger = getRootLogger();


    public TestLogger() {
    }

    public static void info(String message) {
        ReportPortalMessage rpMessage = new ReportPortalMessage(message);
        testLogger.info(message);
    }

    public static void warn(String message) {
        testLogger.warn(message);
    }

    public static void debug(String message) {
        testLogger.debug(message);
    }

    public static void error(String message) {
        testLogger.error(message);
    }

    public static void rpError(String message) {
        //testLogger.error(message);
        ReportPortalMessage rpMessage = new ReportPortalMessage(message);

    }

    public static void rpError(File file, String message) throws IOException {
        //testLogger.error(message);
        ReportPortalMessage rpMessage = new ReportPortalMessage(file, message);
    }

}
