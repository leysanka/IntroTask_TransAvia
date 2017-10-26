package com.epam.transavia.demo.tests.pages;

import com.epam.transavia.demo.gui.pages.HomePage;
import com.epam.transavia.demo.core.exceptions.InvalidTestDataException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomePageTests extends BaseTest {

    public HomePageTests() {    }

    public HomePageTests(WebDriver driver, HomePage homePage) {
        this.driver = driver;
        this.homePage=homePage;
    }

    public void testFromDestinationFillsCorrectly(String destination) {
        homePage.setFromDestinationByKeys(destination);
        Assert.assertTrue(homePage.isMatchFromDestinationFieldText(destination), "FROM destination does not match.");
    }

    public void testToDestinationFillsCorrectly(String destination) {
        homePage.setToDestinationByKeys(destination);
        Assert.assertTrue(homePage.isMatchToDestinationFieldText(destination), "TO destination does not match.");
    }

    public void testDepartOnDateFillsCorrectly(long daysLag) {
        String actualDepartOnDate;
        String expectedDepartOnDate = HomePage.calculateDateNowPlusLag(daysLag);

        homePage.setDepartOnDateFieldPlusLag(daysLag);
        actualDepartOnDate = homePage.getSelectedDepartOnDate();

        Assert.assertEquals(actualDepartOnDate, expectedDepartOnDate,
                "DepartOn date does not meet: set " + actualDepartOnDate + ", while expected is " + expectedDepartOnDate);

    }

    public void testReturnOnDateFillsCorrectly(long daysLag) {
        String actualReturnOnDate;
        String expectedReturnOnDate = HomePage.calculateDateNowPlusLag(daysLag);

        homePage.setReturnOnDateFieldPlusLag(daysLag);
        actualReturnOnDate = homePage.getSelectedReturnOnDate();
        Assert.assertEquals(actualReturnOnDate, expectedReturnOnDate,
                "DepartOn date does not meet: set " + actualReturnOnDate + ", while expected is " + expectedReturnOnDate);
    }


    public void verifyReturnOnIsChecked() {
        Assert.assertTrue(homePage.returnOnIsChecked(), "Return On checkbox is unchecked, while expected to be checked.");
    }

    public void verifyReturnOnIsUnchecked() {
        Assert.assertTrue(!homePage.returnOnIsChecked(), "Return On checkbox should be checked.");
    }

    public void verifyExpectedPassengersCountIsShown(String expPassengers) {
        Assert.assertTrue(homePage.isCorrectPassengersCountShown(expPassengers),
                "Passengers count shown does not meet to the expected: " + expPassengers + ".");
    }

    public void testAddAdultPassengers(int adultsToAdd) {
        try {
            homePage.addAdultPassengers(adultsToAdd);
        } catch (InvalidTestDataException e) {
            testLogger.error(e.getMessage());
        }
        //1 adult is set by default, so total added passenger will be adultsToAdd + 1
        Assert.assertEquals(((Integer) (adultsToAdd + 1)).toString(), getPassengersCountBoxValue("adults"),
                "Unexpected adult passengers set, expected: " + (adultsToAdd + 1) + ".");

    }

    public void testAddChildrenPassenger() {
        if (homePage.isVisiblePassengersPopup()) {
        }
        homePage.addChildrenPassenger();
        testLogger.info("Child added");
    }

    public void testAddBabiesPassenger() {
        if (homePage.isVisiblePassengersPopup()) {
        }
        homePage.addBabyPassenger();
        testLogger.info("Baby added");
    }

    public String getPassengersCountBoxValue(String passengerType) {
        String value = "";
        try {
            value = homePage.getPassengersCountBoxValue(passengerType);
        } catch (IllegalArgumentException e) {
            testLogger.error(e.getMessage());
        }
        return value;
    }

    public void verifyPassengersPopUpIsDisaplyed() {
        Assert.assertTrue(homePage.isVisiblePassengersPopup(), "Passengers pop up is not displayed.");
    }

    public void verifyReturnOnDateIsSet() {
        Assert.assertTrue(homePage.returnOnDateIsSet(), "Return On date is empty.");
    }

    // Depart On should display tomorrow's date by default
    public void verifyDefaultDepartOnDateIsCorrect() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

        String fieldDate = homePage.getSelectedDepartOnDate();
        String currentDate = LocalDate.now().plusDays(1).format(formatter);
        Assert.assertEquals(fieldDate, currentDate, "Depart On date " + fieldDate + " does not meet to the expected default today's " + currentDate + ".");
    }

    @DataProvider(name = "TC1_Provider")
    public Object[][] tc_1_Provider() {
        return new Object[][]{
                {"Dubrovnik, Croatia", "Amsterdam (Schiphol), Netherlands", 7, "1 Adult"},
                // {"Amsterdam (Schiphol), Netherlands", "Dubrovnik, Croatia",14, "2"}
        };
    }

    public void testOpenManageBookingToolbar() {
        homePage.openManageBookingToolbar();
        Assert.assertTrue(homePage.manageBookingPaneIsOpened(), "'Manage your booking' toggle panel does not look to be opened.");
    }

    public void testGoToViewBooking() {
        homePage.goToViewBooking();
    }
}
