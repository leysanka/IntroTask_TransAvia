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

    public void testSelectFirstInboundOutboundFlights() {
        bookingPage.selectOutboundFlight(1);
        bookingPage.pressSelectOutboundFlight();
        bookingPage.selectInboundFlight(1);
        bookingPage.pressSelectInboundFlight();

        Assert.assertEquals(2, bookingPage.getSelectedFlightsCount(),"Selected flights count must equal 2");
        testLogger.info("Out and Inbound flights selection test passed.");

    }

    //public void getTotalPrice

    public void verifyTotalPriceIsCorrect(int adults, int children, int babies){
        double priceClass, pricePerAdult, pricePerChild, pricePerBaby, totalPrice;

        pricePerAdult = bookingPage.getTotalPricePerAdultPassenger();
        pricePerChild = pricePerAdult;
        pricePerBaby = bookingPage.getTotalPricePerBaby();

        totalPrice = adults*pricePerAdult + children*pricePerChild + babies*pricePerBaby;
        Assert.assertEquals(totalPrice, bookingPage.getTotalAmountPrice(), "The 'Total amount' price in the page does not meet to the calculated: " + totalPrice + ";");

    }




}
