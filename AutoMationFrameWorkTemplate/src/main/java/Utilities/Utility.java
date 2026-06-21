package Utilities;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class Utility {

    private static final String  SCREENSHOTSPATH = "testOutputs/ScreenShots/";

    public static void ClickOnElement(WebDriver driver , By locator) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(locator));
            FindWebElement(driver,locator).click();
        } catch (Exception e) {
            System.out.println("Error clicking on element: " + e.getMessage());
        }
    }

    public static void SendData(WebDriver driver , By locator, String data) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            FindWebElement(driver,locator).sendKeys(data);
        } catch (Exception e) {
            System.out.println("Error typing on element: " + e.getMessage());
        }
    }

    public static String GetText(WebDriver driver, By locator) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            System.out.println("Error getting text from element: " + e.getMessage());
        }
        return FindWebElement(driver,locator).getText();
    }
    public static WebDriverWait GeneralWait(WebDriver driver) {
            return new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public static void ScrollToElementJS(WebDriver driver, By locator) {
        try {
            ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",
                    FindWebElement(driver, locator));
        } catch (Exception e) {
            System.out.println("Error scrolling to element: " + e.getMessage());
        }
    }

    public static WebElement FindWebElement(WebDriver driver,By locator) {
        return  driver.findElement(locator);
    }

    public static String getTimeDateStamp() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ssa").format(new Date());
    }

    public static void TakeScreenshot(WebDriver driver, String ScreenshotName) {
        try {
            File ScreenshotSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File ScreenshotDes = new File(SCREENSHOTSPATH + ScreenshotName + "-" + getTimeDateStamp() + ".png");
            FileUtils.copyFile(ScreenshotSrc, ScreenshotDes);

            Allure.addAttachment(ScreenshotName, Files.newInputStream(Path.of(ScreenshotDes.getPath())));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void SelectingFromDropdown(WebDriver driver, By locator, String value) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(locator));
            new Select(FindWebElement(driver, locator)).selectByVisibleText(value);
        } catch (Exception e) {
            System.out.println("Error selecting from dropdown: " + e.getMessage());
        }
    }
    public static void SelectingFromDropdown(WebDriver driver, By locator, int value) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(locator));
            new Select(FindWebElement(driver, locator)).selectByIndex(value);
        } catch (Exception e) {
            System.out.println("Error selecting from dropdown: " + e.getMessage());
        }
    }

}
