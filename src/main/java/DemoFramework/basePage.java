package DemoFramework;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import Utilities.PropertyFile;

public class basePage  {
	
	public WebDriver driver;
	
	public basePage(){}
	
	public basePage(WebDriver driver) {
		
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public Properties getObject() throws IOException{
		FileInputStream objfile = new FileInputStream(System.getProperty("user.dir")+"\\Resources\\Object.properties");
		Properties obj = new Properties();
		obj.load(objfile);
		return obj;
	}
	
	public boolean getPageTitle(String title) throws IOException{
		if (driver.getPageSource().contains(title)) return true;			
		return false;		
	}	
	public boolean isElementPresent(String locator) {
		int s = driver.findElements(By.xpath(locator)).size();
		if(s==0) //element not present since the size of elements found is 0
			return false;
		else
			return true;
	}
	
}
