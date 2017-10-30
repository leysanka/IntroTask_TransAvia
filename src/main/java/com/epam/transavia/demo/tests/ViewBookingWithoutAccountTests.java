package com.epam.transavia.demo.tests;

import com.epam.transavia.demo.businessobjects.BookingInfo;
import com.epam.transavia.demo.core.exceptions.PageNotCreatedException;
import com.epam.transavia.demo.core.exceptions.WrongPageException;
import com.epam.transavia.demo.gui.pages.BookingDetailsPage;
import com.epam.transavia.demo.gui.pages.LoginPage;
import com.epam.transavia.demo.gui.pages.ManageBookingPage;
import com.epam.transavia.demo.gui.pages.ViewYourBookingPage;
import com.epam.transavia.demo.tests.steps.BaseTest;
import com.epam.transavia.demo.tests.steps.LoginPageTests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class ViewBookingWithoutAccountTests extends BaseTest {

    private BookingInfo bookingInfo;

    @Test(dataProvider = "bookingAndFlightInfoProvider")
    public void viewBookingWithoutAccountFlightInfoIsCorrect(String bookingNumber,
                                                             String lastName, String flightDate, String flyingFrom, String flyingTo) throws PageNotCreatedException, WrongPageException {
        bookingInfo = new BookingInfo();
        bookingInfo.setBookingNumber(bookingNumber);
        bookingInfo.setLastName(lastName);
        bookingInfo.setFlightDate(flightDate);
        bookingInfo.setFlyingFrom(flyingFrom);
        bookingInfo.setFlyingTo(flyingTo);

        ViewYourBookingPage viewYourBookingPage = homePage.openManageBookingToolbar().goToViewBooking().viewBookingWithoutAccount(bookingInfo);

        Assert.assertTrue(viewYourBookingPage.isMatchFlyingRoute(bookingInfo), "Flying route does not meet to the expected: " + bookingInfo.getFlyingRoute());
        Assert.assertTrue(viewYourBookingPage.isArrivalTimeLaterThanDeparture(), "Arrival time is not after the Departure time.");

    }

    @Test(dependsOnMethods = {"viewBookingWithoutAccountFlightInfoIsCorrect"})
    public void viewBookingWithoutAccountBookingDetailsPaymentIsCorrect() throws WrongPageException, PageNotCreatedException {

        BookingDetailsPage bookingDetailsPage =
                homePage.openManageBookingToolbar().goToViewBooking().viewBookingWithoutAccount(bookingInfo).openBookingDetails();

        Assert.assertTrue(bookingDetailsPage.isPaymentAmountEqualToTotalPrice(), "Payment amount and Total amount do not meet.");
    }

    @Test(dataProvider = "bookingAndFlightInfoProvider", enabled = false)
    public void oldViewBookingWithoutAccountArrivalTimeIsCorrect(String bookingNumber, String lastName, String flightDate, String flyingFrom, String flyingTo) throws PageNotCreatedException, WrongPageException {
        // HomePageTests homePageTests = new HomePageTests(driver,homePage);
        ManageBookingPage manageBookingPage = homePage.openManageBookingToolbar();
        // homePageTests.testOpenManageBookingToolbar();
        LoginPage loginPage = manageBookingPage.goToViewBooking();
        // LoginPage loginPage = new LoginPage(driver);
        LoginPageTests loginPageTests = new LoginPageTests(driver, loginPage);
      //  loginPageTests.verifyPageTitleIsCorrect();
        loginPageTests.testBookingNumberFillsCorrectly(bookingNumber);
        loginPageTests.testLastNameFillsCorrectly(lastName);
        loginPageTests.testFlightDateFillsCorrectly(flightDate);
        loginPage.submitFlightDateAndGoToViewBooking(); //cannot click View button as it's overlapped with opened calendar, thus using Enter in flightDate field
        ViewYourBookingPage viewYourBookingPage = new ViewYourBookingPage(driver);
        Assert.assertEquals(viewYourBookingPage.getFlyingFrom(), flyingFrom, "Flying From destination does not meet to the expected: " + flyingFrom);
        Assert.assertEquals(viewYourBookingPage.getFlyingTo(), flyingTo, "Flying To destination does not meet to the expected: " + flyingTo);
        Assert.assertNotNull(viewYourBookingPage.getDepartureTime(), "Departure time is not fetched.");
        Assert.assertNotNull(viewYourBookingPage.getArrivalTime(), "Arrival time is not fetched.");
        Assert.assertTrue(viewYourBookingPage.isArrivalTimeLaterThanDeparture(), "Arrival time is not after the Departure time.");
    }


    @Test(dataProvider = "bookingInfoProvider", enabled = false)
    public void oldViewBookingWithoutAccountBookingDetailsPaymentIsCorrect(String bookingNumber, String lastName, String flightDate) throws WrongPageException, PageNotCreatedException {
        // HomePageTests homePageTests = new HomePageTests(driver, homePage);
        // homePageTests.testOpenManageBookingToolbar();
        // homePageTests.testGoToViewBooking();
        ManageBookingPage manageBookingPage = homePage.openManageBookingToolbar();
        LoginPage loginPage = manageBookingPage.goToViewBooking();
        //LoginPage loginPage = new LoginPage(driver);
        LoginPageTests loginPageTests = new LoginPageTests(driver, loginPage);
      //  loginPageTests.verifyPageTitleIsCorrect();
        loginPageTests.testBookingNumberFillsCorrectly(bookingNumber);
        loginPageTests.testLastNameFillsCorrectly(lastName);
        loginPageTests.testFlightDateFillsCorrectly(flightDate);
        loginPage.submitFlightDateAndGoToViewBooking(); //cannot click View button as it's overlapped with opened calendar, thus using Enter in flightDate field
        ViewYourBookingPage viewYourBookingPage = new ViewYourBookingPage(driver);
        viewYourBookingPage.pressBookingDetailsBtn();
        BookingDetailsPage bookingDetailsPage = new BookingDetailsPage(driver);

        Assert.assertTrue(bookingDetailsPage.isPaymentAmountEqualToTotalPrice(), "Payment amount and Total amount do not meet.");
    }


    @DataProvider(name = "bookingAndFlightInfoProvider")
    public Object[][] bookingAndFlightInfoProvider() {
        return new Object[][]{
                {"MF8C9R", "kukharau", "09 June 2016", "Pisa", "Amsterdam (Schiphol)"},
        };
    }

    @DataProvider(name = "bookingInfoProvider")
    public Object[][] bookingInfoProvider() {
        return new Object[][]{
                {"MF8C9R", "kukharau", "09 June 2016"},
        };
    }


}
