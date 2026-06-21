package Listeners;

import Utilities.LogsUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ITesrListener implements ITestListener {

    public void onTestStart(ITestResult result) {
        LogsUtils.info("Test started: " + result.getName());
    }

    public void onTestSuccess(ITestResult result) {
        LogsUtils.info("Test passed: " + result.getName());
    }

    public void onTestSkipped(ITestResult result) {
        LogsUtils.warn("Test skipped: " + result.getName());
    }



}
