package com.epam.transavia.demo.reporting;

import com.epam.reportportal.message.ReportPortalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class TestLogger {

    private static Logger testLogger = LogManager.getLogger("Test");


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
        ReportPortalMessage rpMessage = new ReportPortalMessage(message);
//        testLogger.error(message);
      /*  try {
            File screenshot = ScreenshotHelper.takeDriverScreenshot();
            TestLogger.sendFileToRP(screenshot,message);
        } catch (ScreenshotHelperException e) {
            TestLogger.error("Could not take a screenshot after Error: " + message);
        } catch (IOException e) {
            TestLogger.error("Could not send the screenshot to ReportPortal." + e.getMessage());
        }*/
        testLogger.error(message);

    }

    public static void sendFileToRP(File file, String message) throws IOException {
        testLogger.info("RP_MESSAGE#FILE#{}#{}", file.getAbsolutePath(), message);
    }

}
