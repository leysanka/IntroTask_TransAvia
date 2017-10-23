import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BookingDetailsPage {

    private WebDriver driver;
    private final String PAGE_TITLE = "Booking details";

    @FindBy() private WebElement totalAmountContainer;
    @FindBy() private WebElement totalPaymentContainer;

    public BookingDetailsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public double getTotalAmountValue(){
        return 0;
    }

    public double getTotalPaymentValue(){
        return 0;
    }
}
