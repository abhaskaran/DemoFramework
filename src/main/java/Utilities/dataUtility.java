package Utilities;

import java.io.IOException;
import java.util.Hashtable;

import DemoFramework.basePage;

public class dataUtility {
	
	public static Object[][] getData(ExcelUtility ex, String testCase) throws IOException {
		
		//Get the excel sheetname from properties file
		String sheetName = PropertyFile.getObject().getProperty("sheetName");

		//Find the row where first testcase name exists in excel and then find the number of rows of input for the respective test case
		int testCaseRN = 0;
		while (!ex.getCellData(testCaseRN, 0, sheetName).equals(testCase)) {
		 testCaseRN++; // increment the row number till a match is found
		// System.out.println("testCaeRN = "+testCaseRN);
		 }
		 System.out.println("Row Number containing the test case name = " + testCaseRN);

		// find total number of rows and iterate through total rows from the first row
		int colHeaderRN = testCaseRN + 1; // find the row number of the row which has the column header names.
		int dataStartRN = testCaseRN + 2; // find the starting row number of the row which has the input data to the
											// test case.
		int rows = 0;
		// increment the rows till a blank row is encountered inorder to ensure the end
		// of data to the respective test case

		while (!ex.getCellData(dataStartRN + rows, 0, sheetName).equals("")) {
			rows++;
		}
		System.out.println("Total rows of input data for " + testCase + "=" + rows);

		// Find total number of columns for each test case input

		int cols = 0;

		while (!ex.getCellData(colHeaderRN, cols, sheetName).equals("")) {
			cols++;
		}
		System.out.println("Total number of columns for each test =" + cols);

		Object[][] data = new Object[rows][1];
		Hashtable<String, String> table = null;
		// get or read data from each cell
		int dataRow = 0;
		for (int i = dataStartRN; i < dataStartRN + rows; i++) {
			table = new Hashtable<String, String>();
			for (int j = 0; j < cols; j++) {
				 String key = ex.getCellData(colHeaderRN, j, sheetName);
				 String value = ex.getCellData(i, j, sheetName);
				 table.put(key, value);
			}

			data[dataRow][0] = table;
			dataRow++;
		}
		return data;
	}


}
