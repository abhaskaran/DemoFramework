package DemoFramework;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import Utilities.ExcelUtility;
import Utilities.PropertyFile;

public class baseTest extends PropertyFile{
	
	public static final Logger log = Logger.getLogger(basePage.class.getName());
	//public static final Logger log = Logger.getLogger(BaseTest.class.getName());
	
	public ExtentHtmlReporter reporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	public baseTest() throws IOException{	
	}
	
	public ExcelUtility ex = new ExcelUtility(System.getProperty("user.dir")+"\\Resources",PropertyFile.getObject().getProperty("Excel_Name"));
	
	public void setDriver(String browserType, String baseURL) throws IOException {
				
		if(browserType.equalsIgnoreCase("Chrome")){
			log.info("Launching google chrome browser..");
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+ "\\Resources\\chromedriver.exe");
			driver = new ChromeDriver();
			}
			else if(browserType.equalsIgnoreCase("Mozilla")) {
			log.info("Launching google Firefox browser..");
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+ "\\src\\test\\resources\\config\\geckodriver.exe");
			driver = new FirefoxDriver();
			}
		log.info("Launch the application url");
		driver.get(PropertyFile.getObject().getProperty("URL"));
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);	
		driver.manage().window().maximize();
	}
	//time stamp method
			private static String timestamp() {
				return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
			}

			@BeforeClass(alwaysRun = true)
			public void logging() {
				try {
					String log4jConfPath = System.getProperty("user.dir")+"\\Resources\\log4j.properties";
					PropertyConfigurator.configure(log4jConfPath);
				} catch (Exception e) {
					System.out.println("Error:" + e.getStackTrace());
				}
			}
			
			@BeforeTest
			public void setExtentReport() {
				reporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"//test-output//projectExtentReport.html");
				reporter.config().setDocumentTitle("Automation Test Report");
				
				extent = new ExtentReports();
				extent.attachReporter(reporter);
				extent.setSystemInfo("Browser", "Chrome");
				
			}
			
			@AfterTest
			public void endReport() {
				extent.flush();
			}

			
			public String takeScreenshotIfFailed() throws IOException {
								
						log.info("Taking failed test screenshot..");
						//String failedTest = result.getName();
						File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
						//String failedTest = result.getName();
						String destination = System.getProperty("user.dir")+"//FailedScreenShots//" + "failed_" +"_"+ timestamp()+".png";
						File finalDestination = new File(destination);
						//FileUtils.copyFile(scrFile, new File("./FailedScreenShots/" + "failed_" + result.getName().toLowerCase()+ "_" + timestamp() + ".png"));
						FileUtils.copyFile(scrFile,finalDestination);
						return destination;
			}
			
			@AfterMethod
			public void extentReportInfo(ITestResult result) throws IOException {
				if(result.getStatus() == ITestResult.FAILURE) {
					test.log(Status.FAIL, "Testcase failed is "+result.getName());
					test.addScreenCaptureFromPath(takeScreenshotIfFailed());
				}
				
			}
			
			
			
			
			@AfterClass(alwaysRun = true)
			public void closeBrowser() {
				driver.close();
				driver.quit();
				log.info("Clean up activity: Closed all browser instances..");
			}
			
}
