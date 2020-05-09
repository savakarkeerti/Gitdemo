package testcase;

import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.bson.Document;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

import pom.LoginPageProject1;
import utilities.Baseclass;
import utilities.DbUtility;

public class ProjectOfPunchinTest extends Baseclass {

	
	@Parameters({ "testCaseName", "instance#", "browser" })
	@Test
	public void testcase1(String testCaseName, String instance, String browser) {

		logger = LogManager.getLogger(this);
		logger.debug("Inside TestMethod ");
		logger.debug("testCase Param : " + testCaseName);
		logger.debug("Instance param " + instance);
		DbUtility.initDataSheet();
		DbUtility.findScenarioRowNo(testCaseName, instance);

		String userName, encryptedPassword, isNegative;

		openURL(DbUtility.fetchProperty("url"), browser);
		logger.debug("Opned URL...");
		LoginPageProject1 log = new LoginPageProject1(driver);

		MongoCollection auth = DbUtility.getCollectionValue(DbUtility.fetchProperty("auth"));
		BasicDBObject obj = new BasicDBObject();
		obj.put("userId", "keerti savakar");
		Document doc = (Document) auth.find(obj).first();

		userName = doc.getString("userId");
		encryptedPassword = doc.getString("password");

		// DECODING PASSWORD

		Base64.Decoder decoder = Base64.getMimeDecoder();

		String password = new String(decoder.decode(encryptedPassword));

		System.out.println(userName + "  " + password);

		// Login to application

		log.enterUsn(userName);
		log.enterPwd(password);
		log.clickSubmit();

		driver.close();
	}

}
