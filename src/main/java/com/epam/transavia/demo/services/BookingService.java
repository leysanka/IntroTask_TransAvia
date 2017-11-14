package com.epam.transavia.demo.services;

import com.epam.transavia.demo.core.driver.Driver;
import com.epam.transavia.demo.core.driver.DriverDecorator;
import com.epam.transavia.demo.ui.pages.BookingPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BookingService {

    private DriverDecorator driver = new DriverDecorator(Driver.getDefaultDriver());
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
                logger.info(" Flight selected successfully.");
            } else {
                logger.error("Cannot select the specified flight by order: " + order);
            }
        } else {
            logger.error("Invalid negative index is specified for flights' webelement list.");
        }
    }
}

