package driverFactory;



import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.Functionlibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
	public static WebDriver driver;
	String inputpath="./FileInput/Controller.xlsx";
	String outputpath = "./FileOutput/Hybridresults.xlsx";
	String TCSheet ="MasterTestCases";
	String TCModule= "";
	ExtentReports reports;
	ExtentTest logger;

	public void startTest() throws Throwable
	{
		String ModuleStatus = "";
		String ModuleNew = "";
		//create an object for excelfileutils
		ExcelFileUtil xl= new ExcelFileUtil(inputpath);
		//iterate all rows from TCSheet

		for(int i=1;i<=xl.rowcount(TCSheet);i++)
		{
			if((xl.getCelldata(TCSheet, i, 2).equalsIgnoreCase("Y")))
			{
				//store corresponding sheet into TCModule
				TCModule= xl.getCelldata(TCSheet, i, 1);
				reports=new ExtentReports("./Target/ExtentReports/"+TCModule+Functionlibrary.generatedata()+".html");
				logger=reports.startTest(TCModule);
				logger.assignAuthor("Sirisha");
				for(int j=1;j<=xl.rowcount(TCModule);j++)
				{
					String Description =xl.getCelldata(TCModule, j, 0);
					String ObjType =xl.getCelldata(TCModule, j, 1);
					String LType =xl.getCelldata(TCModule, j, 2);
					String LValue =xl.getCelldata(TCModule, j, 3);
					String TestData =xl.getCelldata(TCModule, j, 4);
					try {

						if(ObjType.equalsIgnoreCase("startBrowser"))
						{
							driver=Functionlibrary.startBrowser();
							logger.log(LogStatus.INFO, Description);
						}

						if(ObjType.equalsIgnoreCase("openUrl"))
						{
							Functionlibrary.openUrl();
							logger.log(LogStatus.INFO, Description);
						}

						if(ObjType.equalsIgnoreCase("waitforElement"))
						{
							Functionlibrary.waitForElement(LType, LValue, TestData);
							logger.log(LogStatus.INFO, Description);

						}

						if(ObjType.equalsIgnoreCase("typeAction"))
						{
							Functionlibrary.typeAction(LType, LValue, TestData);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjType.equalsIgnoreCase("validateTitle"))
						{
							Functionlibrary.validateTitle(TestData);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjType.equalsIgnoreCase("clickAction"))
						{

							Functionlibrary.clickAction(LType, LValue);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjType.equalsIgnoreCase("dropDownAction"))
						{
							Functionlibrary.dropDownaction(LType, LValue, TestData);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjType.equalsIgnoreCase("capStock"))
						{
							Functionlibrary.capStock(LType, LValue);
							logger.log(LogStatus.INFO, Description);
						}	
						if(ObjType.equalsIgnoreCase("stockTable"))
						{
							Functionlibrary.stockTable();
							logger.log(LogStatus.INFO, Description);
						}
						
						if(ObjType.equalsIgnoreCase("capSupplier"))
						{
							Functionlibrary.capSupplier(LType, LValue);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjType.equalsIgnoreCase("supplierTable"))
						{
							Functionlibrary.supplierTable();
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjType.equalsIgnoreCase("capCustomernum"))
						{
							Functionlibrary.capCustomernum(LType, LValue);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjType.equalsIgnoreCase("customerTable"))
						{
							Functionlibrary.customerTable();
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjType.equalsIgnoreCase("closeBrowser"))
						{
							Functionlibrary.closeBrowser();
						}
						
				
						xl.setCelldata(TCModule, j, 5, "Pass", outputpath);
						logger.log(LogStatus.PASS, Description);

						ModuleStatus="True";

					}catch (Exception e) {
						System.out.println(e.getMessage());
						xl.setCelldata(TCModule, j, 5, "Fail", outputpath);
						logger.log(LogStatus.FAIL, Description);
						File ts =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						FileUtils.copyFile(ts, new File("./Target/Screenshot/"+TCModule+Functionlibrary.generatedata()+".jpg"));
						ModuleNew="False";
					}
				}
				reports.endTest(logger);
				reports.flush();
				if(ModuleStatus.equalsIgnoreCase("True"))
				{
					xl.setCelldata(TCSheet, i, 3, "Pass", outputpath);
				}
				if(ModuleNew.equalsIgnoreCase("False"))
				{
					xl.setCelldata(TCSheet, i, 3, "Fail", outputpath);
				}

			}

			else {
				xl.setCelldata(TCSheet, i, 3, "Blocked", outputpath);
			}
		}


	}

}
