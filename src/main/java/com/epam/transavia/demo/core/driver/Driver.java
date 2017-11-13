package com.epam.transavia.demo.core.driver;

import com.epam.transavia.demo.core.exceptions.UnknownDriverTypeException;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Driver {

    private static HashMap<String, org.openqa.selenium.WebDriver> instances = new HashMap<String, WebDriver>();
    private static final int TIMEOUT_IN_SEC = 10;
    private static WebDriverType defaultBrowserType = WebDriverType.CHROME;

    private Driver() {
    }

    private static WebDriver getDriverOfType(WebDriverType webDriverType) {
        WebDriverFactory driverFactory;
        switch (webDriverType) {
            case CHROME: {
                driverFactory = new ChromeDriverFactory();
                break;
            }
            case FIREFOX: {
                driverFactory = new FirefoxDriverFactory();
                break;
            }
            default: {
                throw new UnknownDriverTypeException("The specified driver does not exist in the WebDriverType: " + webDriverType);
            }
        }
        WebDriver driver = driverFactory.getDriverOf(webDriverType);
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(TIMEOUT_IN_SEC, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        return driver;
    }


    private static WebDriver getDriverSingleInstance(WebDriverType webDriverType) {
        WebDriver driver;
        String name = webDriverType.toString();

        if (!instances.containsKey(name)) {
            driver = getDriverOfType(webDriverType);
            instances.put(name, driver);
        } else {
            driver = instances.get(name);
        }
        driver.manage().deleteAllCookies();
        return driver;
    }

    public static WebDriver getDriverByName(String name) {
        setDefaultDriver(name);
        return getDefaultDriver();
    }

    public static WebDriver getDefaultDriver() {
        return getDriverSingleInstance(defaultBrowserType);
    }

    public static void setDefaultDriver(String name) {
        defaultBrowserType = WebDriverType.getWebDriverType(name);
    }

    public static void clearInstances() {
        instances.clear();
    }

    public static void closeDriver(WebDriver driver) {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
        instances.clear();
    }

}
