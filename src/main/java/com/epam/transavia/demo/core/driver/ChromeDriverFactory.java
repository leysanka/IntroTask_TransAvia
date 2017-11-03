package com.epam.transavia.demo.core.driver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

public class ChromeDriverFactory implements WebDriverFactory {
    private static final int TIMEOUT_IN_SEC = 10;

    @Override
    public WebDriver getDriverOf(WebDriverType webDriverType) {

        return new ChromeDriver(setChromeIncognitoCapability());
    }

    private static Capabilities setChromeIncognitoCapability(){

        ChromeOptions options = new ChromeOptions();
        options.addArguments("incognito");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        return capabilities;
    }

    private static void chromeBrowserSetUp(WebDriver driver) {

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(TIMEOUT_IN_SEC, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }


}
