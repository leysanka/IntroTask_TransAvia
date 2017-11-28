package com.epam.transavia.demo.util;

import com.epam.transavia.demo.core.driver.Driver;
import com.epam.transavia.demo.core.exceptions.ScreenshotHelperException;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ScreenshotHelper {

    private static Logger logger = LogManager.getLogger(ScreenshotHelper.class.getSimpleName());

    private static final DateTimeFormatter LOCAL_DATETIME_SEC_MS_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss.ms");
    private static final String SCREENSHOTS_FOLDER_PATH = "/screenshots/.";
    private static final String FILE_EXTENSION = "png";


    public static File takeDriverScreenshot() throws ScreenshotHelperException {
        File screenshot = null;
        try {
            screenshot = ((TakesScreenshot) Driver.getDefaultDriver()).getScreenshotAs(OutputType.FILE);
        } catch (WebDriverException e) {
            logger.error("Driver could not take a screenshot.");
            throw new ScreenshotHelperException("Driver could not take a screenshot.");
        }
        File screenshotStore = new File(generateTargetFilePath());
        copyFile(screenshot, screenshotStore);
        return screenshotStore;
    }

    public static File takeScreenshotFullScreen() throws ScreenshotHelperException {
        Robot robot = null;
        File screenshotStore = null;
        try {
            robot = new Robot();
            BufferedImage screenshot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            screenshotStore = new File(generateTargetFilePath());
            writeBufferedImageToSourceFile(screenshot, screenshotStore);
        } catch (AWTException e) {
            logger.error("java.awt robot failed to take a full screen screenshot. System error: " + e.getMessage());
            throw new ScreenshotHelperException("java.awt robot failed to take a full screen screenshot.");
        } catch (IOException e) {
            logger.error("Could not write full screen image to the location: " + screenshotStore.getAbsolutePath()
                         + "\n System error: " + e.getMessage());
            throw new ScreenshotHelperException("Could not write full screen image to the location: " + screenshotStore.getAbsolutePath());
        }
        return screenshotStore;
    }

    private static void writeBufferedImageToSourceFile(BufferedImage screenshot, File screenshotStore) throws IOException, ScreenshotHelperException {
        if (ImageIO.write(screenshot, FILE_EXTENSION, screenshotStore)) {
            logger.debug("Screenshot saved to the target location: " + screenshotStore.getAbsolutePath());
        } else {
            logger.error("Could not write full screen image to the location: " + screenshotStore.getAbsolutePath());
            throw new ScreenshotHelperException("Could not write full screen image to the location: " + screenshotStore.getAbsolutePath());
        }
    }

    private static String generateTargetFilePath() {
        File currDirectory = new File(System.getProperty("user.dir"));
        File directory = new File(currDirectory.getAbsolutePath().concat(SCREENSHOTS_FOLDER_PATH));
        if (!directory.exists()) {
            directory.mkdir();
        }
        return directory.getAbsolutePath().concat(DateTimeHelper.formatLocalNow(LOCAL_DATETIME_SEC_MS_FORMATTER))
                .concat(".").concat(FILE_EXTENSION);
    }

    private static void copyFile(File source, File target) throws ScreenshotHelperException {
        if (source.exists() && source.canRead()) {
            try {
                FileUtils.copyFile(source, target);
                logger.debug("Screenshot saved to the target location: " + target.getAbsolutePath());
            } catch (IOException e) {
                logger.error("Could not copy screenshot to the target location: " + target.getAbsolutePath());
                throw new ScreenshotHelperException("Could not copy screenshot to the target location.");
            }
        } else {
            throw new ScreenshotHelperException("Source screenshot file does not exist or cannot be read.");
        }
    }

}
