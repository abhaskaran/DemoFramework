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
import org.testng.annotations.BeforeClass;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import Utilities.ExcelUtility;
import Utilities.PropertyFile;

public class baseTest extends PropertyFile{
	
	public static final Logger log = Logger.getLogger(basePage.class.getName());
	//public static final Logger log = Logger.getLogger(BaseTest.class.getName());
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

			@AfterMethod
			public void takeScreenshotIfFailed(ITestResult result) throws IOException {
				if (ITestResult.FAILURE == result.getStatus()) {
					try {
						log.info("Taking failed test screenshot..");
						//String failedTest = result.getName();
						File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
						FileUtils.copyFile(scrFile, new File("./FailedScreenShots/" + "failed_" + result.getName().toLowerCase()+ "_" + timestamp() + ".png"));
						//FileUtils.copyFile(scrFile, new File("FailedScreenShots"));
				} catch (Exception e) {
						System.out.println(e.getStackTrace());
					}
				}
			}
			
			
			@AfterClass(alwaysRun = true)
			public void closeBrowser() {
				driver.close();
				driver.quit();
				log.info("Clean up activity: Closed all browser instances..");
			}
			
}
