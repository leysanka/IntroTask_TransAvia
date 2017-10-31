package com.epam.transavia.demo.gui.pages;

import com.epam.transavia.demo.core.exceptions.PageNotCreatedException;
import com.epam.transavia.demo.core.exceptions.WrongPageException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class ManageBookingPage extends CommonPage {

    @FindBy(xpath = "//div[@id='horizontal-sub-navigation-manageyourbooking']//div[contains(@class, 'togglepanel-content')]")
    private WebElement manageYourBookingPanel;
    @FindBy(xpath = "//li//span[contains(@class, 'icon-account')]")
    private WebElement viewYourBookingIcon;

    public ManageBookingPage(WebDriver driver) {
        super(driver);
    }

    public boolean manageBookingPanelIsOpened(){
        return manageYourBookingPanel.isDisplayed();
    }

    public LoginPage goToViewBooking() throws PageNotCreatedException, WrongPageException {
        if (viewYourBookingIcon.isDisplayed()) {
            viewYourBookingIcon.click();
            return new LoginPage(driver);
        } else {
            throw new PageNotCreatedException("Login page has not been created by ViewYourBooking option click");
        }
    }
}
