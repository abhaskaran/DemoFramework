package PageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import DemoFramework.basePage;

public class ProfilePage extends basePage{

	@FindBy(xpath = "//li[@class = 'has-dropdown active']/a")
	public WebElement menu;
	
	@FindBy(xpath = "//div[@class = 'border']/span[text()= 'Organic']")
	public WebElement coffeeType;
	
	public ProfilePage(WebDriver driver) {
		super(driver);
	}	
	public Object selectFromMenu() {
		menu.click();
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView();,coffeeType");
		coffeeType.click();
		boolean menuSelectionSuccess = isElementPresent("//span[starts-with(text(),'East')]");
		if(menuSelectionSuccess == true) {
			return PageFactory.initElements(driver, OrganicCoffeeListPage.class);
		}
		else
			return PageFactory.initElements(driver, ProfilePage.class);
		
	}
}
