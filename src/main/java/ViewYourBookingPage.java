import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class ViewYourBookingPage {

    private WebDriver driver;
    private final String PAGE_TITLE = "View your booking";

    @FindBy(xpath = "//em//time") private List<WebElement> deptAndArrivalTimes;
    @FindBy() private WebElement departureTime;
    @FindBy() private WebElement arrivalTime;
    @FindBy() private WebElement duration;
    @FindBy(xpath = "//h3//span[@class='nowrap']") private List<WebElement> flyingRoute;




    public ViewYourBookingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }






}
