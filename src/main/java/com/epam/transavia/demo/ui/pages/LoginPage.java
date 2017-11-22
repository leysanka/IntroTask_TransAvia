package com.epam.transavia.demo.ui.pages;

import com.epam.transavia.demo.business_objects.BookingDetailsInfo;
import com.epam.transavia.demo.core.exceptions.WrongPageException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class LoginPage extends CommonPage {

    private static final String LOGIN_PAGE_TITLE = "Log in";

    //View your booking without an account
    @FindBy(xpath = "//input[@id = 'retrieveBookingByLastname_RecordLocator']")
    private WebElement bookingNumberField;
    @FindBy(xpath = "//input[@id = 'retrieveBookingByLastname_LastName']")
    private WebElement lastNameField;
    @FindBy(xpath = "//input[@id = 'retrieveBookingByLastname_FlightDate-datepicker']")
    private WebElement flightDateField;
    @FindBy(xpath = "//button [text()='View booking']")
    private WebElement viewBookingBtn;


    public LoginPage(WebDriver driver) {
        super(driver);
        if (!LOGIN_PAGE_TITLE.equals(driver.getTitle())) {
            logger.error("Login page title does not meet to the expected one: " + LOGIN_PAGE_TITLE + " Found: " + driver.getTitle());
            throw new WrongPageException("Login page title does not meet to the expected one: " + LOGIN_PAGE_TITLE);
        }
    }

    public void setBookingNumberField(String bookingNumber) {
        bookingNumberField.clear();
        bookingNumberField.sendKeys(bookingNumber);
    }

    public void setLastNameFieldField(String lastName) {
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
    }

    public void setFlightDateFieldField(String flightDate) {
        flightDateField.clear();
        flightDateField.sendKeys(flightDate);
    }

    public void submitFlightDateAndGoToViewBooking() {
        flightDateField.sendKeys(Keys.ENTER);
    }

    public ViewBookingPage viewBookingWithoutAccount(BookingDetailsInfo bookingDetailsInfo) {
        setBookingNumberField(bookingDetailsInfo.getBookingNumber());
        setLastNameFieldField(bookingDetailsInfo.getLastName());
        setFlightDateFieldField(bookingDetailsInfo.getFlightDate());
        submitFlightDateAndGoToViewBooking();
        return new ViewBookingPage(driver);
    }

}
