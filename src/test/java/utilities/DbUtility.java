package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DbUtility {
	public static File dataFile;
	public static Workbook wb;
	public static Sheet sheet;
	public static int ScenarioRow = -1;
	public static ArrayList<String> headerArray = new ArrayList<String>();

	public static void initDataSheet() {
		dataFile = new File(DbUtility.fetchProperty("dataSheet"));
		try {
			wb = WorkbookFactory.create(dataFile);// read data without file extention
		} catch (EncryptedDocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sheet = wb.getSheetAt(0);
	}

	public static String fetchProperty(String key) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("./Config/config.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Properties property = new Properties();
		try {
			property.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return property.get(key).toString();

	}

	public static int findScenarioRowNo(String testCaseName, String instance) {
		// File dataFile = new File("./Data/SafeTrax_SBTS.xlsx");
		/*
		 * File dataFile = new File(DbUtility.fetchProperty("dataSheet")); Workbook wb =
		 * null; try { wb = WorkbookFactory.create(dataFile); } catch
		 * (EncryptedDocumentException | IOException e2) { // TODO Auto-generated catch
		 * block e2.printStackTrace(); } Sheet sheet = wb.getSheetAt(0);
		 */
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

		boolean isPresent = false;
		int rowNo = -1;
		for (int i = 0; i <= rowCount; i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				try {
					if (row.getCell(0).toString().equalsIgnoreCase(testCaseName)
							&& row.getCell(1).toString().equalsIgnoreCase(instance)) {
						isPresent = true;
						rowNo = row.getRowNum() + 1;
						ScenarioRow = rowNo;
						System.out.println("Scenario Found on Row Number : " + rowNo);
						break;
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
					continue;
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			} else {
				continue;
			}

		}

		if (!isPresent) {
			System.out.println(
					"Scenario Not Found. Please check if the data for the given TestCase & Scenario is present in the data sheet or not");
		}

		try {
			wb.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowNo;
	}

	public static MongoCollection<Document> getCollectionValue(String collectionName) {

		MongoClient client = MongoClients.create(DbUtility.fetchProperty("mongoDBURL"));
		MongoDatabase databse = client.getDatabase(DbUtility.fetchProperty("mongoDBName"));
		MongoCollection<Document> collnValues = databse.getCollection(collectionName);

		return collnValues;

	}
	public static void getHeaderArray() throws IOException{
		File dataFile = new File(DbUtility.fetchProperty("dataSheet"));
		Workbook wb = WorkbookFactory.create(dataFile);
		Sheet sheet = wb.getSheetAt(0);

		Row row = sheet.getRow(0);
		int lastCell = row.getLastCellNum();
		for (int i = 0; i < lastCell; i++) {
			try {
				headerArray.add(row.getCell(i).toString());
			}
			catch(NullPointerException e){
			}
		}
	}
	
	public static int getHeaderIndex(String headerName) {
		return headerArray.indexOf(headerName);
	}
	
	public static String getValueFromDataSheet(String colName) {
		try {
			getHeaderArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int headerIndex =getHeaderIndex(colName);
		String value;
		try {
			value = sheet.getRow(ScenarioRow-1).getCell(headerIndex).toString();
		}
		catch(NullPointerException e){
			value ="";
		}
		return value.trim();
	}
	
}
