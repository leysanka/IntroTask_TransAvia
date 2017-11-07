package com.epam.transavia.demo.tests.steps;

import com.epam.transavia.demo.gui.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginPageTests extends BaseTestBeforeMethod {

    private LoginPage loginPage;

    public LoginPageTests(WebDriver driver, LoginPage loginPage) {
        this.driver = driver;
        this.loginPage = loginPage;
    }

    public void testBookingNumberFillsCorrectly(String bookingNumber) {
        loginPage.setBookingNumberField(bookingNumber);
        Assert.assertTrue(loginPage.isMatchBookingNumberField(bookingNumber), "BookingDetailsInfo Number does not match to the expected value: " + bookingNumber);
    }

    public void testLastNameFillsCorrectly(String lastName) {
        loginPage.setLastNameFieldField(lastName);
        Assert.assertTrue(loginPage.isMatchLastNameField(lastName), "Last Name does not match to the expected value: " + lastName);
    }

    public void testFlightDateFillsCorrectly(String flightDate) {
        loginPage.setFlightDateFieldField(flightDate);
        Assert.assertTrue(loginPage.isMatchFlightDateField(flightDate), "Flight Date does not match to the expected value: " + flightDate);

    }

}
