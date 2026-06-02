package Hooks;

import DriverFactory.DriverFactory;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        String browser = resolveBrowser();
        String baseUrl = resolveBaseUrl();

        DriverFactory.SetUpDriver(browser);
        WebDriver driver = DriverFactory.getDriver();

        driver.get(baseUrl);
        clearAllCaches(driver);

        LogsUtils.info("Scenario '" + scenario.getName() + "' setup - '"
                + browser + "' (private mode) at " + baseUrl);
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
                        .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", scenario.getName());
                LogsUtils.error("Scenario failed: " + scenario.getName());
            }
        } catch (Exception e) {
            LogsUtils.warn("Could not capture failure screenshot", e);
        } finally {
            // Quitting the private session disposes its entire profile (cache, cookies, storage).
            DriverFactory.quitDriver();
            LogsUtils.info("Scenario '" + scenario.getName() + "' teardown - browser closed.");
        }
    }

    private void clearAllCaches(WebDriver driver) {
        try {
            driver.manage().deleteAllCookies();
            ((JavascriptExecutor) driver).executeScript(
                    "window.localStorage.clear(); window.sessionStorage.clear();");
        } catch (Exception e) {
            LogsUtils.warn("Could not clear browser caches", e);
        }
    }

    private String resolveBrowser() {
        String fromSystem = System.getProperty("browser");
        if (fromSystem != null && !fromSystem.isBlank()) {
            return fromSystem;
        }
        return DataUtils.getPropertyData("environments", "browser");
    }

    private String resolveBaseUrl() {
        String fromSystem = System.getProperty("baseUrl");
        if (fromSystem != null && !fromSystem.isBlank()) {
            return fromSystem;
        }
        return DataUtils.getPropertyData("environments", "baseUrl");
    }
}
