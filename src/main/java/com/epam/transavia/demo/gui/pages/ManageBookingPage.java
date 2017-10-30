package com.epam.transavia.demo.gui.pages;

import com.epam.transavia.demo.core.exceptions.WrongPageException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.epam.transavia.demo.gui.pages.HomePage.logger;

public class ManageBookingPage {

    private WebDriver driver;

    @FindBy(xpath = "//div[@id='horizontal-sub-navigation-manageyourbooking']//div[contains(@class, 'togglepanel-content')]")
    private WebElement manageYourBookingPanel;

    @FindBy(xpath = "//li//span[contains(@class, 'icon-account')]")
    private WebElement viewYourBookingIcon;

    public ManageBookingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean manageBookingPaneIsOpened(){
        return manageYourBookingPanel.isDisplayed();
    }

    public LoginPage goToViewBooking(){
        if(viewYourBookingIcon.isDisplayed()){
            viewYourBookingIcon.click();
            return new LoginPage(driver);
        } else {
            try {
                throw new WrongPageException("Login page cannot be opened by ViewYourBooking option click");
            } catch (WrongPageException e) {
                logger.error(e.getMessage());
            }
        }
        return null;
    }

}
