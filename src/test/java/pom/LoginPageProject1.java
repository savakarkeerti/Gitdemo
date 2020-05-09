package pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.Baseclass;

public class LoginPageProject1 extends Baseclass {

	public LoginPageProject1(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	WebDriver driver;

	@FindBy(id = "Username")
	private WebElement usn;

	@FindBy(id = "password")
	private WebElement pwd;

	@FindBy(id = "submit")
	private WebElement submit;

	public void enterUsn(String username) {
		usn.sendKeys(username);

	}

	public void enterPwd(String password) {
		pwd.sendKeys(password);

	}

	public void clickSubmit() {
		submit.click();
	}

}
