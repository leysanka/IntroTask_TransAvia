import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import java.text.Format;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


public class ViewYourBookingPage {

    private WebDriver driver;
    private final String PAGE_TITLE = "View your booking";

    @FindBy(xpath = "//em//time") private List<WebElement> deptAndArrivalTimes;
    @FindBy() private WebElement departureTime;
    @FindBy() private WebElement arrivalTime;
    @FindBy(xpath = "//h5[contains(text(), 'Duration')]//following-sibling::p") private WebElement duration;
    @FindBy(xpath = "//h3//span[@class='nowrap']") private List<WebElement> flyingRoute;

    public ViewYourBookingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getDepartureTime(){
        return deptAndArrivalTimes.get(0).getAttribute("datetime");
    }

    public String getArrivalTime(){
        return deptAndArrivalTimes.get(1).getAttribute("datetime");
    }

    public boolean isArrivalTimeLaterThanDeparture(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime arrivalTime = LocalDateTime.parse(this.getArrivalTime(), formatter);
        LocalDateTime departureTime = LocalDateTime.parse(this.getDepartureTime(), formatter);

        return arrivalTime.isAfter(departureTime);
    }

    public String getFlyingFrom(){
        return flyingRoute.get(0).getText();
    }

    public String getFlyingTo(){
        return flyingRoute.get(1).getText();
    }




}
