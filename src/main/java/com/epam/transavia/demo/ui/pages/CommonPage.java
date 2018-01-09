package com.epam.transavia.demo.ui.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public abstract class CommonPage {

    private static final int SECONDS_TO_WAIT = 10;
    protected Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    protected WebDriver driver;


    public CommonPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isElementPresent(By by) {
        return !driver.findElements(by).isEmpty();
    }

    public By getBy(String fieldName) {
        try {
            return new Annotations(this.getClass().getDeclaredField(fieldName)).buildBy();
        } catch (NoSuchFieldException e) {
            logger.error("Cannot get By locator for the specified element name.");
            return null;
        }
    }

    public void waitForLoadingIsFinished() {
        logger.info("Waiting for loading is finished.");
        WebDriverWait wait = new WebDriverWait(driver, SECONDS_TO_WAIT);
        wait.until(ExpectedConditions.jsReturnsValue("return jQuery.active == 0;"));
    }

    public void waitForElementIsClickable(WebElement element) {
        logger.info("Waiting for element is clickable.");
        WebDriverWait wait = new WebDriverWait(driver, SECONDS_TO_WAIT);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementIsVisible(WebElement element) {
        logger.info("Waiting for element is visible." );
        WebDriverWait wait = new WebDriverWait(driver, SECONDS_TO_WAIT);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementsAreVisible(List<WebElement> elements) {
        logger.info("Waiting for elements are visible." );
        WebDriverWait wait = new WebDriverWait(driver, SECONDS_TO_WAIT);
        wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }


    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);

    }

    public String getInnerHTMLTrimmedValue(WebElement element) {
        logger.info("Getting InnerHTM value for element. " );

        if (element.getAttribute("innerHTML") != null) {
            return element.getAttribute("innerHTML").trim();
        }
        logger.error("InnerHTML attribute did not return value.");
        return null;
    }

}
