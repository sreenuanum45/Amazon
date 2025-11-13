package utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import constants.AppConstants;

import java.io.FileReader;
import java.io.IOException;

public class TestDataReader {

    private static JsonObject testData;

    static {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(AppConstants.TEST_DATA_FILE_PATH);
            testData = gson.fromJson(reader, JsonObject.class);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load test data JSON file");
        }
    }

    public static String getSearchKeyword() {
        return testData.get("searchKeyword").getAsString();
    }

    public static int getExpectedQuantity() {
        return testData.get("expectedQuantity").getAsInt();
    }

    public static String getTestData(String key) {
        return testData.get(key).getAsString();
    }
}