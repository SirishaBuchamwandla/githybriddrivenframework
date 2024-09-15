package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class ExcelFileUtil {
	XSSFWorkbook wb;


	//create a constructor for reading excel path
	public ExcelFileUtil(String ExcelPath) throws Throwable
	{

		FileInputStream fi=new FileInputStream(ExcelPath);
		wb= new XSSFWorkbook(fi);
	}
//method for counting rows in a sheet
	public int rowcount(String SheetName)
	{
		XSSFSheet sh= wb.getSheet(SheetName);
		int rc =sh.getLastRowNum();
		return rc;	
	}
	//method for reading data from the cell
	public String getCelldata(String SheetName,int row,int column)
	{
		String data="";
		if(wb.getSheet(SheetName).getRow(row).getCell(column).getCellType()==CellType.NUMERIC)
		{
			int celltype=(int) wb.getSheet(SheetName).getRow(row).getCell(column).getNumericCellValue();
			data=String.valueOf(celltype);
		}
		else 
		{
			data =wb.getSheet(SheetName).getRow(row).getCell(column).getStringCellValue();
		}
		return data;

	}


	//method for writing cell 
	public  void setCelldata(String SheetName,int row,int column,String status,String Outputfile) throws Throwable

	{
		XSSFSheet sh= wb.getSheet(SheetName);
		XSSFRow row1 = sh.getRow(row);
		XSSFCell t=row1.createCell(column);
		t.setCellValue(status);
		if(status.equalsIgnoreCase("Pass"))
		{
			XSSFCellStyle style= wb.createCellStyle();
			XSSFFont font=wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			style.setFont(font);
			wb.getSheet(SheetName).getRow(row).getCell(column).setCellStyle(style);
		}
		else if (status.equalsIgnoreCase("Fail")) 
		{
			XSSFCellStyle style= wb.createCellStyle();
			XSSFFont font=wb.createFont();
			font.setColor(IndexedColors.RED1.getIndex());
			font.setBold(true);
			style.setFont(font);
			wb.getSheet(SheetName).getRow(row).getCell(column).setCellStyle(style);	
		}
		else if (status.equalsIgnoreCase("Blocked")) 
		{
			XSSFCellStyle style= wb.createCellStyle();
			XSSFFont font=wb.createFont();
			font.setColor(IndexedColors.BLUE1.getIndex());
			font.setBold(true);
			style.setFont(font);
			wb.getSheet(SheetName).getRow(row).getCell(column).setCellStyle(style);	
		}

		FileOutputStream fo=new FileOutputStream(Outputfile);
		wb.write(fo); 
		//wb.close();
		fo.flush();
		fo.close();

	}	
//	public static void main(String[] args) throws Throwable 
//	{
//		ExcelFileUtil xl=new ExcelFileUtil("D:\\C DRIVE\\Documents\\Book1.xlsx");
//		int r= xl.rowcount("EmpData");
//		System.out.println(r);
//		for(int i = 1;i<=r;i++)
//		{
//			String firstnAME = xl.getCelldata("EmpData", i, 0);
//			String mnAME = xl.getCelldata("EmpData", i, 1);
//			String lnAME = xl.getCelldata("EmpData", i, 2);
//			String eid = xl.getCelldata("EmpData", i,3);
//			System.out.println(firstnAME+"  "+mnAME+" "+lnAME+" "+eid);
//			//xl.setCelldata("EmpData", i, 4,"Pass","D:\\C DRIVE\\Documents\\Results.xlsx" );
//			xl.setCelldata("EmpData", i, 4,"Fail","D:\\C DRIVE\\Documents\\Results.xlsx" );
//			//xl.setCelldata("EmpData", i, 4,"Blocked","D:\\C DRIVE\\Documents\\Results.xlsx" );
//		}
//	}
}










