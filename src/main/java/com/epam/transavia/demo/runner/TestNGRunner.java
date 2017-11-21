package com.epam.transavia.demo.runner;

import com.epam.reportportal.testng.ReportPortalTestNGListener;
import com.epam.transavia.demo.core.driver.Driver;
import com.epam.transavia.demo.core.exceptions.TestNGRunnerSetupException;
import com.epam.transavia.demo.reporting.CustomTestNGListener;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.testng.ITestNGListener;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestNGRunner {

    public static void main(String[] args) {

        TestNG testNG = new TestNG();
        Settings settings = new Settings();
        CmdLineParser cmdAttributesParser = new CmdLineParser(settings);
        ITestNGListener customTestNGListener = new CustomTestNGListener();
        ReportPortalTestNGListener reportPortalTestNGListener = new ReportPortalTestNGListener();

        try {
            cmdAttributesParser.parseArgument(args);
            Driver.setDefaultDriver(settings.driver);
            System.setProperty("log4j2.debug", "true");

            List<Class<? extends ITestNGListener>> classes = new ArrayList<>();
            classes.add(ReportPortalTestNGListener.class);
            classes.add(CustomTestNGListener.class);
            testNG.setListenerClasses(classes);

//          testNG.addListener((ITestNGListener) reportPortalTestNGListener);

            testNG.setXmlSuites((List<XmlSuite>) new Parser(settings.pathToTestngXML).parse());
            TestListenerAdapter results = new TestListenerAdapter();
            testNG.addListener(results);
           // testNG.addListener(customTestNGListener);
            testNG.run();
            System.exit(0);

        } catch (IOException | CmdLineException e) {
            throw new TestNGRunnerSetupException("Cannot start TestNG suite due to XmlSuite file is not found or incorrect Cmd arguments are passed. "
                                                 + "\n" + e.getMessage());
        }
    }
}
