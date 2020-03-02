package PageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import DemoFramework.basePage;

public class LoginPage extends basePage{

	public static final Logger log = Logger.getLogger(LoginPage.class.getName());
	
	@FindBy(xpath = "//span[text()= 'Sign in']")
	public WebElement SignUp;
	
	@FindBy(id ="LoginEmail")
	public WebElement user;
	
	@FindBy(id="LoginPassword")
	public WebElement password;
	
	@FindBy(xpath = "//button[@class='btn signInBtnTop']")
	public WebElement submit;
	
		
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	public Object doLogin(String usr, String pwd) {
		Actions builder = new Actions(driver);
		Action moveOverSignin = builder.moveToElement(SignUp).build();
		moveOverSignin.perform();
		user.sendKeys(usr);
		password.sendKeys(pwd);
		submit.click();
		
		boolean loginSuccess = isElementPresent("//div[contains(text(),'PERSONAL')]");
		if(loginSuccess == true) {
			return PageFactory.initElements(driver, ProfilePage.class);		
		}
		else
			return PageFactory.initElements(driver, LoginPage.class);
	}
}
