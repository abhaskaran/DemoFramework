package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

public class PropertyFile {
	
	public WebDriver driver;
	
	public static Properties getObject() throws IOException{
		String workingDirectory = System.getProperty("user.dir");
		FileInputStream objfile = new FileInputStream(new File(workingDirectory+"\\Resources\\Object.properties"));
		Properties obj = new Properties();
		obj.load(objfile);
		return obj;
	}
	
	public boolean getPageTitle(String title) throws IOException{
		if (driver.getPageSource().contains(title)) return true;			
		return false;		
	}	
	
}
