package com.testcases;

import static org.testng.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.testcases.SauceLabConfiguration;

public class LoginTest {

	static WebDriver driver;
	public static final String URL = "https://" + SauceLabConfiguration.USERNAME + ":" + SauceLabConfiguration.ACCESS_KEY
			+ "@ondemand.saucelabs.com:443/wd/hub";

	@Test()
	public static void testng() throws InterruptedException,
			MalformedURLException {
		//configuring the sauce lab capabilities 
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setCapability("platform", "Windows 10");
		caps.setCapability("name", "Login page verification");
		caps.setCapability("version", "59.0");
		
		//creating the remote driver
		driver = new RemoteWebDriver(new URL(URL), caps);
		
		//opening the browser in sauce lab
		driver.get("https://login.salesforce.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(50L, TimeUnit.SECONDS);
		
		//input the user credential
		driver.findElement(By.xpath("//*[@id='username']")).sendKeys(
				"testgoogledc_qa@10kview.com");
		driver.findElement(By.xpath("//*[@id='password']")).sendKeys(
				"C0ncret101234");
		//submit the login button
		driver.findElement(By.xpath("//*[@id='Login']")).click();
		
		Thread.sleep(10000);
		
		String str = driver.findElement(By.xpath("//*[@id='userNavLabel']"))
				.getText();
		//verifying the result
		assertEquals(str, "test googledcqa");
		ITestResult result = Reporter.getCurrentTestResult();
	}

	
	
	@AfterMethod
	/*
	   showing the result status on sauce lab 
	*/
	public static void afterMethod(ITestResult result) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("sauce:job-result=passed");
			if (result.getStatus() == ITestResult.SUCCESS)
				js.executeScript("sauce:job-result=passed");
			else if (result.getStatus() == ITestResult.FAILURE)
				js.executeScript("sauce:job-result=failed");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.quit();
		}
	}
}
