package com.epam.transavia.demo.tests.pages;

import com.epam.transavia.demo.gui.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginPageTests extends BaseTest {

    private LoginPage loginPage;

    public LoginPageTests(WebDriver driver, LoginPage loginPage) {
        this.driver = driver;
        this.loginPage = loginPage;
    }


    public void verifyPageTitleIsCorrect(){
       Assert.assertTrue(loginPage.isMatchPageTitle());
    }

    public void testBookingNumberFillsCorrectly(String bookingNumber){
        loginPage.setBookingNumberField(bookingNumber);
        Assert.assertTrue(loginPage.isMatchBookingNumberField(bookingNumber), "Booking Number does not match to the expected value: " + bookingNumber);
    }

    public void testLastNameFillsCorrectly(String lastName){
        loginPage.setLastNameFieldField(lastName);
        Assert.assertTrue(loginPage.isMatchLastNameField(lastName), "Last Name does not match to the expected value: " + lastName);
    }

    public void testFLightDateFillsCorrectly(String flightDate){
        loginPage.setFlightDateFieldField(flightDate);
        Assert.assertTrue(loginPage.isMatchFlightDateField(flightDate), "Flight Date does not match to the expected value: " + flightDate);

    }


}
