package com.epam.transavia.demo.services;

import com.epam.transavia.demo.business_objects.NewBooking;
import com.epam.transavia.demo.business_objects.PassengersTypes;
import com.epam.transavia.demo.core.driver.Driver;
import com.epam.transavia.demo.core.driver.DriverDecorator;
import com.epam.transavia.demo.core.exceptions.InvalidTestDataException;
import com.epam.transavia.demo.core.exceptions.UnknownPassengerTypeException;
import com.epam.transavia.demo.tests.BaseTestBeforeClass;
import com.epam.transavia.demo.ui.pages.BookingPage;
import com.epam.transavia.demo.ui.pages.HomePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SearchFlightsService {

    private DriverDecorator driver = new DriverDecorator(Driver.getDefaultDriver());
    private static Logger logger = LogManager.getLogger();
    private static final int MAX_PASSENGERS_TO_FILL = 10;

    public boolean navigateToWhereToGoWindow() {

        if (!BaseTestBeforeClass.getHomePageUrl().equals(driver.getCurrentUrl())) {
            driver.navigate().to(BaseTestBeforeClass.getHomePageUrl());
        }
        return (new HomePage(driver).whereToGoWindowIsDisplayed());
    }

    public void setFlyDestinations(NewBooking newBooking) {
        HomePage homePage = new HomePage(driver);
        homePage.setFromDestinationByKeys(newBooking.getFromDestination());
        homePage.setToDestinationByKeys(newBooking.getToDestination());
    }

    public void searchDefaultOneWayOneUserFlight(NewBooking newBooking) {
        HomePage homePage = new HomePage(driver);
        setFlyDestinations(newBooking);
        if (homePage.returnOnIsChecked()) {
            homePage.returnOnCheckBoxClick();
        }
        homePage.searchBtnSubmit();
    }

    public void searchRoundTripWithParameters(NewBooking newBooking) {
        HomePage homePage = new HomePage(driver);
        setFlyDestinations(newBooking);
        if (!homePage.returnOnIsChecked()) {
            homePage.returnOnCheckBoxClick();
        }
        addRequiredPassengers(newBooking);
        setDepartReturnOnDatesIfSpecified(newBooking);
        homePage.searchBtnSubmit();
    }

    public void setDepartReturnOnDatesIfSpecified(NewBooking newBooking) {
        HomePage homePage = new HomePage(driver);
        if (newBooking.getDepartDate() != null) {
            homePage.setDepartOnDateField(newBooking.getDepartDate());
        }
        if (newBooking.getReturnDate() != null) {
            homePage.setReturnOnDateField(newBooking.getReturnDate());
        }
    }

    public void addRequiredPassengers(NewBooking newBooking) {

        addPassengers(newBooking.getAdultsCount(), PassengersTypes.ADULTS);
        addPassengers(newBooking.getChildrenCount(), PassengersTypes.CHILDREN);
        addPassengers(newBooking.getBabiesCount(), PassengersTypes.BABIES);
    }

    private void addPassengers(int passengersCountToAdd, PassengersTypes passengerType) {
        if (passengerType == PassengersTypes.ADULTS) {
            passengersCountToAdd = passengersCountToAdd - 1; //As 1 Adult is set by default in the form and Passengers field is not editable to be cleared.
        }
        if (passengersCountToAdd > 0) {
            if (passengersCountToAdd < MAX_PASSENGERS_TO_FILL) {
                HomePage homePage = new HomePage(driver);
                homePage.passengersPopUpActivate();
                logger.info("Passengers field is activated.");
                for (int i = 1; i <= passengersCountToAdd; i++) {
                    addPassengerOfType(passengerType);
                    logger.info("Passenger added: " + i + " " + passengerType.toString());
                }
            } else {
                throw new InvalidTestDataException("Invalid passengers count to add is specified: " + passengersCountToAdd + " " + passengerType.toString());
            }
        }
    }

    private void addPassengerOfType(PassengersTypes passengerType) {
        HomePage homePage = new HomePage(driver);
        switch (passengerType) {
            case ADULTS:
                homePage.increaseAdultPassenger();
                break;
            case CHILDREN:
                homePage.increaseChildPassenger();
                break;
            case BABIES:
                homePage.increaseBabyPassenger();
                break;
            default:
                throw new UnknownPassengerTypeException("Unknown passenger type: " + passengerType);
        }
    }


    public int fetchAllDateSpinnersShownCount() {
        BookingPage bookingPage = new BookingPage(driver);
        return bookingPage.getAllDatesShownCount();
    }

    public int fetchDateSpinnersWithFlightsCount() {
        BookingPage bookingPage = new BookingPage(driver);
        return bookingPage.getFoundFlightsCount();
    }

    public String fetchBookingError() {
        BookingPage bookingPage = new BookingPage(driver);
        return bookingPage.getSearchFlightError();
    }
}
