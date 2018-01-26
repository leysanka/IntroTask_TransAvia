package com.epam.transavia.demo.reporting;

import com.epam.transavia.demo.core.exceptions.ScreenshotHelperException;
import com.epam.transavia.demo.util.ScreenshotHelper;
import org.testng.*;

import java.io.File;
import java.io.IOException;


public class CustomTestNGListener implements ITestListener, ISuiteListener, IConfigurationListener{

    private static final String START_MSG = "[%s] %s test has started.";
    private static final String SUCCESS_MSG = "[%s] %s finished successfully.";
    private static final String FAIL_MSG = "[%s] %s test failed.";
    private static final String SKIP_MSG = "[%s] %s is skipped.";


    @Override
    public void onTestStart(ITestResult iTestResult) {
        TestLogger.info(String.format(START_MSG, iTestResult.getInstanceName(), iTestResult.getName()));
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        TestLogger.info(String.format(SUCCESS_MSG, iTestResult.getInstanceName(), iTestResult.getName()));
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
//        TODO one method in TestLogger errorWithScreenshot
        TestLogger.error(String.format(FAIL_MSG, iTestResult.getInstanceName(), iTestResult.getName()) + "\n Trying to take screenshot...");
        screenshotRpError(iTestResult);
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        TestLogger.error(String.format(SKIP_MSG, iTestResult.getInstanceName(), iTestResult.getName()));
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }

    @Override
    public void onStart(ISuite iSuite) {
        TestLogger.debug("Starting suit run: " + iSuite.getName());

    }

    @Override
    public void onFinish(ISuite iSuite) {
        TestLogger.info(iSuite.getName() + " suit run is finished.");
    }

    @Override
    public void onConfigurationSuccess(ITestResult iTestResult) {

    }

    @Override
    public void onConfigurationFailure(ITestResult iTestResult) {
        TestLogger.error(String.format(FAIL_MSG, iTestResult.getInstanceName(), iTestResult.getName()) + "\n Trying to take screenshot...");
        screenshotRpError(iTestResult);
    }

    private void screenshotRpError(ITestResult iTestResult) {
        try {
            File screenshot = ScreenshotHelper.takeDriverScreenshot();
            TestLogger.sendFileToRP(screenshot, "Screenshot has been taken for failed test: ");
        } catch (ScreenshotHelperException e) {
            TestLogger.error("Could not take a screenshot on test failure " + iTestResult.getName());
        } catch (IOException e) {
            TestLogger.error("Could not send the screenshot to ReportPortal." + e.getMessage());
        }
    }

    @Override
    public void onConfigurationSkip(ITestResult iTestResult) {

    }
}
