import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ViewBookingWithoutAccountTests extends BaseTest {

    @Test (dataProvider = "viewBooking_WithoutAccount_Provider")
    public void viewBooking_WithoutAccount_ArrivalTime_IsCorrect(String bookingNumber, String lastName, String flightDate, String flyingFrom, String flyingTo){
        HomePageTests homePageTests = new HomePageTests(driver,homePage);
        homePageTests.testOpenManageBookingToolbar();
        homePageTests.testGoToViewBooking();
        LoginPage loginPage = new LoginPage(driver);
        LoginPageTests loginPageTests = new LoginPageTests(driver, loginPage);
        loginPageTests.verifyPageTitleIsCorrect();
        loginPageTests.testBookingNumberFillsCorrectly(bookingNumber);
        loginPageTests.testLastNameFillsCorrectly(lastName);
        loginPageTests.testFLightDateFillsCorrectly(flightDate);
        loginPage.submitFlightDateAndGoToViewBooking(); //cannot click View button as it's overlapped with opened calendar, thus using Enter in flightDate field
        ViewYourBookingPage viewYourBookingPage = new ViewYourBookingPage(driver);
        Assert.assertEquals(viewYourBookingPage.getFlyingFrom(), flyingFrom, "Flying From destination does not meet to the expected: " +flyingFrom);
        Assert.assertEquals(viewYourBookingPage.getFlyingTo(), flyingTo, "Flying To destination does not meet to the expected: " +flyingTo);
        Assert.assertNotNull(viewYourBookingPage.getDepartureTime(), "");
        Assert.assertNotNull(viewYourBookingPage.getArrivalTime(), "");



        String s = "";


    }

    @DataProvider(name = "viewBooking_WithoutAccount_Provider")
    public Object[][] viewBooking_WithoutAccount_Provider() {
            return new Object[][]{
                    {"MF8C9R", "kukharau", "09 June 2016", "Pisa", "Amsterdam(Shiphol)"},
            };
    }

    
}
