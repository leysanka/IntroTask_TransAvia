package com.epam.transavia.demo.gui.pages;

import com.epam.transavia.demo.businessobjects.BookingInfo;
import com.epam.transavia.demo.core.exceptions.WrongPageException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class LoginPage extends CommonPage{

    private final String LOGIN_PAGE_TITLE = "Log in";
    static Logger logger = LogManager.getLogger();

    //View your booking without an account
    @FindBy(xpath = "//input[@id = 'retrieveBookingByLastname_RecordLocator']") private WebElement bookingNumberField;
    @FindBy(xpath = "//input[@id = 'retrieveBookingByLastname_LastName']") private WebElement lastNameField;
    @FindBy(xpath = "//input[@id = 'retrieveBookingByLastname_FlightDate-datepicker']") private WebElement flightDateField;
    @FindBy(xpath = "//button [text()='View booking']") private WebElement viewBookingBtn;


    public LoginPage(WebDriver driver)  {
        super(driver);
        if (!LOGIN_PAGE_TITLE.equals(driver.getTitle())) {
            throw new WrongPageException("Login page title does not meet to the expected one: " + LOGIN_PAGE_TITLE);
        } else {
            logger.info("Login page initialized successfully.");
        }
    }

    public void setBookingNumberField(String bookingNumber) {
        bookingNumberField.sendKeys(bookingNumber);
    }

    public boolean isMatchBookingNumberField(String bookingNumber) {
        return bookingNumberField.getAttribute("value").equals(bookingNumber);
    }

    public void setLastNameFieldField(String lastName) {
        lastNameField.sendKeys(lastName);
    }

    public boolean isMatchLastNameField(String lastName) {
        return lastNameField.getAttribute("value").equals(lastName);
    }


    public void setFlightDateFieldField(String flightDate) {
        flightDateField.sendKeys(flightDate);
    }

    public boolean isMatchFlightDateField(String flightDate) {
        return flightDateField.getAttribute("value").equals(flightDate);
    }

    public void submitFlightDateAndGoToViewBooking() {
        flightDateField.sendKeys(Keys.ENTER);
    }

    public ViewYourBookingPage viewBookingWithoutAccount(BookingInfo bookingInfo) {
        setBookingNumberField(bookingInfo.getBookingNumber());
        setLastNameFieldField(bookingInfo.getLastName());
        setFlightDateFieldField(bookingInfo.getFlightDate());
        submitFlightDateAndGoToViewBooking();
        return new ViewYourBookingPage(driver);
    }

}
