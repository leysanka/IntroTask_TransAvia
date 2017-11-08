package com.epam.transavia.demo.services;

import com.epam.transavia.demo.business_objects.NewBooking;
import com.epam.transavia.demo.business_objects.PassengersTypes;
import com.epam.transavia.demo.core.driver.Driver;
import com.epam.transavia.demo.core.exceptions.InvalidTestDataException;
import com.epam.transavia.demo.gui.pages.BookingPage;
import com.epam.transavia.demo.gui.pages.HomePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class SearchFlightsService {
    private static WebDriver driver = Driver.getDefaultDriver();
    private static Logger logger = LogManager.getLogger();
    private static final int MAX_PASSENGERS_TO_FILL = 10;

    public boolean navigateToWhereToGoWindow() {
        if (!HomePage.getHomePageEnEuUrl().equals(driver.getCurrentUrl())) {
            driver.navigate().to(HomePage.getHomePageEnEuUrl());
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
            passengersCountToAdd = passengersCountToAdd - 1; //As 1 Adult is set by default in page and Passengers field is not editable to be cleared.
        }
        if (passengersCountToAdd > 0 && passengersCountToAdd < MAX_PASSENGERS_TO_FILL) {
            HomePage homePage = new HomePage(driver);
            homePage.passengersFieldActivate();
            logger.info("Passengers field is activated.");
            for (int i = 0; i < passengersCountToAdd; i++) {
                addPassengerOfType(passengerType);
                logger.info("Passenger added: " + i + passengerType.toString());
            }
        } else {
            throw new InvalidTestDataException("Invalid passengers count to add is specified: " + passengersCountToAdd + passengerType.toString());
        }

    }

    private void addPassengerOfType(PassengersTypes passengerType) {
        HomePage homePage = new HomePage(driver);
        switch (passengerType) {
            case ADULTS:
                homePage.addAdultPassenger();
                break;
            case CHILDREN:
                homePage.addChildPassenger();
                break;
            case BABIES:
                homePage.addBabyPassenger();
                break;
            default:
                throw new IllegalArgumentException("Unknow passenger type: " + passengerType);
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
