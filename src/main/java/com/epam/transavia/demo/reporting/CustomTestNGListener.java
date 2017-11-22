package com.epam.transavia.demo.reporting;

import com.epam.transavia.demo.core.exceptions.ScreenshotHelperException;
import com.epam.transavia.demo.util.ScreenshotHelper;
import org.testng.*;

import java.io.File;


public class CustomTestNGListener implements ITestListener, ISuiteListener {

    private static final String START_MSG = "[%s] %s test has started.";
    private static final String SUCCESS_MSG = "[%s] %s finished successfully.";
    private static final String FAIL_MSG = "[%s] %s test failed.";
    private static final String SKIP_MSG = "[%s] %s is skipped.";


    @Override
    public void onTestStart(ITestResult iTestResult) {
       TestLogger.info( String.format(START_MSG,iTestResult.getInstanceName(),iTestResult.getName()));



    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
         TestLogger.info(String.format(SUCCESS_MSG,iTestResult.getInstanceName(),iTestResult.getName()));
    }
//TODO Try screenshots posting when RP is up
    @Override
    public void onTestFailure(ITestResult iTestResult) {
        TestLogger.error(String.format(FAIL_MSG,iTestResult.getInstanceName(),iTestResult.getName()) +"\n Trying to take screenshot...");
        TestLogger.rpError(String.format(FAIL_MSG,iTestResult.getInstanceName(),iTestResult.getName()) +"\n Trying to take screenshot...");
        try {
            File screenshot = ScreenshotHelper.takeScreenshotFullScreen();
            TestLogger.debug("Screenshot has been taken for failed test " + iTestResult.getName());
         //   TestLogger.rpError(screenshot, "Screenshot has been taken for failed test " + iTestResult.getName() );
        } catch (ScreenshotHelperException e) {
            TestLogger.error("Could not take a screenshot on test failure " + iTestResult.getName());
        } /*catch (IOException e) {
           TestLogger.rpError("Could not send the screenshot to ReportPortal. " + e.getMessage());
        }*/
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        TestLogger.error(String.format(SKIP_MSG,iTestResult.getInstanceName(),iTestResult.getName()));
        TestLogger.rpError(String.format(SKIP_MSG,iTestResult.getInstanceName(),iTestResult.getName()));
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        TestLogger.debug("OnFinishTextContext called.");

    }

    @Override
    public void onStart(ISuite iSuite) {
        TestLogger.debug("Starting suit run: " + iSuite.getName());

    }

    @Override
    public void onFinish(ISuite iSuite) {
        TestLogger.info(iSuite.getName() + " suit run is finished.");

    }
}
