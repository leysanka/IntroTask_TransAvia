import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomePageTests extends BaseTest {

    BookingPageTests bookingPageTests = new BookingPageTests();

/*    public HomePageTests(WebDriver driver) {
        this.driver = driver;
    }*/

    public void testFromDestinationFillsCorrectly(String destination){
        homePage.setFromDestinationByKeys(destination);
        Assert.assertTrue(homePage.isMatchFromDestinationFieldText(destination), "FROM destination does not match.");
    }

    public void testToDestinationFillsCorrectly(String destination){
        homePage.setToDestinationByKeys(destination);
        Assert.assertTrue(homePage.isMatchToDestinationFieldText(destination), "TO destination does not match.");
    }

    public void testDepartOnDateFillsCorrectly(long daysLag){
        String actualDepartOnDate;
        String expectedDepartOnDate = HomePage.calculateDateNowPlusLag(daysLag);

        homePage.setDepartOnDateFieldPlusLag(daysLag);
        actualDepartOnDate = homePage.getSelectedDepartOnDate();

        Assert.assertEquals(actualDepartOnDate,expectedDepartOnDate ,
                "DepartOn date does not meet: set "+actualDepartOnDate+ ", while expected is " + expectedDepartOnDate);

    }

    public void testReturnOnDateFillsCorrectly(long daysLag){
        String actualReturnOnDate;
        String expectedReturnOnDate = HomePage.calculateDateNowPlusLag(daysLag);

        homePage.setReturnOnDateFieldPlusLag(daysLag);
        actualReturnOnDate = homePage.getSelectedReturnOnDate();
        Assert.assertEquals(actualReturnOnDate,expectedReturnOnDate ,
                "DepartOn date does not meet: set "+actualReturnOnDate+ ", while expected is " + expectedReturnOnDate);
    }


    public void verifyReturnOnIsChecked(){
        Assert.assertTrue(homePage.returnOnIsChecked(), "Return On checkbox is unchecked, while expected to be checked.");
    }

    public void verifyReturnOnIsUnchecked(){
        Assert.assertTrue(!homePage.returnOnIsChecked(), "Return On checkbox should be checked.");
    }

    public void testExpectedPassengersCountIsShown(String expPassengers){
        Assert.assertTrue(homePage.isCorrectPassengersCountShown(expPassengers),
                "Passengers count shown does not meet to the expected: " + expPassengers + ".");
    }


   // TC_1: to verify that at least from 1 to 7 one way flights found for a single adult person
    @Test (priority=0, dataProvider = "TC1_Provider")
    public void oneWay_OneUser_Flight_ShouldBeFound(String destFrom, String destTo, long departDaysLag, String expPassengersNumber){
        this.testFromDestinationFillsCorrectly(destFrom);
        this.testToDestinationFillsCorrectly(destTo);
        this.testDepartOnDateFillsCorrectly(departDaysLag);
        this.verifyReturnOnIsChecked();
        homePage.returnOnCheckBoxClick();
        this.verifyReturnOnIsUnchecked();
        this.testExpectedPassengersCountIsShown(expPassengersNumber);
        homePage.searchBtnSubmit();
        bookingPage = bookingPageTests.createBookingPageIfValid(driver);
        bookingPageTests.testExpectedDatesSpinnersCountShown(7);
        bookingPageTests.verifyFlightsAreFound();
        bookingPageTests.testCorrectNumberOfOneWayFlightsIsFound();
        testLogger.info("\"oneWay_OneUser_Flight_ShouldBeFound\" Test executed successfully.");
    }


    /*TC_8: to verify that error message "Unfortunately we do not fly from Dubai, United Arab Emirates to..." is shown for the unsupported flight destination*/
    @Test(priority = 1)
    public void doNotFly_Destination_Error_ShouldBeShown(){

        final String DEST_FROM = "Dubai, United Arab Emirates";
        final String DEST_TO = "Agadir, Morocco";
        final String ERROR_MSG = "Unfortunately we do not fly from Dubai, United Arab Emirates to Agadir, Morocco." +
                                 " However, we do fly from Dubai, United Arab Emirates to other destinations." +
                                 " Please change your destination and try again.";
        this.testFromDestinationFillsCorrectly(DEST_FROM);
        this.testToDestinationFillsCorrectly(DEST_TO);
        homePage.searchBtnSubmit();
        bookingPage = bookingPageTests.createBookingPageIfValid(driver);
        Assert.assertTrue(bookingPage.isUnsupportedFlightErrorShown(ERROR_MSG), ERROR_MSG.substring(0,40)+"... is not displayed or does not meet to the expected.");

    }
    //TC_2
    @Test
    public void totalPrice_Calculation_IsCorrect(){
        final String DEST_FROM = "Edinburgh, United Kingdom";
        final String DEST_TO = "Paris (Orly South), France";
        final long RETURN_DAYS_LAG = 7;
        final int ADULTS_TO_ADD = 3;


        testFromDestinationFillsCorrectly(DEST_FROM);
        testToDestinationFillsCorrectly(DEST_TO);
        verifyDefaultDepartOnDateIsCorrect();
        verifyReturnOnIsChecked();
        verifyReturnOnDateIsSet();
        testExpectedPassengersCountIsShown("1 Adult");
        homePage.passengersFieldClick();
        verifyPassengersPopUpIsDisaplyed();
        homePage.addAdultPassengers();
        //testAddAdultPassengers(ADULTS_TO_ADD);
        //TODO To finish
        //String s ="";

    }

    public void testAddAdultPassengers(int adults_count) {
        //TODO add try/catch for InvalidTestInputData
        homePage.addAdultPassengers();
        //Assert.assertEquals(((Integer)(adults_count-1)).toString(), homePage.getAdultPassengersCountBoxValue(), "Unexpected adult passengers set, expected: " +(adults_count-1)+ "." );
        Assert.assertEquals("2", homePage.getAdultPassengersCountBoxValue(), "Unexpected adult passengers set, expected: " +(adults_count-1)+ "." );
    }

    public void verifyPassengersPopUpIsDisaplyed() {
        Assert.assertTrue(homePage.isSelectPassengersPopupVisible(), "Passengers pop up is not displayed.");
    }

    public void verifyReturnOnDateIsSet() {
        Assert.assertTrue(homePage.returnOnDateIsSet(), "Return On date is empty.");
    }

    // Depart On should display tomorrow's date by default
    public void verifyDefaultDepartOnDateIsCorrect() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

        String fieldDate = homePage.getSelectedDepartOnDate();
        String currentDate = LocalDate.now().plusDays(1).format(formatter);
        Assert.assertEquals(fieldDate, currentDate, "Depart On date " + fieldDate+ " does not meet to the expected default today's " + currentDate + ".");
    }

    @DataProvider(name = "TC1_Provider")
    public Object[][] tc_1_Provider() {
        return new Object[][]{
                {"Dubrovnik, Croatia", "Amsterdam (Schiphol), Netherlands", 7, "1 Adult"},
               // {"Amsterdam (Schiphol), Netherlands", "Dubrovnik, Croatia",14, "2"}
        };
    }

}
