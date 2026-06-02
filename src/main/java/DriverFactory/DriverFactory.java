package DriverFactory;

import Utilities.LogsUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static void SetUpDriver(String browser) {
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        String target = (browser == null || browser.isBlank())
                ? "chrome"
                : browser.toLowerCase().trim();

        WebDriver driver;
        switch (target) {
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximize");
                edgeOptions.addArguments("--inprivate");
                if (headless) edgeOptions.addArguments("--headless=new");
                driver = new EdgeDriver(edgeOptions);
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximize");
                firefoxOptions.addArguments("-private");
                if (headless) firefoxOptions.addArguments("-headless");
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "chrome":
                driver = new ChromeDriver(chromeOptions(headless));
                break;
            default:
                LogsUtils.warn("Unknown browser '" + browser + "', defaulting to Chrome.");
                driver = new ChromeDriver(chromeOptions(headless));
                break;
        }
        driverThreadLocal.set(driver);
        LogsUtils.info("Started '" + target + "' driver (headless=" + headless + ").");
    }

    private static ChromeOptions chromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximize");
        options.addArguments("--incognito");
        if (headless) options.addArguments("--headless=new", "--window-size=1920,1080");
        return options;
    }

    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new IllegalStateException(
                    "WebDriver not initialized for this thread. Call SetUpDriver(...) first.");
        }
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
            LogsUtils.info("Driver quit and removed from thread.");
        }
    }
}
