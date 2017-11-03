package com.epam.transavia.demo.gui.pages;

import com.epam.transavia.demo.core.exceptions.WrongPageException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WelcomePage extends CommonPage {

    private static final String WELCOME_PAGE_TITLE = "Welcome to Transavia!";

    @FindBy(xpath = "//a[@href='/en-EU/home']")
    private WebElement welcomeOtherCountries;


    public WelcomePage(WebDriver driver) {
        super(driver);
        if (!WELCOME_PAGE_TITLE.equals(driver.getTitle())) {
            throw new WrongPageException("Welcome screen title does not match to the expected: " + WELCOME_PAGE_TITLE);
        } else {
            logger.info("Welcome page initialized successfully.");
        }
    }
}
