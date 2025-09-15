package com.ajay.learning.tests;

import java.io.File;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.interactions.Actions;


public class DemoQATest {

    public WebDriver driver;

    @BeforeClass
    public void setUp() {
        LocatorUtils.loadLocatorsFromClasspath("Locators.json");
        System.setProperty("webdriver.chrome.driver", ConfigLoader.getProperty("chromedriver.path"));
    }

    @BeforeMethod
    public void launchBrowser() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        String baseUrl = ConfigLoader.getProperty("base.url");
        driver.get(baseUrl);
    }

    @DataProvider(name = "formData")
    public Object[][] formData() {
        return JsonUtils.getTestData("src\\test\\resources\\DemoQATestData.json");
    }

    @Test(dataProvider = "formData")
    public void fillPracticeForm(JSONObject data) {
        driver.findElement(LocatorUtils.getBy("firstName")).sendKeys((String) data.get("firstName"));
        driver.findElement(LocatorUtils.getBy("lastName")).sendKeys((String) data.get("lastName"));
        driver.findElement(LocatorUtils.getBy("email")).sendKeys((String) data.get("email"));

        String genderValue = data.get("gender").toString(); // e.g., "Male" or "Female"
        String genderXpathTemplate = LocatorUtils.getLocatorSafe("genderRadio");
        String genderXpath = String.format(genderXpathTemplate, genderValue);
        try {
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Gender XPath: " + genderXpath); // Debug print

        driver.findElement(By.xpath(genderXpath)).click();

        driver.findElement(LocatorUtils.getBy("mobile")).sendKeys((String) data.get("mobile"));

        WebElement dateOfBirthField = driver.findElement(LocatorUtils.getBy("dateOfBirth"));
        Actions actions = new Actions(driver);
        dateOfBirthField.click();

        actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
        dateOfBirthField.sendKeys((String) data.get("dateOfBirth"));
        dateOfBirthField.sendKeys(Keys.ENTER);

        try {
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        

        // Handle hobbies dynamically using a single XPath template
        JSONArray hobbies = (JSONArray) data.get("hobby");
        System.out.println("Hobbies: " + hobbies); // Debug print
        if (hobbies != null) {
            String hobbyXpathTemplate = LocatorUtils.getLocatorSafe("hobbyCheck");
            for (Object hobby : hobbies) {
                String hobbyName = hobby.toString();
                String hobbyXpath = String.format(hobbyXpathTemplate, hobbyName);
                WebElement hobbyElement = driver.findElement(By.xpath(hobbyXpath));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", hobbyElement);
                hobbyElement.click();
                System.out.println("Clicked hobby: " + hobbyName); // Debug print
                try {
                    Thread.sleep(2000); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        // Upload file
        System.out.println("Uploading file...");
        WebElement uploadElement = driver.findElement(LocatorUtils.getBy("fileUpload"));
        File file = new File("src/test/resources/WhatsApp Image.jpg");
        String absolutePath = file.getAbsolutePath();
        uploadElement.sendKeys(absolutePath);
        System.out.println("File uploaded: " + absolutePath);
        try {
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        //Select State
        driver.findElement(LocatorUtils.getBy("stateDropdown")).click();
        String stateValue = (String) data.get("state");
        String stateXpathTemplate = LocatorUtils.getLocatorSafe("stateValue");
        String stateXpath = String.format(stateXpathTemplate, stateValue);
        driver.findElement(By.xpath(stateXpath)).click();

        

        // Submit the form
        WebElement submitButton = driver.findElement(LocatorUtils.getBy("submitButton"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        submitButton.click();

        try {
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Add assertions here if necessary
        String actualMessage = driver.findElement(LocatorUtils.getBy("successMessage")).getText();
        String expectedMessage = (String) data.get("successMessage");
        Assert.assertEquals(actualMessage, expectedMessage, "Success message mismatch!");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
    
}
