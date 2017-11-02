package com.epam.transavia.demo.runner;

import com.epam.transavia.demo.core.driver.Driver;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;

import java.io.IOException;
import java.util.List;

public class TestNGRunner {

    public static void main(String[] args) {

        TestNG testNG = new TestNG();
        Settings settings = new Settings();
        CmdLineParser cmdAttributesParser = new CmdLineParser(settings);

        try {
            cmdAttributesParser.parseArgument(args);
            Driver.setDefaultDriver(settings.driver);
            testNG.setXmlSuites((List<XmlSuite>) new Parser(settings.pathToTestngXML).parse());
            testNG.run();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CmdLineException e) {
            e.printStackTrace();
        }
    }
}
