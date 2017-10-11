import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTests extends BaseTest {



    @Test /*TC_1: to verify that at least from 1 to 7 one way flights found for a single adult person*/
    public void oneWay_OneUser_Flight_IsFound(){




        HomePage homePage = new HomePage(driver);

        if (homePage.whereToGoWindowIsDisplayed())
        {

              homePage.uncheckReturnOnCheckBox();
               // homePage.fromFieldActivate();
              homePage.setFromDestinationByText("Dubrovnik, Croatia");

              homePage.setToDestinationByText("Amsterdam (Schiphol), Netherlands");

                Assert.assertTrue(homePage.isMatchFromDestinationFieldText("Dubrovnik, Croatia"));
                Assert.assertTrue(homePage.isMatchToDestinationFieldText("Amsterdam (Schiphol), Netherlands"));
               //homePage.selectDestinationFromDropDown(5);
            testLogger.info("\"oneWay_OneUser_Flight_IsFound\" Test executed successfully.");


        }









    }

}
