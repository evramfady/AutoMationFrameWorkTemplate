package DriverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static void SetUpDriver(String browser)
    {
        switch (browser.toLowerCase())
        {
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximize");
                driverThreadLocal.set(new EdgeDriver(edgeOptions));
                break;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximize");
                driverThreadLocal.set(new FirefoxDriver(firefoxOptions));
                break;
            default:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximize");
                driverThreadLocal.set(new ChromeDriver(chromeOptions));
                System.out.println("Wrong browser and started with Chrome by default ");
                break;
        }
    }

    public static WebDriver getDriver()
    {
       return driverThreadLocal.get();
    }

    public static void quitDriver()
    {
        getDriver().quit();
        driverThreadLocal.remove();
    }
}
