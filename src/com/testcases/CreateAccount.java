package com.testcases;

import static org.testng.Assert.assertEquals;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import com.testcases.SauceLabConfiguration;

public class CreateAccount {

	static WebDriver driver;
	public static final String URL = "https://"
			+ SauceLabConfiguration.USERNAME + ":"
			+ SauceLabConfiguration.ACCESS_KEY
			+ "@ondemand.saucelabs.com:443/wd/hub";

	@Test
	public static void testng() throws InterruptedException,
			MalformedURLException {
		// configuring the sauce lab capabilities
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setCapability("platform", "Windows 10");
		caps.setCapability("name", "Login, Creating Account and pushing it to DSM");
		caps.setCapability("version", "59.0");

		// creating the remote driver
		driver = new RemoteWebDriver(new URL(URL), caps);

		// opening the browser in sauce lab
		driver.get("https://login.salesforce.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(50L, TimeUnit.SECONDS);

		// input the user credential
		driver.findElement(By.xpath("//*[@id='username']")).sendKeys(
				SauceLabConfiguration.SF_USERNAME);
		driver.findElement(By.xpath("//*[@id='password']")).sendKeys(
				SauceLabConfiguration.SF_PASSWORD);
		// submit the login button
		driver.findElement(By.xpath("//*[@id='Login']")).click();

		Thread.sleep(10000);
		driver.findElement(By.xpath("//*[@id='AllTab_Tab']/a/img")).click();
		// Thread.sleep(2000);
		driver.findElement(
				By.xpath("//*[@id='bodyCell']/div[3]/div[2]/table/tbody/tr[1]/td[1]/a"))
				.click();
		// Thread.sleep(3000);

		driver.findElement(
				By.xpath("//*[@id='hotlist']/table/tbody/tr/td[2]/input"))
				.click();
		driver.findElement(By.xpath("//*[@id='acc2']")).sendKeys(
				"Automated Account 105");
		driver.findElement(By.xpath("//*[@id='topButtonRow']/input[1]"))
				.click();
		driver.findElement(By.xpath("//*[@id='topButtonRow']/input[6]"))
				.click();
		// Thread.sleep(3000);

		Alert alert = driver.switchTo().alert();
		alert.accept();
		Thread.sleep(4000);// *[@id='identifier'] //*[@id='identifier']

		driver.findElement(
				By.xpath("//*[@id='pg:j_id1:j_id27:j_id40:bottom']/input"))
				.click();
		Thread.sleep(2000);
		String ParentWindowHandle = driver.getWindowHandle();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//*[@id='00N7F000004I6L1_ileinner']/a"))
				.click();// *[@id='00N7F000004I6L1_ileinner']/a

		Thread.sleep(3000);

		for (String childTab : driver.getWindowHandles()) {
			driver.switchTo().window(childTab);

		}

		driver.findElement(By.xpath("//*[@id='identifierId']")).sendKeys(
				"10kdfptestuser@10kview.com");
		driver.findElement(By.xpath("//*[@id='identifierNext']/content/span"))
				.click();
		Thread.sleep(2000);
		driver.findElement(
				By.xpath("//*[@id='password']/div[1]/div/div[1]/input"))
				.sendKeys("concret123");
		driver.findElement(By.xpath("//*[@id='passwordNext']/content/span"))
				.click();
		Thread.sleep(4000);
		/*driver.findElement(By.id("knowledge-preregistered-email-response"))
				.sendKeys("matt@10Kview.com");*/
		Thread.sleep(8000);

		System.out.println("Name of the Company:  "+driver.findElement(
				By.xpath("//*[@id='gwt-debug-companyDetails-header']/table/tbody/tr/td[2]/div[2]/span/strong"))
				.getText());
		
		String str = driver
				.findElement(
						By.xpath("//*[@id='gwt-debug-companyDetails-header']/table/tbody/tr/td[2]/div[2]/span/strong"))
				.getText();
		// verifying the result
		assertEquals(str, "Automated Account 105");
		// ITestResult result = Reporter.getCurrentTestResult();

	}

	@AfterMethod
	/*
	 * 
	 * 
	 * showing the result status on sauce lab
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
