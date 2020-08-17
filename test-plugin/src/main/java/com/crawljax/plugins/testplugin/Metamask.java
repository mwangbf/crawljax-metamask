package com.crawljax.plugins.testplugin;

import com.crawljax.browser.EmbeddedBrowser;
import org.openqa.selenium.By;
		
import org.openqa.selenium.WebDriver;		
import org.openqa.selenium.chrome.ChromeDriver;		
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.JavascriptExecutor;

import java.io.File;

public class Metamask {
	//public static void main(String[] args) {
	public void test(EmbeddedBrowser browser){
        String baseUrl = "chrome-extension://nkbihfbeogaeaoehlefnkodbefgpgknn/popup.html";
        WebDriver driver = browser.getWebDriver();
        driver.get(baseUrl);
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor)driver).executeScript("ethereum.enable()");
        } else {
            throw new IllegalStateException("This driver does not support JavaScript!");
        }
        while (driver.findElement(By.id("password"))!=null){
            WebElement password = driver.findElement(By.id("password"));
            password.sendKeys("");
            driver.findElement(By.className("jss40")).click();
        }
        WebElement confirm = driver.findElement(By.className("button btn-secondary btn--large page-container__footer-button"));
        confirm.click();
        driver.close();
        System.exit(0);
    }
}

//popup:https://www.guru99.com/alert-popup-handling-selenium.html#3
