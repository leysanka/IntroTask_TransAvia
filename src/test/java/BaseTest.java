import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


import java.util.concurrent.TimeUnit;

public class BaseTest {

    protected WebDriver driver;
    protected static String homePageUrl = "https://www.transavia.com/";

    protected static Logger testLogger = LogManager.getLogger("test");

    @BeforeMethod
    protected void setUpBeforeMethod(){

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(homePageUrl);

    }

    @AfterMethod
    protected void tearDownAfterMethod() {
        driver.quit();
    }
}
