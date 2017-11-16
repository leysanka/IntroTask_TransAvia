package com.epam.transavia.demo.util;

import com.epam.transavia.demo.core.driver.Driver;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class ScreenshotHelper {

    private static Logger logger = LogManager.getLogger(ScreenshotHelper.class.getSimpleName());

    private static final String SCREENSHOTS_FOLDER_PATH = "/target/screenshots/";

    public static void takeDriverScreenshot() {
        File screenshot = ((TakesScreenshot) Driver.getDefaultDriver()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File(SCREENSHOTS_FOLDER_PATH.concat(LocalDate.now().toString())));
        } catch (IOException e) {
            logger.error("Cannot copy screenshot to target location.");
        }

    }

    public void takeScreenshotFullScreen() {

    }


}
