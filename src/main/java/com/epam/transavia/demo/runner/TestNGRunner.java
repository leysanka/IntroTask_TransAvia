package com.epam.transavia.demo.runner;

import com.epam.reportportal.testng.ReportPortalTestNGListener;
import com.epam.transavia.demo.core.driver.Driver;
import com.epam.transavia.demo.core.exceptions.TestNGRunnerSetupException;
import com.epam.transavia.demo.reporting.CustomTestNGListener;
import com.google.common.collect.Lists;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.testng.ITestNGListener;
import org.testng.TestNG;

import java.util.Collections;
import java.util.List;

public class TestNGRunner {

    public static void main(String[] args) {

        TestNG testNG = new TestNG();
        Settings settings = new Settings();
        CmdLineParser cmdAttributesParser = new CmdLineParser(settings);
        ReportPortalTestNGListener reportPortalTestNGListener = new ReportPortalTestNGListener();
        CustomTestNGListener testNGListener = new CustomTestNGListener();
        try {
            cmdAttributesParser.parseArgument(args);
            Driver.setDefaultDriver(settings.driver);
            System.setProperty("log4j2.debug", "true");
            testNG.addListener((ITestNGListener) testNGListener);
            testNG.addListener((ITestNGListener) reportPortalTestNGListener);

            List<String> lSuites = Lists.newArrayList();
            Collections.addAll(lSuites, settings.pathToTestngXML);
            testNG.setTestSuites(lSuites);
            testNG.run();
            System.exit(0);

        } catch (CmdLineException e) {
            throw new TestNGRunnerSetupException("Cannot start TestNG suite due to XmlSuite file is not found or incorrect Cmd arguments are passed. "
                                                 + "\n" + e.getMessage());
        }
    }
}
