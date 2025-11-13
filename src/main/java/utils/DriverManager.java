package utils;

import constants.AppConstants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class DriverManager {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    private static void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
    }

    public static void initializeDriver() {
        String browser = ConfigReader.getBrowser().toLowerCase();
        WebDriver webDriver;

        switch (browser) {
            case AppConstants.CHROME:
                WebDriverManager.chromedriver().setup();  // ADD THIS!

                ChromeOptions chromeOptions = new ChromeOptions();

                // 🔥 BEST settings for Amazon + heavy apps
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--disable-features=IsolateOrigins,site-per-process");

                chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

                if (ConfigReader.isHeadless()) {
                    chromeOptions.addArguments("--headless=new");
                }

                webDriver = new ChromeDriver(chromeOptions);
                break;

            case AppConstants.FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if (ConfigReader.isHeadless()) {
                    firefoxOptions.addArguments("--headless");
                }

                webDriver = new FirefoxDriver(firefoxOptions);
                webDriver.manage().window().maximize();
                break;

            case AppConstants.EDGE:
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();

                if (ConfigReader.isHeadless()) {
                    edgeOptions.addArguments("--headless");
                }

                webDriver = new EdgeDriver(edgeOptions);
                webDriver.manage().window().maximize();
                break;

            default:
                throw new IllegalArgumentException("Browser " + browser + " is not supported");
        }

        // Reduced timeout — avoids renderer crash
        webDriver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(50)
        );

        webDriver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(ConfigReader.getImplicitWait())
        );

        setDriver(webDriver);
    }

    public static void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}
