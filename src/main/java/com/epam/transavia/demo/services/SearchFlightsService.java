package com.epam.transavia.demo.services;

import com.epam.transavia.demo.business_objects.NewBooking;
import com.epam.transavia.demo.core.driver.Driver;
import com.epam.transavia.demo.gui.pages.BookingPage;
import com.epam.transavia.demo.gui.pages.HomePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class SearchFlightsService {
    private static WebDriver driver = Driver.getDefaultDriver();
    private static Logger logger = LogManager.getLogger();

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

    public void searchDefaultOneWayOneUserFlight() {
        HomePage homePage = new HomePage(driver);
        if (homePage.returnOnIsChecked()) {
            homePage.returnOnCheckBoxClick();
        }
        homePage.searchBtnSubmit();
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
