import org.testng.annotations.*;


public class ValidSearchTests extends BaseTest {

//TODO move tests there from HomePageTest!

//    HomePageTests homePageTests = new HomePageTests();
//    BookingPageTests bookingPageTests = new BookingPageTests();

@Test (priority=0, dataProvider = "TC1_Provider")
public void oneWay_OneUser_Flight_ShouldBeFound(String destFrom, String destTo, long departDaysLag, String expPassengersNumber){
    HomePageTests homePageTests = new HomePageTests(driver,homePage);
    BookingPageTests bookingPageTests = new BookingPageTests();
    homePageTests.testFromDestinationFillsCorrectly(destFrom);
    homePageTests.testToDestinationFillsCorrectly(destTo);
    homePageTests.testDepartOnDateFillsCorrectly(departDaysLag);
    homePageTests.verifyReturnOnIsChecked();
    homePage.returnOnCheckBoxClick();
    homePageTests.verifyReturnOnIsUnchecked();
    homePageTests.verifyExpectedPassengersCountIsShown(expPassengersNumber);
    homePage.searchBtnSubmit();
    bookingPage = bookingPageTests.createBookingPageIfValid(driver);
    bookingPageTests.testExpectedDatesSpinnersCountShown(7);
    bookingPageTests.verifyFlightsAreFound();
    bookingPageTests.testCorrectNumberOfOneWayFlightsIsFound();
    testLogger.info("\"oneWay_OneUser_Flight_ShouldBeFound\" Test executed successfully.");
}

    @DataProvider(name = "TC1_Provider")
    public Object[][] tc_1_Provider() {
        return new Object[][]{
                {"Dubrovnik, Croatia", "Amsterdam (Schiphol), Netherlands", 7, "1 Adult"},
                // {"Amsterdam (Schiphol), Netherlands", "Dubrovnik, Croatia",14, "2"}
        };
    }
}
