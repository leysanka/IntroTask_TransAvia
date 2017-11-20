package com.epam.transavia.demo.reporting;

import com.epam.transavia.demo.core.exceptions.ScreenshotHelperException;
import com.epam.transavia.demo.util.ScreenshotHelper;
import org.testng.*;

public class CustomTestNGListener implements ITestListener, ISuiteListener {
    @Override
    public void onTestStart(ITestResult iTestResult) {
        TestLogger.warn( "[" + iTestResult.getInstanceName() + "] " + iTestResult.getName() + "test has started.");

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        TestLogger.info("[" + iTestResult.getInstanceName() + "] " + iTestResult.getName() + " finished successfully.");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        TestLogger.error("[" + iTestResult.getInstanceName() + "] " + iTestResult.getName() + " test Failed \n Trying to take screenshot...");
        try {
            ScreenshotHelper.takeDriverScreenshot();
           // ScreenshotHelper.takeScreenshotFullScreen();
            TestLogger.info("Screenshot has been taken for failed test " + iTestResult.getName());
        } catch (ScreenshotHelperException e) {
            TestLogger.error("Could not take a screenshot on test failure " + iTestResult.getName());
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        TestLogger.error("[" + iTestResult.getInstanceName() + "] " + iTestResult.getName() + " is skipped.");
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
        TestLogger.info("Starting suit run: " + iSuite.getName());

    }

    @Override
    public void onFinish(ISuite iSuite) {
        TestLogger.info(iSuite.getName() + " suit run is finished.");

    }
}
