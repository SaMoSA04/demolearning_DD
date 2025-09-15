package com.ajay.learning.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class GoogleTest {
    
    @Test
    public void googleTitleTest() {

        WebDriver driver = new org.openqa.selenium.chrome.ChromeDriver();
        driver.get("https://www.google.com");
        String title = driver.getTitle();

        System.out.println("Title: " + title);

        assert title.equals("Google") : "Title does not match!";
        driver.quit();
        // System.out.println("Google Title Test");
    }
    
}
