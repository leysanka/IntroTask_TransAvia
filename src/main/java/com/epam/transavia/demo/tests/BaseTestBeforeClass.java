package com.epam.transavia.demo.tests;

import com.epam.transavia.demo.business_objects.WelcomeScreenLanguages;
import com.epam.transavia.demo.core.driver.Driver;
import com.epam.transavia.demo.ui.pages.HomePage;
import com.epam.transavia.demo.ui.pages.WelcomePage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class BaseTestBeforeClass {

    protected  WebDriver driver;

    private static final String WELCOME_PAGE_URL = "https://www.transavia.com/";
    private static String homePageUrl;
    private static WelcomeScreenLanguages defaultLocale = WelcomeScreenLanguages.OTHER_COUNTRY;

    @BeforeSuite
    protected void setUpInitial() {

        driver = Driver.getDriverByName("chrome");
        driver.get(WELCOME_PAGE_URL);
        WelcomePage welcomePage = new WelcomePage(driver);
        HomePage homePage = welcomePage.selectLocaleAndOpenHomePage(defaultLocale);
        homePageUrl = driver.getCurrentUrl();
    }

    @BeforeClass
    protected void navigateToHomePage() {

        driver = Driver.getDefaultDriver();
        if (!driver.getCurrentUrl().equals(homePageUrl)) {
            driver.navigate().to(homePageUrl);
        }
        HomePage homePage = new HomePage(driver);
    }


    public static String getHomePageUrl() {
        return homePageUrl;
    }

    @AfterSuite(alwaysRun = true)
    protected void tearDownAfterMethod() {

        Driver.closeDriver(driver);
    }
}
