package stepDefinitions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.ConfigReader;
import utils.DriverManager;
import utils.ExtentReportManager;

public class Hooks {

    private static ExtentReports extent;
    private ExtentTest test;

    @BeforeAll
    public static void beforeAllTests() {
        extent = ExtentReportManager.createInstance();
    }

    @Before
    public void setUp(Scenario scenario) {
        System.out.println("========================================");
        System.out.println("Starting Scenario: " + scenario.getName());
        System.out.println("========================================");

        // Create Extent Test
        test = extent.createTest(scenario.getName());
        ExtentReportManager.setExtentTest(test);

        // Initialize Driver
        DriverManager.initializeDriver();

        DriverManager.getDriver().get(ConfigReader.getUrl());

        test.log(Status.INFO, "Browser launched: " + ConfigReader.getBrowser());
        test.log(Status.INFO, "Navigated to: " + ConfigReader.getUrl());
    }

    @After
    public void tearDown(Scenario scenario) {
        ExtentTest test = ExtentReportManager.getExtentTest();

        // Take screenshot if scenario failed
        if (scenario.isFailed()) {
            try {
                final byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                        .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", scenario.getName());

                // Add to Extent Report
                String base64Screenshot = ((TakesScreenshot) DriverManager.getDriver())
                        .getScreenshotAs(OutputType.BASE64);
                test.fail("Scenario Failed")
                        .addScreenCaptureFromBase64String(base64Screenshot, scenario.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Log status to Extent Report
        if (scenario.isFailed()) {
            test.log(Status.FAIL, "Scenario Failed: " + scenario.getName());
        } else {
            test.log(Status.PASS, "Scenario Passed: " + scenario.getName());
        }

        System.out.println("========================================");
        System.out.println("Scenario Status: " + scenario.getStatus());
        System.out.println("========================================");

        // Clean up
        ExtentReportManager.removeExtentTest();
        DriverManager.quitDriver();
    }

    @AfterAll
    public static void afterAllTests() {
        if (extent != null) {
            extent.flush();
            System.out.println("\n===========================================");
            System.out.println("Extent Report Generated: " + ExtentReportManager.getReportPath());
            System.out.println("===========================================\n");
        }
    }
}