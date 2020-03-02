package TestDriver;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import DemoFramework.baseTest;
import PageObjects.LoginPage;
import PageObjects.ProfilePage;
import Utilities.PropertyFile;
import Utilities.dataUtility;

public class LoginTest extends baseTest{

	public LoginTest() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	String testCase ="LoginTest";
		
	@Test(dataProvider = "getData")
	public void doLogging(Hashtable<String,String> table) throws InterruptedException, IOException {
		
				log.info("Initiate browser..");
				setDriver(table.get("Browser"), PropertyFile.getObject().getProperty("URL"));
				LoginPage l = new LoginPage(driver);
				log.info("Login to the application by entering user credentials");
				Object page = l.doLogin(table.get("UserName"), table.get("Password"));
				if(page instanceof LoginPage) {
					log.info("Login unsuccessful");
					Assert.fail("Login failed");
				}
				log.info("Login successful");
				ProfilePage p = (ProfilePage)page;
				p.selectFromMenu();
				
		}
	
	@DataProvider
	public Object[][] getData() throws IOException{
		return dataUtility.getData(ex, testCase);
	}
}
