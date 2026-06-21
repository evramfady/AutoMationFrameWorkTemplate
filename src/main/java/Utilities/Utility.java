package Utilities;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    public static void DragAndDrop(WebDriver driver, By sourceLocator, By targetLocator) {
        try {
            GeneralWait(driver).until(ExpectedConditions.visibilityOfElementLocated(sourceLocator));
            GeneralWait(driver).until(ExpectedConditions.visibilityOfElementLocated(targetLocator));
            WebElement source = FindWebElement(driver, sourceLocator);
            WebElement target = FindWebElement(driver, targetLocator);
            new Actions(driver).dragAndDrop(source, target).perform();
        } catch (Exception e) {
            LogsUtils.error("Failed to drag element " + sourceLocator + " onto " + targetLocator, e);
            throw new RuntimeException("Failed to drag element " + sourceLocator + " onto " + targetLocator, e);
        }
    }

    public static void DragAndDropByOffset(WebDriver driver, By sourceLocator, int xOffset, int yOffset) {
        try {
            GeneralWait(driver).until(ExpectedConditions.visibilityOfElementLocated(sourceLocator));
            WebElement source = FindWebElement(driver, sourceLocator);
            new Actions(driver).dragAndDropBy(source, xOffset, yOffset).perform();
        } catch (Exception e) {
            LogsUtils.error("Failed to drag element " + sourceLocator + " by offset ("
                    + xOffset + "," + yOffset + ")", e);
            throw new RuntimeException("Failed to drag element " + sourceLocator + " by offset ("
                    + xOffset + "," + yOffset + ")", e);
        }
    }

    public static void UploadFile(WebDriver driver, By locator, String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new IllegalArgumentException("File to upload does not exist: " + file.getAbsolutePath());
            }
            GeneralWait(driver).until(ExpectedConditions.presenceOfElementLocated(locator));
            FindWebElement(driver, locator).sendKeys(file.getAbsolutePath());
            LogsUtils.info("Uploaded file '" + file.getAbsolutePath() + "' to element: " + locator);
        } catch (Exception e) {
            LogsUtils.error("Failed to upload file '" + filePath + "' to element: " + locator, e);
            throw new RuntimeException("Failed to upload file '" + filePath + "' to element: " + locator, e);
        }
    }

    public static void Hover(WebDriver driver, By locator) {
        try {
            GeneralWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
            new Actions(driver).moveToElement(FindWebElement(driver, locator)).perform();
        } catch (Exception e) {
            LogsUtils.error("Failed to hover over element: " + locator, e);
            throw new RuntimeException("Failed to hover over element: " + locator, e);
        }
    }

    public static void DoubleClick(WebDriver driver, By locator) {
        try {
            GeneralWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
            new Actions(driver).doubleClick(FindWebElement(driver, locator)).perform();
        } catch (Exception e) {
            LogsUtils.error("Failed to double-click element: " + locator, e);
            throw new RuntimeException("Failed to double-click element: " + locator, e);
        }
    }

    public static void RightClick(WebDriver driver, By locator) {
        try {
            GeneralWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
            new Actions(driver).contextClick(FindWebElement(driver, locator)).perform();
        } catch (Exception e) {
            LogsUtils.error("Failed to right-click element: " + locator, e);
            throw new RuntimeException("Failed to right-click element: " + locator, e);
        }
    }

    public static void ClickOnElementJS(WebDriver driver, By locator) {
        try {
            GeneralWait(driver).until(ExpectedConditions.presenceOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();", FindWebElement(driver, locator));
        } catch (Exception e) {
            LogsUtils.error("Failed to JS-click element: " + locator, e);
            throw new RuntimeException("Failed to JS-click element: " + locator, e);
        }
    }

    public static String GetAttribute(WebDriver driver, By locator, String attribute) {
        try {
            GeneralWait(driver).until(ExpectedConditions.presenceOfElementLocated(locator));
            return FindWebElement(driver, locator).getAttribute(attribute);
        } catch (Exception e) {
            LogsUtils.error("Failed to get attribute '" + attribute + "' from element: " + locator, e);
            throw new RuntimeException("Failed to get attribute '" + attribute + "' from element: " + locator, e);
        }
    }

    public static boolean IsElementDisplayed(WebDriver driver, By locator) {
        try {
            GeneralWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
            return FindWebElement(driver, locator).isDisplayed();
        } catch (Exception e) {
            LogsUtils.warn("Element not displayed: " + locator);
            return false;
        }
    }

    public static void SwitchToFrame(WebDriver driver, By locator) {
        try {
            GeneralWait(driver).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
            LogsUtils.info("Switched to frame: " + locator);
        } catch (Exception e) {
            LogsUtils.error("Failed to switch to frame: " + locator, e);
            throw new RuntimeException("Failed to switch to frame: " + locator, e);
        }
    }

    public static void SwitchToFrame(WebDriver driver, int index) {
        try {
            GeneralWait(driver).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
            LogsUtils.info("Switched to frame at index: " + index);
        } catch (Exception e) {
            LogsUtils.error("Failed to switch to frame at index: " + index, e);
            throw new RuntimeException("Failed to switch to frame at index: " + index, e);
        }
    }

    public static void SwitchToFrame(WebDriver driver, String nameOrId) {
        try {
            GeneralWait(driver).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrId));
            LogsUtils.info("Switched to frame: " + nameOrId);
        } catch (Exception e) {
            LogsUtils.error("Failed to switch to frame: " + nameOrId, e);
            throw new RuntimeException("Failed to switch to frame: " + nameOrId, e);
        }
    }

    public static void SwitchToDefaultContent(WebDriver driver) {
        try {
            driver.switchTo().defaultContent();
            LogsUtils.info("Switched back to default content.");
        } catch (Exception e) {
            LogsUtils.error("Failed to switch to default content.", e);
            throw new RuntimeException("Failed to switch to default content.", e);
        }
    }

    public static void SwitchToParentFrame(WebDriver driver) {
        try {
            driver.switchTo().parentFrame();
            LogsUtils.info("Switched to parent frame.");
        } catch (Exception e) {
            LogsUtils.error("Failed to switch to parent frame.", e);
            throw new RuntimeException("Failed to switch to parent frame.", e);
        }
    }

    // ---------- Waits ----------

    public static void WaitForVisibility(WebDriver driver, By locator) {
        try {
            GeneralWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            LogsUtils.error("Element never became visible: " + locator, e);
            throw new RuntimeException("Element never became visible: " + locator, e);
        }
    }

    public static void WaitForClickability(WebDriver driver, By locator) {
        try {
            GeneralWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            LogsUtils.error("Element never became clickable: " + locator, e);
            throw new RuntimeException("Element never became clickable: " + locator, e);
        }
    }

    public static boolean WaitForElementToDisappear(WebDriver driver, By locator) {
        try {
            return GeneralWait(driver).until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (Exception e) {
            LogsUtils.error("Element never disappeared: " + locator, e);
            throw new RuntimeException("Element never disappeared: " + locator, e);
        }
    }

    public static boolean WaitForTextToBePresent(WebDriver driver, By locator, String text) {
        try {
            return GeneralWait(driver).until(
                    ExpectedConditions.textToBePresentInElementLocated(locator, text));
        } catch (Exception e) {
            LogsUtils.error("Text '" + text + "' never appeared in element: " + locator, e);
            throw new RuntimeException("Text '" + text + "' never appeared in element: " + locator, e);
        }
    }

    public static void WaitForPageLoad(WebDriver driver) {
        try {
            GeneralWait(driver).until(d ->
                    "complete".equals(((JavascriptExecutor) d).executeScript("return document.readyState")));
        } catch (Exception e) {
            LogsUtils.error("Page did not finish loading in time.", e);
            throw new RuntimeException("Page did not finish loading in time.", e);
        }
    }

    // ---------- Alerts ----------

    public static void AcceptAlert(WebDriver driver) {
        try {
            GeneralWait(driver).until(ExpectedConditions.alertIsPresent()).accept();
            LogsUtils.info("Alert accepted.");
        } catch (Exception e) {
            LogsUtils.error("Failed to accept alert.", e);
            throw new RuntimeException("Failed to accept alert.", e);
        }
    }

    public static void DismissAlert(WebDriver driver) {
        try {
            GeneralWait(driver).until(ExpectedConditions.alertIsPresent()).dismiss();
            LogsUtils.info("Alert dismissed.");
        } catch (Exception e) {
            LogsUtils.error("Failed to dismiss alert.", e);
            throw new RuntimeException("Failed to dismiss alert.", e);
        }
    }

    public static String GetAlertText(WebDriver driver) {
        try {
            return GeneralWait(driver).until(ExpectedConditions.alertIsPresent()).getText();
        } catch (Exception e) {
            LogsUtils.error("Failed to read alert text.", e);
            throw new RuntimeException("Failed to read alert text.", e);
        }
    }

    public static void SendDataToAlert(WebDriver driver, String data) {
        try {
            Alert alert = GeneralWait(driver).until(ExpectedConditions.alertIsPresent());
            alert.sendKeys(data);
            alert.accept();
            LogsUtils.info("Sent text to alert and accepted.");
        } catch (Exception e) {
            LogsUtils.error("Failed to send data to alert.", e);
            throw new RuntimeException("Failed to send data to alert.", e);
        }
    }

    // ---------- Windows / Tabs ----------

    public static void SwitchToNewWindow(WebDriver driver) {
        try {
            String current = driver.getWindowHandle();
            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(current)) {
                    driver.switchTo().window(handle);
                    LogsUtils.info("Switched to new window: " + handle);
                    return;
                }
            }
            LogsUtils.warn("No other window found to switch to.");
        } catch (Exception e) {
            LogsUtils.error("Failed to switch to new window.", e);
            throw new RuntimeException("Failed to switch to new window.", e);
        }
    }

    public static void SwitchToWindowByTitle(WebDriver driver, String title) {
        try {
            Set<String> handles = driver.getWindowHandles();
            for (String handle : handles) {
                driver.switchTo().window(handle);
                if (driver.getTitle().equals(title)) {
                    LogsUtils.info("Switched to window with title: " + title);
                    return;
                }
            }
            throw new NoSuchWindowException("No window found with title: " + title);
        } catch (Exception e) {
            LogsUtils.error("Failed to switch to window with title: " + title, e);
            throw new RuntimeException("Failed to switch to window with title: " + title, e);
        }
    }

    public static void CloseCurrentTabAndReturn(WebDriver driver, String returnToHandle) {
        try {
            driver.close();
            driver.switchTo().window(returnToHandle);
            LogsUtils.info("Closed current tab and returned to: " + returnToHandle);
        } catch (Exception e) {
            LogsUtils.error("Failed to close tab and return to: " + returnToHandle, e);
            throw new RuntimeException("Failed to close tab and return to: " + returnToHandle, e);
        }
    }

    // ---------- Navigation ----------

    public static void NavigateTo(WebDriver driver, String url) {
        try {
            driver.navigate().to(url);
            LogsUtils.info("Navigated to: " + url);
        } catch (Exception e) {
            LogsUtils.error("Failed to navigate to: " + url, e);
            throw new RuntimeException("Failed to navigate to: " + url, e);
        }
    }

    public static void Refresh(WebDriver driver) {
        try {
            driver.navigate().refresh();
            LogsUtils.info("Page refreshed.");
        } catch (Exception e) {
            LogsUtils.error("Failed to refresh page.", e);
            throw new RuntimeException("Failed to refresh page.", e);
        }
    }

    public static void NavigateBack(WebDriver driver) {
        try {
            driver.navigate().back();
            LogsUtils.info("Navigated back.");
        } catch (Exception e) {
            LogsUtils.error("Failed to navigate back.", e);
            throw new RuntimeException("Failed to navigate back.", e);
        }
    }

    public static void NavigateForward(WebDriver driver) {
        try {
            driver.navigate().forward();
            LogsUtils.info("Navigated forward.");
        } catch (Exception e) {
            LogsUtils.error("Failed to navigate forward.", e);
            throw new RuntimeException("Failed to navigate forward.", e);
        }
    }

    // ---------- Misc helpers ----------

    public static void SelectingFromDropdownByValue(WebDriver driver, By locator, String value) {
        try {
            GeneralWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
            new Select(FindWebElement(driver, locator)).selectByValue(value);
        } catch (Exception e) {
            LogsUtils.error("Failed to select value '" + value + "' from dropdown: " + locator, e);
            throw new RuntimeException("Failed to select value '" + value + "' from dropdown: " + locator, e);
        }
    }

    public static List<String> GetAllTexts(WebDriver driver, By locator) {
        try {
            GeneralWait(driver).until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
            List<String> texts = new ArrayList<>();
            for (WebElement element : driver.findElements(locator)) {
                texts.add(element.getText());
            }
            return texts;
        } catch (Exception e) {
            LogsUtils.error("Failed to get texts from elements: " + locator, e);
            throw new RuntimeException("Failed to get texts from elements: " + locator, e);
        }
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
