package com.epam.transavia.demo.util;

import com.epam.transavia.demo.core.driver.Driver;
import com.epam.transavia.demo.core.exceptions.ScreenshotHelperException;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ScreenshotHelper {

    private static Logger logger = LogManager.getLogger(ScreenshotHelper.class.getSimpleName());

    private static final DateTimeFormatter LOCAL_DATETIME_SEC_MS_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss.ms");
    private static final String SCREENSHOTS_FOLDER_PATH =
            "C:\\Users\\Alesia_Kastsiuchenka\\IdeaProjects\\IntroTask_TransAvia\\target\\screenshots\\";
    private static final String FILE_EXTENSION = ".png";


    public static void takeDriverScreenshot() throws ScreenshotHelperException {
        File screenshot = null;
        try {
            screenshot = ((TakesScreenshot) Driver.getDefaultDriver()).getScreenshotAs(OutputType.FILE);
        } catch (WebDriverException e) {
            logger.error("Driver could not take a screenshot.");
            throw new ScreenshotHelperException("Driver could not take a screenshot.");
        }
        File screenshotStore = new File(generateTargetPath());
        copyFile(screenshot, screenshotStore);
    }

    public void takeScreenshotFullScreen() {

    }

    private static String generateTargetPath() {

        return SCREENSHOTS_FOLDER_PATH.concat(DateTimeHelper.formatLocalNow(LOCAL_DATETIME_SEC_MS_FORMATTER))
                .concat(FILE_EXTENSION);
    }

    private static void copyFile(File source, File target) throws ScreenshotHelperException {
        if (source.exists() && source.canRead()) {
            try {
                FileUtils.copyFile(source, target);
                logger.debug("Screenshot has been put to the target location: " + target.getAbsolutePath());
            } catch (IOException e) {
                logger.error("Could not copy screenshot to the target location: " + target.getAbsolutePath());
                throw new ScreenshotHelperException("Could not copy screenshot to the target location.");
            }
        } else {
            throw new ScreenshotHelperException("Source screenshot file does not exist or cannot be read.");
        }
    }

}
