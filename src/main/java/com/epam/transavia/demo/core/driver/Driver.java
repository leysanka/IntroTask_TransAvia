package com.epam.transavia.demo.core.driver;

import com.epam.transavia.demo.core.exceptions.UnknownDriverTypeException;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Driver {

    private static HashMap<String, DriverDecorator> instances = new HashMap<String, DriverDecorator>();
    private static final int TIMEOUT_IN_SEC = 10;
    private static WebDriverType defaultBrowserType = WebDriverType.CHROME;

    private Driver() {
    }

    private static WebDriver getDriverOfType(WebDriverType webDriverType) {
        WebDriverFactory driverFactory;
        /**Use this code to run in SeleniumGrid*/
        /*DesiredCapabilities caps = new DesiredCapabilities();
        URL gridHubUrl = null;*/
        switch (webDriverType) {
            case CHROME: {
                driverFactory = new ChromeDriverFactory();
                /**Use this code to run in SeleniumGrid*/
                /*caps.setBrowserName("chrome");
                caps.setCapability(ChromeOptions.CAPABILITY, new ChromeOptions().addArguments("incognito"));*/
                break;
            }
            case FIREFOX: {
                driverFactory = new FirefoxDriverFactory();
                /**Use this code to run in SeleniumGrid*/
                /*caps.setBrowserName("firefox");
                caps.setCapability(FirefoxOptions.FIREFOX_OPTIONS, new FirefoxOptions().addArguments("private"));*/
                break;
            }
            default: {
                throw new UnknownDriverTypeException("The specified driver does not exist in the WebDriverType: " + webDriverType);
            }
        }
        /**Use this code to run in SeleniumGrid*/
        /*try {
            gridHubUrl = new URL("http://localhost:4444/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        WebDriver driver = new RemoteWebDriver(gridHubUrl, caps);*/

        WebDriver driver = driverFactory.createDriver();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(TIMEOUT_IN_SEC, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        return driver;
    }

    private static WebDriver getDriverSingleInstance(WebDriverType webDriverType) {
        DriverDecorator decoratedDriver;
        String name = webDriverType.toString();

        if (!instances.containsKey(name)) {
            decoratedDriver = new DriverDecorator(getDriverOfType(webDriverType));
            instances.put(name, decoratedDriver);
        } else {
            decoratedDriver = instances.get(name);
        }
        decoratedDriver.manage().deleteAllCookies();
        return decoratedDriver;
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

    private static void clearInstances() {
        instances.clear();
    }

    public static void closeDriver(WebDriver driver) {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
        clearInstances();
    }
}
