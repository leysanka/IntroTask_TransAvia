package com.epam.transavia.demo.core.driver;

import com.epam.transavia.demo.core.exceptions.UnknownDriverTypeException;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

public class Driver {

    private static HashMap<String, org.openqa.selenium.WebDriver> instances = new HashMap<String, WebDriver>();
    private static final ChromeDriverFactory chromeFactory = new ChromeDriverFactory();
    private static final FirefoxDriverFactory firefoxFactory = new FirefoxDriverFactory();
    private static String defaultBrowserName = "CHROME";

    private Driver() {
    }

    private static WebDriver getDriverOfType(WebDriverType webDriverType) {
        WebDriverFactory driverFactory;
        switch (webDriverType) {
            case CHROME: {
                driverFactory = chromeFactory;
                break;
            }
            case FIREFOX: {
                driverFactory = firefoxFactory;
                break;
            }
            default: {
                throw new UnknownDriverTypeException("The specified driver does not exist in the WebDriverType: " + webDriverType);
            }
        }
        return driverFactory.getDriverOf(webDriverType);
    }


    private static WebDriver getDriverSingleInstance(String name, WebDriverType webDriverType) {
        WebDriver driver;
        if (!instances.containsKey(name)) {
            driver = getDriverOfType(webDriverType);
            instances.put(name, driver);
        } else {
            driver = instances.get(name);
        }
        return driver;
    }

    public static WebDriver getDriverByName(String name) {
        return getDriverSingleInstance(name, WebDriverType.valueOf(name.toUpperCase()));
    }

    public static WebDriver getDefaultDriver() {
        return getDriverSingleInstance(defaultBrowserName, WebDriverType.valueOf(defaultBrowserName.toUpperCase()));
    }

    public static void setDefaultDriver(String name) {
        defaultBrowserName = name;
    }

    public static void clearInstances() {
        instances.clear();
    }

}
