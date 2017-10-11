import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BookingPage {

    WebDriver driver;

    /*@FindBy()
    WebElement */


    public BookingPage(WebDriver driver) {
        this.driver = driver;
        if (!"Book a flight".equals(driver.getTitle())) {
            throw new IllegalStateException("Not booking page is opened.");
        }
        PageFactory.initElements(driver, this);
    }
}
