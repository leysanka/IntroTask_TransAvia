import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class BookingPageTests extends BaseTest{

    public  BookingPage createBookingPageIfValid(WebDriver driver){
        try {
            bookingPage = new BookingPage(driver);
        } catch (WrongPageException e) {
            testLogger.error(e);
        }

        Assert.assertNotNull(bookingPage, "Booking page object is not created: title did not meet.");
        return bookingPage;
    }

    public void testExpectedDatesSpinnersCountShown(int expDays){

        int allDatesShownCount = bookingPage.getAllDatesShownCount();
        Assert.assertTrue((allDatesShownCount == expDays),
                allDatesShownCount + " count of shown dates does not equal to the expected " + expDays + " days." );

    }

    public void verifyFlightsAreFound(){
        Assert.assertTrue(bookingPage.isFlightsFounds(), "No flights found.");
    }

    public void testCorrectNumberOfOneWayFlightsIsFound(){
        int foundFlightsCount = bookingPage.getFoundFlightsCount();
        Assert.assertTrue((foundFlightsCount >=1 && foundFlightsCount<=7),
                foundFlightsCount +" flights found does not meet to the expected for a week (1-7)." );
    }


}
