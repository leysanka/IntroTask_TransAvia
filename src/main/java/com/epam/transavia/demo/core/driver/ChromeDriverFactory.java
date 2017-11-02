package com.epam.transavia.demo.core.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class ChromeDriverFactory implements WebDriverFactory {

    @Override
    public WebDriver getDriverOf(WebDriverType webDriverType) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("incognito");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        return new ChromeDriver(capabilities);
    }
}
