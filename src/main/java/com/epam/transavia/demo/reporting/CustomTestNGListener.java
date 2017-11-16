package com.epam.transavia.demo.reporting;

import com.epam.transavia.demo.util.ScreenshotHelper;
import org.testng.*;

public class CustomTestNGListener implements ITestListener, ISuiteListener {
    @Override
    public void onTestStart(ITestResult iTestResult) {

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        TestsLogger.info(iTestResult.getInstanceName() + " : " + iTestResult.getName() + " finished successfully.");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        TestsLogger.error("Test failed, trying to take screenshot ...");
        ScreenshotHelper.takeDriverScreenshot();
        TestsLogger.info("Screenshot has been taken.");

    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        TestsLogger.warn("[" + iTestResult.getInstanceName() + "] " + iTestResult.getName() + " is skipped. ");
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
        TestsLogger.info("Starting suit run: " + iSuite.getName());

    }

    @Override
    public void onFinish(ISuite iSuite) {
        TestsLogger.info("Suit run is finished.");

    }
}
