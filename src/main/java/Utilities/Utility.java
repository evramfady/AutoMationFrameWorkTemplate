package Utilities;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class Utility {

    private static final String SCREENSHOTSPATH = "testOutputs/ScreenShots/";
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

    private Utility() {
        // Static utility class - prevent instantiation.
    }

    public static WebDriverWait GeneralWait(WebDriver driver) {
        return new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    public static void ClickOnElement(WebDriver driver, By locator) {
        try {
            GeneralWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
            FindWebElement(driver, locator).click();
        } catch (Exception e) {
            LogsUtils.error("Failed to click element: " + locator, e);
            throw new RuntimeException("Failed to click element: " + locator, e);
        }
    }

    public static void SendData(WebDriver driver, By locator, String data) {
        try {
            GeneralWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement element = FindWebElement(driver, locator);
            element.clear();
            element.sendKeys(data);
        } catch (Exception e) {
            LogsUtils.error("Failed to type into element: " + locator, e);
            throw new RuntimeException("Failed to type into element: " + locator, e);
        }
    }

    public static String GetText(WebDriver driver, By locator) {
        try {
            GeneralWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
            return FindWebElement(driver, locator).getText();
        } catch (Exception e) {
            LogsUtils.error("Failed to get text from element: " + locator, e);
            throw new RuntimeException("Failed to get text from element: " + locator, e);
        }
    }

    public static void ScrollToElementJS(WebDriver driver, By locator) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView(true);", FindWebElement(driver, locator));
        } catch (Exception e) {
            LogsUtils.error("Failed to scroll to element: " + locator, e);
            throw new RuntimeException("Failed to scroll to element: " + locator, e);
        }
    }

    public static void SelectingFromDropdown(WebDriver driver, By locator, String value) {
        try {
            GeneralWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
            new Select(FindWebElement(driver, locator)).selectByVisibleText(value);
        } catch (Exception e) {
            LogsUtils.error("Failed to select '" + value + "' from dropdown: " + locator, e);
            throw new RuntimeException("Failed to select '" + value + "' from dropdown: " + locator, e);
        }
    }

    public static void SelectingFromDropdown(WebDriver driver, By locator, int index) {
        try {
            GeneralWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
            new Select(FindWebElement(driver, locator)).selectByIndex(index);
        } catch (Exception e) {
            LogsUtils.error("Failed to select index " + index + " from dropdown: " + locator, e);
            throw new RuntimeException("Failed to select index " + index + " from dropdown: " + locator, e);
        }
    }

    public static WebElement FindWebElement(WebDriver driver, By locator) {
        return driver.findElement(locator);
    }

    public static String getTimeDateStamp() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ssa").format(new Date());
    }

    public static void TakeScreenshot(WebDriver driver, String screenshotName) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            File destination = new File(
                    SCREENSHOTSPATH + screenshotName + "-" + getTimeDateStamp() + ".png");
            FileUtils.writeByteArrayToFile(destination, screenshot);
            Allure.addAttachment(screenshotName, new ByteArrayInputStream(screenshot));
        } catch (Exception e) {
            LogsUtils.error("Failed to capture screenshot: " + screenshotName, e);
        }
    }
}
