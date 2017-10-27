package com.epam.transavia.demo.runner;


import org.kohsuke.args4j.Option;

public class Settings {

       @Option(name = "--testng", required = true) public String pathToTestngXML;
       @Option(name = "--driver") public String driver;

}
