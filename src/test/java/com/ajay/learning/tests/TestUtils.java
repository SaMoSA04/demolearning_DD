package com.ajay.learning.tests;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtils {
    public static String captureScreenshot(WebDriver driver, String testName) throws IOException {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String dir = System.getProperty("user.dir") + "/screenshots/";
        Files.createDirectories(Paths.get(dir));
        String destPath = dir + testName + ".png";
        Files.copy(srcFile.toPath(), Paths.get(destPath));
        return destPath;
    }
}