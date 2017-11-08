package com.epam.transavia.demo.tests.steps_old;

import com.epam.transavia.demo.gui.pages.BookingPage;
import com.epam.transavia.demo.core.exceptions.WrongPageException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class BookingPageTests extends BaseTestBeforeMethod {

    public BookingPage createBookingPageIfValid(WebDriver driver) {
        try {
            bookingPage = new BookingPage(driver);
        } catch (WrongPageException e) {
            testLogger.error(e);
        }

        Assert.assertNotNull(bookingPage, "BookingDetailsInfo page object is not created: title did not meet.");
        return bookingPage;
    }


    public void testSelectFirstInboundOutboundFlights() {
        bookingPage.selectOutboundFlight(1);
        bookingPage.pressSelectOutboundFlight();
        bookingPage.selectInboundFlight(1);
        bookingPage.pressSelectInboundFlight();

        Assert.assertEquals(2, bookingPage.getSelectedFlightsCount(), "Selected flights count must equal 2");
        testLogger.info("Out and Inbound flights selection test passed.");

    }

    public void verifyTotalPriceIsCorrect(int adults, int children, int babies) {
        double priceClass, pricePerAdult, pricePerChild, pricePerBaby, totalPrice;

        pricePerAdult = bookingPage.getTotalPricePerAdultPassenger();
        pricePerChild = pricePerAdult;
        pricePerBaby = bookingPage.getTotalPricePerBaby();

        totalPrice = adults * pricePerAdult + children * pricePerChild + babies * pricePerBaby;
        Assert.assertEquals(totalPrice, bookingPage.getTotalAmountPrice(), "The 'Total amount' price in the page does not meet to the calculated: " + totalPrice + ";");

    }


}
