package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static String reportPath;

    public static ExtentReports createInstance() throws IOException {
        String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
        String reportDir = System.getProperty("user.dir") + "/test-output/ExtentReports/";

        // Create directory if not exists
        File directory = new File(reportDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        reportPath = reportDir + "AmazonTest_" + timestamp + ".html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

        // Load config from XML
        String configPath = System.getProperty("user.dir") + "/src/test/resources/extent-config.xml";
        File configFile = new File(configPath);
        if (configFile.exists()) {
            sparkReporter.loadXMLConfig(configFile);
        }

        // Basic Configuration
        sparkReporter.config().setDocumentTitle("Amazon Automation Test Report");
        sparkReporter.config().setReportName("Add to Cart - Test Execution Report");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setEncoding("UTF-8");
        sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // System Information
        extent.setSystemInfo("Application", "Amazon");
        extent.setSystemInfo("Environment", ConfigReader.getProperty("environment"));
        extent.setSystemInfo("Browser", ConfigReader.getBrowser());
        extent.setSystemInfo("OS", System.getProperty("os.name"));


        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("Test Framework", "Cucumber + TestNG + Selenium");

        return extent;
    }

    public static ExtentReports getExtentReport() throws IOException {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    public static void setExtentTest(ExtentTest test) {
        extentTest.set(test);
    }

    public static ExtentTest getExtentTest() {
        return extentTest.get();
    }

    public static void removeExtentTest() {
        extentTest.remove();
    }

    public static String getReportPath() {
        return reportPath;
    }

    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
}
