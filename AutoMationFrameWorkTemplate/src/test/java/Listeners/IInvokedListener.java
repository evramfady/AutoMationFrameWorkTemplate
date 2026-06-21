package Listeners;

import Utilities.Utility;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import static DriverFactory.DriverFactory.getDriver;

public class IInvokedListener implements IInvokedMethodListener {

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        System.out.println("Starting test: " + testResult.getName());
        if (method.isTestMethod()) {
            System.out.println("Executing test method: " + method.getTestMethod().getMethodName());
        } else {
            System.out.println("Executing configuration method: " + method.getTestMethod().getMethodName());
        }
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if(testResult.getStatus()==ITestResult.FAILURE) {
            System.out.println("Test failed: " + testResult.getName());
            System.out.println("Error message: " + testResult.getThrowable().getMessage());
            Utility.TakeScreenshot(getDriver(), testResult.getName());

        } else if(testResult.getStatus() == ITestResult.SUCCESS) {
            System.out.println("Test passed: " + testResult.getName());
        } else if(testResult.getStatus() == ITestResult.SKIP) {
            System.out.println("Test skipped: " + testResult.getName());
        }
    }
}
