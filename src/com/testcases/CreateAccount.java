package com.testcases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.testcases.SauceLabConfiguration;

public class CreateAccount {
	
	static WebDriver driver;
	public static final String URL = "https://" + SauceLabConfiguration.USERNAME + ":" + SauceLabConfiguration.ACCESS_KEY
			+ "@ondemand.saucelabs.com:443/wd/hub";

	@Test(priority=1)
	public static void testng() throws InterruptedException,
			MalformedURLException {
		//configuring the sauce lab capabilities 
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setCapability("platform", "Windows 10");
		caps.setCapability("name", "Creating Account and pushing it to DSM");
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
		driver.findElement(By.xpath("//*[@id='AllTab_Tab']/a/img")).click();
		//Thread.sleep(2000);
		driver.findElement(
				By.xpath("//*[@id='bodyCell']/div[3]/div[2]/table/tbody/tr[1]/td[1]/a"))
				.click();
		//Thread.sleep(3000);

		driver.findElement(
				By.xpath("//*[@id='hotlist']/table/tbody/tr/td[2]/input"))
				.click();
		driver.findElement(By.xpath("//*[@id='acc2']")).sendKeys("Automated Account 16");
		driver.findElement(By.xpath("//*[@id='topButtonRow']/input[1]"))
				.click();
		driver.findElement(By.xpath("//*[@id='topButtonRow']/input[6]"))
				.click();
		//Thread.sleep(3000);

		Alert alert = driver.switchTo().alert();
		alert.accept();
		Thread.sleep(4000);

		
		String str = driver.findElement(By.xpath("///*[@id='bodyCell']/div[1]/div/div/h2"))
				.getText();
		//verifying the result
		assertEquals(str, "Create New DSM Company From Salesforce Account");
		ITestResult result = Reporter.getCurrentTestResult();

}
	@AfterMethod
	/*
	 * 
	 * 
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

