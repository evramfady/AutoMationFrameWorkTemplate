package StepDefinitions;

import DriverFactory.DriverFactory;
import Utilities.Utility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginSteps {

    // Fetched lazily: glue classes are constructed before the @Before hook sets up the driver.
    private WebDriver driver() {
        return DriverFactory.getDriver();
    }

    @Given("the user is on the login page")
    public void the_user_is_on_the_login_page() {
        Assert.assertTrue(
                Utility.FindWebElement(driver(), By.id("user-name")).isDisplayed(),
                "Login page username field was not displayed.");
    }

    @When("the user logs in with username {string} and password {string}")
    public void the_user_logs_in_with(String username, String password) {
        Utility.SendData(driver(), By.id("user-name"), username);
        Utility.SendData(driver(), By.id("password"), password);
        Utility.ClickOnElement(driver(), By.id("login-button"));
    }

    @Then("the products page should be displayed")
    public void the_products_page_should_be_displayed() {
        Assert.assertTrue(
                driver().getCurrentUrl().contains("inventory"),
                "Expected to land on the inventory/products page.");
    }

    @Then("an error message should be shown")
    public void an_error_message_should_be_shown() {
        String error = Utility.GetText(driver(), By.cssSelector("[data-test='error']"));
        Assert.assertFalse(error.isBlank(), "Expected a login error message to be shown.");
    }

    @Then("the error message {string} should be shown")
    public void the_error_message_should_be_shown(String expected) {
        String actual = Utility.GetText(driver(), By.cssSelector("[data-test='error']"));
        Assert.assertTrue(
                actual.contains(expected),
                "Expected error message to contain:\n  " + expected + "\nbut was:\n  " + actual);
    }
}
