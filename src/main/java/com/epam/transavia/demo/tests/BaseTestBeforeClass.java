package com.epam.transavia.demo.tests;

import com.epam.transavia.demo.business_objects.WelcomeScreenLanguages;
import com.epam.transavia.demo.core.driver.Driver;
import com.epam.transavia.demo.gui.pages.HomePage;
import com.epam.transavia.demo.gui.pages.WelcomePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

public class BaseTestBeforeClass {

    protected WebDriver driver;

    private static String welcomePageUrl = "https://www.transavia.com/";
    private static Logger testLogger = LogManager.getLogger("test");

    @BeforeClass
    protected void setUpBeforeMethod() {

        driver = Driver.getDefaultDriver();
        //Move to service
        driver.get(welcomePageUrl);
        WelcomePage welcomePage = new WelcomePage(driver);
        testLogger.info("Try to select Other countries locale...");
        HomePage homePage = welcomePage.selectLocaleAndOpenHomePage(WelcomeScreenLanguages.OTHER_COUNTRY);
    }

    protected Logger getTestLogger() {
        return testLogger;
    }

    @AfterSuite()
    protected void tearDownAfterMethod() {

        Driver.closeDriver(driver);
    }
}
