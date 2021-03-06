package utilities;

import java.util.Random;
import java.util.logging.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class Baseclass {

	public static WebDriver driver;
	public static ExtentReports extentReport;
	public static ExtentTest extentLogger;
	public static Logger logger;

	@BeforeSuite
	public void beforeSuite() {
		logger = LogManager.getLogger(this);
		logger = LogManager.getLogger(new Object() {
		}.getClass().getEnclosingMethod().getName());
		java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
		logger.debug("In BeforeSuite Method");
	}

	@AfterTest
	public void afterTest() {
		// driver.close();
		// driver.quit();
	}

	public Baseclass() {

	}

	public static String random() {

		// create a string of all characters
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		// create random string builder
		StringBuilder sb = new StringBuilder();

		// create an object of Random class
		Random random = new Random();

		// specify length of random string
		int length = 7;

		for (int i = 0; i < length; i++) {

			// generate random index number
			int index = random.nextInt(alphabet.length());

			// get character specified by index
			// from the string
			char randomChar = alphabet.charAt(index);

			// append the character to string builder
			sb.append(randomChar);
		}

		String randomString = sb.toString();
		System.out.println("Random String is: " + randomString);

		return randomString;
	}

	public void openURL(String URL, String browser) {

		String chrome = "chrome";
		String fox = "firefox";
		String ie = "IE";
		if (chrome.equalsIgnoreCase(browser)) {
			System.setProperty("webdriver.chrome.driver", "WebDrivers/chromedriver.exe");
			driver = new ChromeDriver();
//			PageFactory.initElements(driver, this);

		} else if (fox.equalsIgnoreCase(browser)) {

			System.setProperty("webdriver.gecko.driver", "WebDrivers/geckodriver.exe");
			driver = new FirefoxDriver();

			PageFactory.initElements(driver, this);
		} else {
			System.setProperty("webdriver.ie.driver", "WebDrivers/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			PageFactory.initElements(driver, this);
		}

		// driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(URL);
	}

	/*
	 * @AfterSuite public void afterSuite() { logger.debug("In AfterSuite Method");
	 * 
	 * try { Utility.wb.close(); } catch (IOException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } // driver.close(); // driver.quit(); }
	 */

}
