package com.epam.transavia.demo.runner;


import org.kohsuke.args4j.Option;

public class Settings {

    @Option(name = "--testng", required = true)
    public String pathToTestngXML;

    @Option(name = "--driver", required = true)
    public String driver;

    @Option(name = "--scr.path")
    public String screenshotsPath;

    @Option(name = "--scr.format")
    public String screenshotsFormat;

    @Option(name = "--OAUTH_TOKEN")
    public String gitToken;

}
