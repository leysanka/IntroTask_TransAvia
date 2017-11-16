package com.epam.transavia.demo.services;

import com.epam.transavia.demo.core.driver.Driver;
import com.epam.transavia.demo.core.exceptions.TextParseHelperException;
import com.epam.transavia.demo.ui.pages.BookingPage;
import com.epam.transavia.demo.util.TextParseHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BookingService {

    private WebDriver driver = Driver.getDefaultDriver();
    private static Logger logger = LogManager.getLogger(BookingService.class.getSimpleName());


    public void selectFirstInboundOutboundFlightsAndNextToFareSelection() {
        BookingPage bookingPage = new BookingPage(driver);
        selectAvailableFlightByOrder(1, bookingPage.getFoundOutboundFlights());
        bookingPage.pressSelectOutboundFlight();
        selectAvailableFlightByOrder(1, bookingPage.getFoundInboundFlights());
        bookingPage.pressSelectInboundFlight();
        bookingPage.pressNextButton();
    }

    public double getPricePerAdult() {

        return calculateTotalPricePerPassenger(new BookingPage(driver).getListPricesPerAdultPassenger());
    }

    public double getPricePerBaby() {

        return calculateTotalPricePerPassenger(new BookingPage(driver).getListPricesPerBabyPassenger());
    }

    /**
     * Plus Fare price is fetched from page as text "+$48",
     * thus need to be parsed to be used in calculation verification further.
     */
    public double selectAndFetchPlusFarePrice() {
        BookingPage bookingPage = new BookingPage(driver);
        bookingPage.pressSelectPlusFareBtn();
        try {
            return TextParseHelper.takePriceFromText(bookingPage.getPlusFarePriceContainerText());
        } catch (TextParseHelperException e) {
            logger.error(e.getMessage());
            return 0;
        }
    }

    /**
     * Price per each passenger may consist of a list of different prices. They are fetched from Booking Page,
     * as a String list for each WebElement of WebElements list. And here calculate double totalPrice from String list,
     * to use it in calculations' verification further.
     */
    private double calculateTotalPricePerPassenger(List<String> textPricesList) {
        double totalPrice = 0;
        for (String textItem : textPricesList) {
            try {
                totalPrice += TextParseHelper.takePriceFromText(textItem);
            } catch (TextParseHelperException e) {
                logger.error(e.getMessage());
            }
        }
        logger.info("Total price passenger is: " + totalPrice);
        return totalPrice;
    }


    public double fetchTotalAmountPrice() {
        try {
            return TextParseHelper.takePriceFromText(new BookingPage(driver).getTotalAmountPriceValue());
        } catch (TextParseHelperException e) {
            logger.error(e.getMessage());
        }
        return 0;
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

