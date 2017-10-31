package com.epam.transavia.demo.gui.pages;

import com.epam.transavia.demo.businessobjects.BookingInfo;
import com.epam.transavia.demo.core.exceptions.WrongPageException;
import org.apache.logging.log4j.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class LoginPage extends CommonPage{

    //private WebDriver driver;
    static Logger logger = LogManager.getLogger();

    private final String LOGIN_PAGE_TITLE = "Log in";

    //View your booking without an account
    @FindBy(xpath = "//input[@id = 'retrieveBookingByLastname_RecordLocator']") private WebElement bookingNumberField;
    @FindBy(xpath = "//input[@id = 'retrieveBookingByLastname_LastName']") private WebElement lastNameField;
    @FindBy(xpath = "//input[@id = 'retrieveBookingByLastname_FlightDate-datepicker']") private WebElement flightDateField;
    @FindBy(xpath = "//button [text()='View booking']") private WebElement viewBookingBtn;


    public LoginPage(WebDriver driver) throws WrongPageException {
        super(driver);
        if (!LOGIN_PAGE_TITLE.equals(driver.getTitle())) {
            throw new WrongPageException("Login page title does not meet to the expected one: " + LOGIN_PAGE_TITLE);
        } else {
            logger.info("Login page initialized successfully.");
        }
    }

    public void setBookingNumberField(String bookingNumber){
        bookingNumberField.sendKeys(bookingNumber);
    }

    public boolean isMatchBookingNumberField(String bookingNumber){
        return bookingNumberField.getAttribute("value").equals(bookingNumber);
    }

    public void setLastNameFieldField(String lastName){
        lastNameField.sendKeys(lastName);
    }

    public boolean isMatchLastNameField(String lastName){
        return lastNameField.getAttribute("value").equals(lastName);
    }


    public void setFlightDateFieldField(String flightDate){
        flightDateField.sendKeys(flightDate);
    }

    public boolean isMatchFlightDateField(String flightDate){
        return flightDateField.getAttribute("value").equals(flightDate);
    }

    public void submitFlightDateAndGoToViewBooking(){
          flightDateField.sendKeys(Keys.ENTER);
    }

    public void pressViewBookingBtn(){
        viewBookingBtn.click();
    }

    public ViewYourBookingPage viewBookingWithoutAccount(BookingInfo bookingInfo) throws WrongPageException {
        this.setBookingNumberField(bookingInfo.getBookingNumber());
        this.setLastNameFieldField(bookingInfo.getLastName());
        this.setFlightDateFieldField(bookingInfo.getFlightDate());
        this.submitFlightDateAndGoToViewBooking();
        return new ViewYourBookingPage(driver);
    }





}
