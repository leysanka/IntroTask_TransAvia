import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HomePageTests extends BaseTest {

    @DataProvider(name = "TC1_Provider")
    public Object[][] tc_1_Provider() {
        return new Object[][]{
                {"Dubrovnik, Croatia", "Amsterdam (Schiphol), Netherlands", 7, "1 "}
                //{"Amsterdam (Schiphol), Netherlands", "Dubrovnik, Croatia"}
        };
    }

    /*TC_1: to verify that at least from 1 to 7 one way flights found for a single adult person*/

    @Test (dataProvider = "TC1_Provider")
    public void oneWay_OneUser_Flight_ShouldBeFound(String destFrom, String destTo, long departDayLag, String passengersNumber){

        HomePage homePage;
        BookingPage bookingPage = null;
        String expectedDepartOnDate, actualDepartOnDate;
        int allDatesShownCount, foundFlightsCount;

        homePage = new HomePage(driver);
        homePage.selectOtherCountriesLocale();
        Assert.assertTrue(homePage.whereToGoWindowIsDisplayed(), "Window \"Where do you want to go?\" is not displayed.");

        homePage.setFromDestinationByKeys(destFrom);
        homePage.setToDestinationByKeys(destTo);
        Assert.assertTrue(homePage.isMatchFromDestinationFieldText(destFrom), "FROM destination does not match.");
        Assert.assertTrue(homePage.isMatchToDestinationFieldText(destTo), "TO destination does not match.");
        homePage.setDepartOnDateField(departDayLag);
        expectedDepartOnDate = HomePage.calculateStartDate(departDayLag);
        actualDepartOnDate = homePage.getSelectedDepartOnDate();
        Assert.assertEquals(actualDepartOnDate,expectedDepartOnDate ,
                "DepartOn date does not meet: set "+actualDepartOnDate+ ", while expected is " + expectedDepartOnDate);
        homePage.returnOnCheckBoxClick();
        Assert.assertTrue(homePage.isCorrectPassengersCountShown(passengersNumber), "Passengers count shown does not meet, expected " + passengersNumber);
        homePage.searchBtnSubmit();

        try {
            bookingPage = new BookingPage(driver);
        } catch (WrongPageException e) {
            testLogger.error(e);
        }

        Assert.assertNotNull(bookingPage);
        Assert.assertTrue(bookingPage.isFlightsFounds(), "No flights found");
        allDatesShownCount = bookingPage.getAllDatesShownCount();
        Assert.assertTrue((allDatesShownCount == 7), allDatesShownCount + " count of shown dates does not equal to one way search, i.e. 7 days." );

        foundFlightsCount = bookingPage.getFoundFlightsCount();
        Assert.assertTrue((foundFlightsCount >=1 && foundFlightsCount<=7), foundFlightsCount +" flights found does not meet to the expected for a week (1-7)." );

        testLogger.info("\"oneWay_OneUser_Flight_ShouldBeFound\" Test executed successfully.");
    }

}
