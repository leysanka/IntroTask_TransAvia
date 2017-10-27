package com.epam.transavia.demo.runner;

import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;

import java.io.IOException;
import java.util.List;

public class TestNGRunner {

    public static void main(String[] args) {

        TestNG testNG = new TestNG();

        try {
            testNG.setXmlSuites((List<XmlSuite>)new Parser(args[0]).parse());
            testNG.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
