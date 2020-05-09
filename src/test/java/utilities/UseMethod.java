package utilities;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UseMethod extends Baseclass {
	

	public void selectFromDropdown_old(WebElement ele, String text) {
		
		logger.info("Selecting value : " + text + " from dropdown : " + ele);
		threadSleep(500);
		ele.click();
		;
		String id = ele.getAttribute("id");

		if (!id.isBlank()) {
			driver.findElement(By.xpath("//*[@id='" + id + "']/div/ul/li/span[contains(text(),'" + text + "')]"))
					.click();
		} else {
			driver.findElement(By.xpath("//ul[contains(@class,'active')]/li/span[contains(text(),'" + text + "')]"))
					.click();
		}
	}

	

	public void threadSleep(long sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}

	public void waitForElementVisibility(WebElement ele) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		try {
			wait.until(
					ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"dyn-container\"]/div/div/p")));
			wait.until(ExpectedConditions
					.invisibilityOfElementLocated(By.xpath("//*[contains(@id,'materialize-lean-overlay')]")));
			wait.until(ExpectedConditions.visibilityOf(ele));
		} catch (TimeoutException e) {
			System.out.println(ele + "Element was not visible before the Timeout");
		} catch (Exception e1) {
			System.out.println("Exception Occurred");
			e1.printStackTrace();
		}
	}
	
	public void waitForElementToBeClickable(WebElement ele) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		try {
			// wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"dyn-container\"]/div/div/p")));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
		} catch (TimeoutException e) {
			System.out.println(ele + "Element was not clickable before the Timeout");
		}
	}
	
	public void scrollintoViewAndClick(WebElement ele) {
		// waitForElementVisibility(ele);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView()", ele);
		ele.click();
	}

	public void clickThroughJS(WebElement ele) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click()", ele);
	}

	public File takeScreenshot() {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		String imgName = getTimeString();
		String dest = System.getProperty("user.dir") + "\\test-output\\MTapScreenshots\\" + imgName + ".png";
		File destFile = new File(dest);
		try {
			FileUtils.copyFile(srcFile, destFile);
			// System.out.println("Scrrenshot Captured and Dest Path is : " + dest);
		} catch (IOException e) {
			// System.out.println("Couldn't Copy the screenshot file to the destination : "
			// + dest);
			e.printStackTrace();
		}
		return destFile;
	}
	
	public String getTimeString() {
		Date now = new Date();
		String timeString = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH).format(now);
		return timeString;
	}
	
	public String getRandomDecimalString(int unitNumber, int numberOfDecimalDigits) {
		String str = "#.";
		for (int i = 0; i < numberOfDecimalDigits; i++) {
			str = str + "0";
		}
		double decimalNumber = unitNumber + Math.random();
		DecimalFormat numberFormat = new DecimalFormat(str);
		String decimalStr = numberFormat.format(decimalNumber);
		return decimalStr;
	}
	
	public void selByValue(WebElement ele, String value) {
		Select sel = new Select(ele);
		ele.click();
		sel.selectByValue(value);
	}

	public void selByIndex(WebElement ele, int index) {
		Select sel = new Select(ele);
		ele.click();
		sel.selectByIndex(index);
	}

	public void selByText(WebElement ele, String text) {
		Select sel = new Select(ele);
		sel.selectByVisibleText(text);
	}

	public void setValueAttribute(WebElement ele, String value) {
		setAttribute(ele, "value", value);
	}

	public void setAttribute(WebElement element, String attName, String attValue) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attName, attValue);
	}

	public void clickThroughAction(WebElement ele) {
		// waitForElementToBeClickable(ele);
		Actions actions = new Actions(driver);
		actions.click(ele).click().build().perform();
		// actions.moveToElement(ele).click().build().perform();
	}

	public void doubleClickThroughAction(WebElement ele) {
		Actions actions = new Actions(driver);
		actions.doubleClick(ele).build().perform();

	}

//	public void typeThroughAction(WebElement ele,String text) {
//		//waitForElementToBeClickable(ele);
//		Actions actions = new Actions(driver);
//		actions.sendKeys(ele, text);
//		//actions.moveToElement(ele).click().build().perform();
//	}
	
	
	
}
