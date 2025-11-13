package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

public class AllureManager {

    /**
     * Attach screenshot to Allure report
     */
    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] saveScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    /**
     * Attach screenshot with custom name
     */
    public static void attachScreenshot(WebDriver driver, String screenshotName) {
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment(screenshotName, new ByteArrayInputStream(screenshot));
    }

    /**
     * Attach text to Allure report
     */
    @Attachment(value = "{0}", type = "text/plain")
    public static String attachText(String message) {
        return message;
    }

    /**
     * Attach JSON to Allure report
     */
    @Attachment(value = "{0}", type = "application/json")
    public static String attachJson(String json) {
        return json;
    }

    /**
     * Add step to Allure report
     */
    public static void addStep(String stepDescription) {
        Allure.step(stepDescription);
    }

    /**
     * Add parameter to Allure report
     */
    public static void addParameter(String name, String value) {
        Allure.parameter(name, value);
    }

    /**
     * Add environment info
     */
    public static void addEnvironmentInfo(String name, String value) {
        Allure.addAttachment(name, value);
    }
}