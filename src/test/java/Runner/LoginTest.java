package Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * Runs only the scenarios tagged @login. Shared options come from cucumber.properties;
 * this runner just adds the tag filter. Copy this class per feature/tag (e.g. CheckoutRunner
 * with tags = "@checkout") to make each suite selectable from testng.xml.
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"StepDefinitions", "Hooks"},
        tags = "@login"
)
public class LoginTest extends AbstractTestNGCucumberTests {
}

