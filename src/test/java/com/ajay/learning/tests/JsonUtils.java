package com.ajay.learning.tests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.parser.ParseException;

public class JsonUtils {
    public static Object[][] getTestData(String filePath) {
        JSONParser parser = new JSONParser();
        try {
            JSONArray dataArray = (JSONArray) parser.parse(new FileReader(filePath));
            Object[][] data = new Object[dataArray.size()][1];
            for (int i = 0; i < dataArray.size(); i++) {
                JSONObject obj = (JSONObject) dataArray.get(i);
                data[i][0] = obj;
            }
            return data;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return new Object[0][0];
        }
    }
}
