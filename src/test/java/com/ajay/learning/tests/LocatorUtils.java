package com.ajay.learning.tests;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.openqa.selenium.By;

public class LocatorUtils {
    private static JSONObject locators;

    /**
     * Load locators JSON from classpath resource (recommended)
     * Example arg: "Locators.json" 
     */
    public static void loadLocatorsFromClasspath(String resourceName) {
        JSONParser parser = new JSONParser();
        try (InputStream input = LocatorUtils.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (input == null) {
                throw new RuntimeException("Locator file not found in classpath: " + resourceName);
            }
            InputStreamReader reader = new InputStreamReader(input);
            locators = (JSONObject) parser.parse(reader);
            System.out.println("Locator file loaded successfully from classpath!");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load locators from classpath resource: " + resourceName, e);
        }
    }

    /**
     * Load locators JSON from file system path
     * Example arg: "src/test/resources/Locators.json"
     */
    public static void loadLocatorsFromFile(String filePath) {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(filePath)) {
            locators = (JSONObject) parser.parse(reader);
            System.out.println("Locator file loaded successfully from file path!");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load locators from file path: " + filePath, e);
        }
    }

    /**
     * Safe get locator string by key
     * Throws meaningful error if locators not loaded or key missing
     */
    public static String getLocatorSafe(String key) {
        if (locators == null) {
            throw new IllegalStateException("Locators file not loaded!");
        }
        String locator = (String) locators.get(key);
        if (locator == null) {
            throw new IllegalStateException("Locator for key '" + key + "' not defined");
        }
        return locator;
    }

    /**
     * Convenience method: get Selenium By from xpath locator key
     */
    public static By getBy(String key) {
        return By.xpath(getLocatorSafe(key));
    }
}