package Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * Runs ALL scenarios. Shared options (glue, features, plugins) are read from
 * src/test/resources/cucumber.properties, so nothing needs to be repeated here.
 */
@CucumberOptions
public class TestRunner extends AbstractTestNGCucumberTests {
}
