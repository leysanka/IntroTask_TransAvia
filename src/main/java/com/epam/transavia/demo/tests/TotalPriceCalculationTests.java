package com.epam.transavia.demo.tests;

import com.epam.transavia.demo.tests.steps.BaseTest;
import com.epam.transavia.demo.tests.steps.BookingPageTests;
import com.epam.transavia.demo.tests.steps.HomePageTests;
import org.testng.*;
import org.testng.annotations.Test;

@Test()
public class TotalPriceCalculationTests extends BaseTest {

    //TC_2: Check Total Amount price's calculated correctly for round-trip for '2 Adults , 1 Child and 1 Baby' passengers with Plus Fare (ie. 20kg luggage).

    @Test(testName = "priceIsCorrect")
    public void totalPriceCalculationOneCityRoundTripIsCorrect(){

        HomePageTests homePageTests = new HomePageTests(driver,homePage);
        BookingPageTests bookingPageTests = new BookingPageTests();

        final String DEST_FROM = "Edinburgh, United Kingdom";
        final String DEST_TO = "Paris (Orly South), France";
        final String DEFAULT_PASSENGERS = "1 Adult";
        final int ADULTS_TO_ADD = 1;
        final String TOTAL_PASSENGERS = "2 Adults , 1 Child and 1 Baby";


        homePageTests.testFromDestinationFillsCorrectly(DEST_FROM);
        homePageTests.testToDestinationFillsCorrectly(DEST_TO);
        homePageTests.verifyDefaultDepartOnDateIsCorrect();
        homePageTests.verifyReturnOnIsChecked();
        homePageTests.verifyReturnOnDateIsSet();
        homePageTests.verifyExpectedPassengersCountIsShown(DEFAULT_PASSENGERS);
        homePage.passengersFieldClick();
        homePageTests.verifyPassengersPopUpIsDisaplyed();
        homePageTests.testAddAdultPassengers(ADULTS_TO_ADD);
        homePageTests.testAddChildrenPassenger();
        homePageTests.testAddBabiesPassenger();
        homePageTests.verifyExpectedPassengersCountIsShown(TOTAL_PASSENGERS);
        homePage.searchBtnSubmit();
        bookingPage = bookingPageTests.createBookingPageIfValid(driver);
        bookingPageTests.testSelectFirstInboundOutboundFlights();
        bookingPage.pressNextButton();
        double adultPrice = bookingPage.getTotalPricePerAdultPassenger();
        double babyPrice = bookingPage.getTotalPricePerBaby();
        bookingPage.pressSelectPlusFareBtn();
        double plusFarePrice = bookingPage.getPlusFarePrice();

        double totalPrice = 3*adultPrice + 3*plusFarePrice + 1*babyPrice; //childPrice = adultPrice, thus 3 adults calc. Fare price does not apply to babies.
        Assert.assertEquals(totalPrice, bookingPage.getTotalAmountPrice(),"The 'Total amount' price in the BookingInfo page does not meet to the calculated: " + totalPrice + ";");

        bookingPageTests.verifyTotalPriceIsCorrect(2,1,1);
    }


    //TC_9: Check Total Amount price's calculated correctly for multi-city trip:
    // 'Bologna-Eindhoven' (1st date) Outbound  and  'Amsterdam-Casablanca' (2nd date) Inbound flights
    @Test
    public void totalPriceCalculationMultiCityRoundTripIsCorrect(){

    }
}
