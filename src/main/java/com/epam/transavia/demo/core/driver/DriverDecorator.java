package com.epam.transavia.demo.core.driver;

import com.epam.transavia.demo.core.exceptions.NotInstanceOfJavascriptExecutorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;

import java.util.List;
import java.util.Set;

public class DriverDecorator implements WebDriver, JavascriptExecutor, TakesScreenshot {

    private WebDriver driver;
    private Logger logger = LogManager.getLogger(DriverDecorator.class.getSimpleName());

    public DriverDecorator(WebDriver driver) {
        this.driver = driver;
        logger.info("Decorator initialised.");
    }

    @Override
    public void get(String url) {
        logger.info("Driver gets the url: " + url);
        driver.get(url);
    }

    @Override
    public String getCurrentUrl() {

        return driver.getCurrentUrl();
    }

    @Override
    public String getTitle() {

        logger.info("Driver gets title: " + driver.getTitle());
        return driver.getTitle();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    @Override
    public String getPageSource() {
        return driver.getPageSource();
    }

    @Override
    public void close() {
        logger.info("Driver closed.");
        driver.close();
    }

    @Override
    public void quit() {
        logger.info("Driver quit is called.");
        driver.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return driver.switchTo();
    }

    @Override
    public Navigation navigate() {

        logger.info("Driver navigate is being performed.");
        return driver.navigate();
    }

    @Override
    public Options manage() {
        return driver.manage();
    }

    @Override
    public Object executeScript(String script, Object... args) {
        if (driver instanceof JavascriptExecutor) {
            logger.info("Executing javascript " + script + ".");
            return ((JavascriptExecutor) driver).executeScript(script, args);
        } else {
            throw new NotInstanceOfJavascriptExecutorException("Decorated driver cannot instantiate of the JavascriptExecutor class.");
        }
    }

    @Override
    public Object executeAsyncScript(String script, Object... args) {
        if (driver instanceof JavascriptExecutor) {
            return ((JavascriptExecutor) driver).executeAsyncScript(script, args);
        } else {
            throw new NotInstanceOfJavascriptExecutorException("Decorated driver cannot instantiate of the JavascriptExecutor class.");
        }
    }
    //TODO Add highlighting of failed element there??
    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return ((TakesScreenshot) driver).getScreenshotAs(target);
    }
}
