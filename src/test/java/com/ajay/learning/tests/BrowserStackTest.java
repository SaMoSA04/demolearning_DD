package com.ajay.learning.tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class BrowserStackTest {

    @Test
    public void browserStackTest() {
        System.out.println("BrowserStack Test");
        WebDriver driver = new org.openqa.selenium.chrome.ChromeDriver();
        driver.get("https://www.browserstack.com");

        List<WebElement> links = driver.findElements(By.xpath("//button[@class='product-section__tabs-btn']/h4"));
        List<String> linkValue = links.stream().map(e -> e.getText()).toList();

        System.out.println("Links: " + linkValue);

        WebElement elementOne = driver.findElement(By.xpath("//button[@class='product-section__tabs-btn' and .//h4[text()='" + linkValue.get(1) + "']]"));

        System.out.println("Element One: //button[@class='product-section__tabs-btn']/h4[text()='"+linkValue.get(1)+"']");

        if(elementOne.isDisplayed()) {
            System.out.println(linkValue.get(1) + " is displayed");
            elementOne.click();
        } else {
            System.out.println("-1");
        }

        List<WebElement> elementList = driver.findElements(By.xpath("//button[@class='product-section__tabs-btn']/h4[text()='Something Else']"));
        if (!elementList.isEmpty()) {
            WebElement elementTwo = elementList.get(0);
            // Element is present, interact with elementTwo as needed
        } else {
            // Element not found - handle accordingly
            System.out.println("Element not present");
        }

        


        driver.quit();
    }
    
}
