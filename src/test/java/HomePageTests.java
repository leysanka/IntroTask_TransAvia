import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HomePageTests extends BaseTest {

    private BookingPage bookingPage = null;

    /*TC_1: to verify that at least from 1 to 7 one way flights found for a single adult person*/

    @Test (priority=0, dataProvider = "TC1_Provider")
    public void oneWay_OneUser_Flight_ShouldBeFound(String destFrom, String destTo, long departDayLag, String expPassengersNumber){

        String expectedDepartOnDate, actualDepartOnDate;
        int allDatesShownCount, foundFlightsCount;

        homePage.setFromDestinationByKeys(destFrom);
        Assert.assertTrue(homePage.isMatchFromDestinationFieldText(destFrom), "FROM destination does not match.");
        homePage.setToDestinationByKeys(destTo);
        Assert.assertTrue(homePage.isMatchToDestinationFieldText(destTo), "TO destination does not match.");

        homePage.setDepartOnDateField(departDayLag);
        expectedDepartOnDate = HomePage.calculateStartDate(departDayLag);
        actualDepartOnDate = homePage.getSelectedDepartOnDate();
        Assert.assertEquals(actualDepartOnDate,expectedDepartOnDate ,
                "DepartOn date does not meet: set "+actualDepartOnDate+ ", while expected is " + expectedDepartOnDate);

        Assert.assertTrue(homePage.returnOnCheckBoxState(), "Return On checkbox is unchecked, while expected to be checked by default.");
        homePage.returnOnCheckBoxClick();
        Assert.assertTrue(!homePage.returnOnCheckBoxState(), "Return On checkbox should be checked.");
        Assert.assertTrue(homePage.isCorrectPassengersCountShown(expPassengersNumber), "Passengers count shown does not meet to the expected: " + expPassengersNumber + ".");
        homePage.searchBtnSubmit();

        bookingPage = this.createBookingPage();
        Assert.assertTrue(bookingPage.isFlightsFounds(), "No flights found.");
        allDatesShownCount = bookingPage.getAllDatesShownCount();
        Assert.assertTrue((allDatesShownCount == 7), allDatesShownCount + " count of shown dates does not equal to one way search, i.e. 7 days." );

        foundFlightsCount = bookingPage.getFoundFlightsCount();
        Assert.assertTrue((foundFlightsCount >=1 && foundFlightsCount<=7), foundFlightsCount +" flights found does not meet to the expected for a week (1-7)." );

        testLogger.info("\"oneWay_OneUser_Flight_ShouldBeFound\" Test executed successfully.");
    }


    /*TC_8: to verify that error message "Unfortunately we do not fly from Dubai, United Arab Emirates to..." is shown for the unsupported flight destination*/
    @Test(priority = 1)
    public void doNotFly_Destination_Error_ShouldBeShown(){

        final String destFrom = "Dubai, United Arab Emirates";
        final String destTo = "Agadir, Morocco";
        final String errorMsg = "Unfortunately we do not fly from Dubai, United Arab Emirates to Agadir, Morocco." +
                                 " However, we do fly from Dubai, United Arab Emirates to other destinations." +
                                 " Please change your destination and try again.";

        homePage.setFromDestinationByKeys(destFrom);
        Assert.assertTrue(homePage.isMatchFromDestinationFieldText(destFrom), "FROM destination does not match.");
        homePage.setToDestinationByKeys(destTo);
        Assert.assertTrue(homePage.isMatchToDestinationFieldText(destTo), "TO destination does not match.");
        homePage.searchBtnSubmit();

        bookingPage = this.createBookingPage();
        Assert.assertTrue(bookingPage.isUnsupportedFlightErrorShown(errorMsg), errorMsg.substring(0,40)+"... is not displayed or does not meet to the expected.");

    }

     public BookingPage createBookingPage(){

        try {
           bookingPage = new BookingPage(driver);
        } catch (WrongPageException e) {
            testLogger.error(e);
        }
        Assert.assertNotNull(bookingPage, "Booking page object is not created: title did not meet.");
        return bookingPage;
     }


    @DataProvider(name = "TC1_Provider")
    public Object[][] tc_1_Provider() {
        return new Object[][]{
                {"Dubrovnik, Croatia", "Amsterdam (Schiphol), Netherlands", 7, "1 "},
               // {"Amsterdam (Schiphol), Netherlands", "Dubrovnik, Croatia",14, "2"}
        };
    }

}
