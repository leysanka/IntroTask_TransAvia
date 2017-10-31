package com.epam.transavia.demo.tests;

import com.epam.transavia.demo.businessobjects.BookingInfo;
import com.epam.transavia.demo.core.exceptions.PageNotCreatedException;
import com.epam.transavia.demo.core.exceptions.WrongPageException;
import com.epam.transavia.demo.gui.services.ViewBookingService;
import com.epam.transavia.demo.tests.steps.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;


public class ViewBookingWithoutAccountTests extends BaseTest {

    private BookingInfo bookingInfo;

    @Factory(dataProvider = "bookingAndFlightInfoProvider")
    public ViewBookingWithoutAccountTests(String bookingNumber, String lastName, String flightDate, String flyingFrom, String flyingTo) {

        bookingInfo = new BookingInfo();
        bookingInfo.setBookingNumber(bookingNumber);
        bookingInfo.setLastName(lastName);
        bookingInfo.setFlightDate(flightDate);
        bookingInfo.setFlyingFrom(flyingFrom);
        bookingInfo.setFlyingTo(flyingTo);

    }

    @Test
    public void viewBookingWithoutAccountBookingIsLoaded() throws PageNotCreatedException, WrongPageException {

        Assert.assertTrue(new ViewBookingService(driver, homePage).viewBookingWithoutAccountSuccess(bookingInfo),
                "View booking without account did not load the expected booking: " + bookingInfo.getBookingNumber());
    }

    @Test
    public void viewBookingWithoutAccountBookingRouteIsCorrect() throws PageNotCreatedException, WrongPageException {

        ViewBookingService viewBookingService = new ViewBookingService(driver, homePage);
        Assert.assertTrue(viewBookingService.isMatchFlyingFrom(bookingInfo),
                "Flying From destination does not match to the expected: " + bookingInfo.getFlyingFrom());
        Assert.assertTrue(viewBookingService.isMatchFlyingTo(bookingInfo),
                "Flying To destination does not match to the expected: " + bookingInfo.getFlyingTo());
    }

    @Test
    public void viewBookingWithoutAccountBookingArrivalTimeIsCorrect() throws PageNotCreatedException, WrongPageException {

        Assert.assertTrue(new ViewBookingService(driver, homePage).isArrivalTimeLaterThanDepartureInBookingPage(bookingInfo),
                "Arrival time is not after the departure time.");

    }

    @Test
    public void viewBookingWithoutAccountBookingDetailsPaymentIsCorrect() throws WrongPageException, PageNotCreatedException {

        Assert.assertTrue(new ViewBookingService(driver, homePage).isPaymentAmountEqualToTotalPrice(bookingInfo),
                "Payment amount and Total amount do not meet.");
    }


    @DataProvider(name = "bookingAndFlightInfoProvider")
    public static Object[][] bookingAndFlightInfoProvider() {
        return new Object[][]{
                {"MF8C9R", "kukharau", "09 June 2016", "Pisa", "Amsterdam (Schiphol)"},
        };
    }


}
