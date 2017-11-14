package com.epam.transavia.demo.core.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverFactory implements WebDriverFactory {

    @Override
    public WebDriver getDriverOf(WebDriverType webDriverType) {

        return new ChromeDriver(setIncognitoMode());
    }

    private static ChromeOptions setIncognitoMode() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("incognito");
        return options;
    }

}
