package com.epam.transavia.demo.tests.steps;

import com.epam.transavia.demo.business_objects.WelcomeLanguages;
import com.epam.transavia.demo.core.driver.Driver;
import com.epam.transavia.demo.gui.pages.HomePage;
import com.epam.transavia.demo.gui.pages.WelcomePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTestBeforeClass {

    protected WebDriver driver;
  //  protected HomePage homePage;

    private static String welcomePageUrl = "https://www.transavia.com/";
    private static Logger testLogger = LogManager.getLogger("test");

    @BeforeClass
    protected void setUpBeforeMethod() {

        //driver = Driver.getDriverByName("chrome");

        //Add checks for UnknowDriverType
      //  Driver.setDefaultDriver("bla-bla");
        driver = Driver.getDriverByName("bla-bla");

      //  driver = Driver.getDefaultDriver();
        //Move to service
        driver.get(welcomePageUrl);
        WelcomePage welcomePage = new WelcomePage(driver);
        HomePage homePage = welcomePage.selectLocaleAndOpenHomePage(WelcomeLanguages.OTHER_COUNTRY);
        testLogger.info("Try to select Other countries locale...");
    }

    protected Logger getTestLogger() {
        return this.testLogger;
    }

    @AfterClass(alwaysRun = true)
    protected void tearDownAfterMethod() {
        /*driver.quit();
        Driver.clearInstances();*/
        Driver.closeDriver(driver);
    }
}
