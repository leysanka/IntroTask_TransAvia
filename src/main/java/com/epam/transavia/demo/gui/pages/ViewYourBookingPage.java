package com.epam.transavia.demo.gui.pages;

import com.epam.transavia.demo.businessobjects.BookingInfo;
import com.epam.transavia.demo.core.exceptions.WrongPageException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class ViewYourBookingPage extends CommonPage {

 //   private WebDriver driver;
    private final String VIEW_BOOKING_PAGE_TITLE = "View your booking";
    private static Logger logger = LogManager.getLogger();

    @FindBy(xpath = "//em//time") private List<WebElement> deptAndArrivalTimes;
    @FindBy(xpath = "//h5[contains(text(), 'Duration')]//following-sibling::p") private WebElement duration;
    @FindBy(xpath = "//h3//span[@class='nowrap']") private List<WebElement> flyingRoute;
    @FindBy(xpath = "//div[contains(@class, 'section--button')]//a[contains(@href, 'booking-details')]") private WebElement bookingDetailsBtn;
    @FindBy(xpath = "//div[@class = 'footnote booking-number']//p") private WebElement bookingNumberText;

    public ViewYourBookingPage(WebDriver driver) throws WrongPageException{
        super(driver);
        if (!VIEW_BOOKING_PAGE_TITLE.equals(driver.getTitle())){
            throw new WrongPageException("View your booking page title does not meet to the expected: " + VIEW_BOOKING_PAGE_TITLE);
        } else {
            logger.info("View your booking page initialized successfully.");
        }
    }

    public String getDepartureTime(){
        return deptAndArrivalTimes.get(0).getAttribute("datetime");
    }

    public String getArrivalTime(){
        return deptAndArrivalTimes.get(1).getAttribute("datetime");
    }

    public String getShownBookingNumber() {
        return bookingNumberText.getText();
    }

    public String getFlyingFrom(){
        return flyingRoute.get(0).getText();
    }

    public String getFlyingTo(){
        return flyingRoute.get(1).getText();
    }


    public void pressBookingDetailsBtn(){
        bookingDetailsBtn.click();
    }

    public BookingDetailsPage openBookingDetails() throws WrongPageException {
        bookingDetailsBtn.click();
        return new BookingDetailsPage(driver);
    }

}
