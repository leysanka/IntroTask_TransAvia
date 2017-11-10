package com.epam.transavia.demo.services;

import com.epam.transavia.demo.core.driver.Driver;
import com.epam.transavia.demo.ui.pages.BookingPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BookingService {

    private WebDriver driver = Driver.getDefaultDriver();
    private static Logger logger = LogManager.getLogger();


    public void selectFirstInboundOutboundFlightsAndNextToFareSelection() {
        BookingPage bookingPage = new BookingPage(driver);
        selectAvailableFlightByOrder(1, bookingPage.getOutboundFlightsFound());
        bookingPage.pressSelectOutboundFlight();
        selectAvailableFlightByOrder(1, bookingPage.getInboundFlightsFound());
        bookingPage.pressSelectInboundFlight();
        bookingPage.pressNextButton();
    }

    public double fetchPricePerAdult() {

        return new BookingPage(driver).getTotalPricePerAdultPassenger();
    }

    public double fetchPricePerBaby() {

        return new BookingPage(driver).getTotalPricePerBaby();
    }

    public double selectAndFetchPlusFarePrice() {
        BookingPage bookingPage = new BookingPage(driver);
        bookingPage.pressSelectPlusFareBtn();
        return bookingPage.getPlusFarePrice();
    }

    public double fetchTotalAmountPrice() {
        return new BookingPage(driver).getTotalAmountPrice();
    }


    private void selectAvailableFlightByOrder(int order, List<WebElement> availableFlights) {
        if (order > 0) {
            int index = order - 1;
            if (availableFlights.size() >= order) {
                availableFlights.get(index).click();
                //  new WebDriverWait(driver, 5).until(ExpectedConditions.jsReturnsValue("return jQuery.active == 0;"));
                logger.info(" Flight selected successfully.");
            } else {
                logger.error("Cannot select the specified flight by order: " + order);
            }
        } else {
            logger.error("Invalid negative index is specified for flights' webelement list.");
        }
    }
}
/*

    public void selectOutboundFlight(int flightNumber) {
        int index = flightNumber - 1;

        if (flightNumber > 0) {
            if (!outboundFlights.isEmpty() && outboundFlights.size() >= flightNumber) {
                outboundFlights.get(index).click();
                logger.info(flightNumber + " Outbound flight chosen successfully.");
            } else logger.error("Cannot select the specified outbound flight by order: " + flightNumber);
        } else logger.error("Invalid negative index is specified for flights' webelement list.");
    }*/
