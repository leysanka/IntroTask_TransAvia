package com.epam.transavia.demo.gui.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public abstract class CommonPage {

    private static final int SECONDS_TO_WAIT = 10;
    protected static Logger logger = LogManager.getLogger();
    protected WebDriver driver;


    public CommonPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void waitForLoadingIsFinished() {
        WebDriverWait wait = new WebDriverWait(driver, SECONDS_TO_WAIT);
        wait.until(ExpectedConditions.jsReturnsValue("return jQuery.active == 0;"));
    }

    public void waitForElementIsClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, SECONDS_TO_WAIT);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementIsVisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, SECONDS_TO_WAIT);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

}
