# AutoMationFrameWorkTemplate

A BDD UI test-automation framework template for web applications, built with **Selenium**, **Cucumber**, and **TestNG** on **Java 21** and **Maven**. It provides a clean, reusable foundation: thread-safe driver management, Gherkin feature files, reusable interaction utilities, and rich reporting via Allure.

---

## Tech Stack

| Concern            | Tool                          | Version  |
|--------------------|-------------------------------|----------|
| Language           | Java                          | 21       |
| Build              | Maven                         | 3.9.x    |
| Browser automation | Selenium                      | 4.33.0   |
| BDD                | Cucumber JVM                  | 7.23.0   |
| Test runner        | TestNG                        | 7.11.0   |
| Reporting          | Allure                        | 2.29.1   |
| Logging            | Log4j2 (+ SLF4J bridge)       | 2.24.3   |
| Test data          | Gson (JSON), Properties       | 2.10.1   |
| Fixtures           | JavaFaker                     | 1.0.2    |

---

## Prerequisites

- **JDK 21** on the `PATH`, with `JAVA_HOME` pointing at it
- **Maven 3.9+** on the `PATH`
- A supported browser installed: **Chrome**, **Edge**, or **Firefox** (Selenium Manager auto-resolves drivers)
- *(Optional)* Allure CLI to view reports locally

---

## Project Structure

```
AutoMationFrameWorkTemplate/
├── pom.xml                       # Dependencies, Java 21, Surefire -> testng.xml
├── testng.xml                    # Suite file: choose which scenarios to run
├── src/
│   ├── main/java/
│   │   ├── DriverFactory/        # Thread-safe WebDriver creation (private/incognito, headless)
│   │   └── Utilities/            # Reusable helpers
│   │       ├── Utility.java      #   waits, click/type/select, screenshots
│   │       ├── DataUtils.java    #   read JSON / .properties test data
│   │       └── LogsUtils.java    #   Log4j2 wrapper with caller info
│   ├── main/resources/
│   │   ├── log4j2.properties      # Console + file logging config
│   │   └── allure.properties      # Allure results directory
│   └── test/
│       ├── java/
│       │   ├── Hooks/             # @Before/@After: launch & quit driver per scenario
│       │   ├── Runner/            # TestNG Cucumber runners (TestRunner, LoginRunner)
│       │   └── StepDefinitions/   # Gherkin step implementations
│       └── resources/
│           ├── features/          # .feature files (Gherkin scenarios)
│           ├── cucumber.properties # Shared glue / features / plugins config
│           └── TestData/           # environments.properties + test data files
└── testOutputs/                   # Generated reports, logs, screenshots (git-ignored)
```

---

## Configuration

Default run settings live in `src/test/resources/TestData/environments.properties`:

```properties
browser=chrome
baseUrl=https://www.saucedemo.com/
```

Any key can be overridden at runtime with `-D`, e.g. `-Dbrowser=edge`. Supported overrides:

| Property   | Values                          | Default                     |
|------------|---------------------------------|-----------------------------|
| `browser`  | `chrome`, `edge`, `firefox`     | `chrome`                    |
| `baseUrl`  | any URL                         | `https://www.saucedemo.com/`|
| `headless` | `true`, `false`                 | `false`                     |

Each scenario runs in a **fresh, private-mode** browser (Chrome `--incognito`, Edge `--inprivate`, Firefox `-private`); cookies and local/session storage are cleared after navigation, and the driver is fully quit afterwards — guaranteeing full isolation between scenarios.

---

## Running Tests

The Surefire plugin runs whatever is enabled in `testng.xml` (defaults to the `Login` suite).

Run the default suite:
```bash
mvn clean test
```

Run headless with a specific browser:
```bash
mvn clean test -Dheadless=true -Dbrowser=chrome
```

Run only scenarios with a given tag (independent of `testng.xml`):
```bash
mvn clean test -Dcucumber.filter.tags="@login"
```

Run a different suite file:
```bash
mvn clean test -DsuiteXmlFile=regression.xml
```

From IntelliJ: right-click `testng.xml` → **Run**, or right-click any `.feature` file → **Run**.

---

## Selecting What to Run

`testng.xml` controls which scenarios execute. Each `<test>` block points at a Cucumber
runner whose `@CucumberOptions(tags = ...)` decides the scope. Enable a block by uncommenting it:

```xml
<test name="Login">
    <classes>
        <class name="Runner.LoginRunner"/>   <!-- runs @login scenarios -->
    </classes>
</test>
```

To add a new feature suite:
1. Write a `.feature` file under `src/test/resources/features/` and tag it (e.g. `@checkout`).
2. Create a runner, e.g. `Runner/CheckoutRunner.java`, with `@CucumberOptions(tags = "@checkout")`.
3. Add a `<test>` block in `testng.xml` referencing `Runner.CheckoutRunner`.

`Runner.TestRunner` (no tag filter) runs **all** scenarios.

---

## Reports

After a run, artifacts are written to `testOutputs/`:

- **Cucumber HTML/JSON** — `testOutputs/cucumber/report.html` / `report.json`
- **Allure results** — `testOutputs/target/allure-results`
- **Logs** — `testOutputs/Logs/`
- **Failure screenshots** — captured automatically on scenario failure and attached to the report

Serve the Allure report (requires the Allure CLI):
```bash
allure serve testOutputs/target/allure-results
```

---

## Writing a New Test

1. **Feature** — add a scenario in a `.feature` file using Gherkin (`Given/When/Then`).
2. **Steps** — implement matching steps in a class under `StepDefinitions/`, fetching the
   driver via `DriverFactory.getDriver()` and using `Utility` helpers for interactions.
3. **Data** — put any test data in `src/test/resources/TestData/` and read it with `DataUtils`.

No base class or manual driver setup is needed — the `Hooks` class handles browser lifecycle
for every scenario automatically.
