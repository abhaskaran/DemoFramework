package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	public XSSFWorkbook wb = null;
	public XSSFSheet s = null;

	public ExcelUtility(String FilePath, String FileName) {
		try {

			String filePath = FilePath;
		//	System.out.println(filePath);

			String fileName = FileName;
		//	System.out.println(fileName);

			String fullPath = filePath + "\\" + fileName;
		//	System.out.println("Full Path " + fullPath);

			File f = new File(fullPath);
			try {
				FileInputStream fis = new FileInputStream(f);
				wb = new XSSFWorkbook(fis);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public String getCellData(int rowCount, int Col, String sheetName) {

		System.out.println("rowcount " + rowCount);	
		System.out.println("No of coulmns " + Col);
		System.out.println("Sheetname " + sheetName);

		if (wb != null) {
			s = wb.getSheet(sheetName);
		}
		XSSFRow row = s.getRow(rowCount);
		
		if(row == null) {
			System.out.println("Row is empty/Null");
			return "";
		}
		
		XSSFCell cell = row.getCell(Col);
		
		if(cell == null) {
			System.out.println("Cell is empty/Null");
			return "";
		}
		
		String data = cell.getStringCellValue();
		System.out.println("getCellData " + data);
		return data;
	}

	public int getRowCount(String sheetName) {

		s = wb.getSheet(sheetName);
		int rowCount = s.getLastRowNum() - s.getFirstRowNum();
		rowCount = rowCount + 1;
		return rowCount;
	}

}
