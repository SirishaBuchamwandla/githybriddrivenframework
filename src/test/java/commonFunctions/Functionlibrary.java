package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Driver;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.Assertion;

public class Functionlibrary 
{

	public static Properties conpro;
	public static WebDriver driver;
	public static WebDriver startBrowser() throws Throwable
	{
		conpro = new Properties();
		//load property file
		conpro.load(new FileInputStream("./Propertyfile/Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("Chrome"))
		{
			driver= new ChromeDriver();
			driver.manage().window().maximize();

		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("Firefox"))
		{
			driver= new FirefoxDriver();
			driver.manage().window().maximize();
		}
		else
		{
			Reporter.log("DRIVER NOT MATCHING",true);
		}
		return driver;
	}



	public static void openUrl()
	{
		driver.get(conpro.getProperty("Url"));

	}
	//method for wait for the element
	public static void waitForElement(String Locatortype,String LocatorValue,String TestData)
	{
		WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(TestData)));
		if(Locatortype.equalsIgnoreCase("name"))
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
		}
		if(Locatortype.equalsIgnoreCase("id"))
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		}
		if(Locatortype.equalsIgnoreCase("xpath"))
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		}


	}
	//method for entering text into textbox
	public static void typeAction(String Locatortype,String LocatorValue,String TestData)
	{
		if(Locatortype.equalsIgnoreCase("name"))	
		{
			driver.findElement(By.name(LocatorValue)).clear();
			driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
		}
		if(Locatortype.equalsIgnoreCase("id"))	
		{
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
		}
		if(Locatortype.equalsIgnoreCase("xpath"))	
		{
			driver.findElement(By.xpath(LocatorValue)).clear();
			driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
		}
	}
	//method for click Action
	public static void clickAction(String Locatortype,String LocatorValue) throws Throwable
	{
		if(Locatortype.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).click();
		}
		if(Locatortype.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
		}
		if(Locatortype.equalsIgnoreCase("xpath"))
		{
			Thread.sleep(2000);
			driver.findElement(By.xpath(LocatorValue)).click();
		}
	}
	// method for validate any page title
	public static void validateTitle(String Expected_Title)
	{
		String Actual_Title = driver.getTitle();
		try
		{
			Assert.assertEquals(Expected_Title, Actual_Title,"Title is not Matching");

		}
		catch (AssertionError a) 
		{
			System.out.println(a.getMessage());
		}
	}
	public static void closeBrowser()
	{
	
		driver.quit();
	}


	public static String generatedata()
	{
		Date dt= new Date();
		DateFormat ft= new SimpleDateFormat("YYYY_ MM_ dd mm_ hh");
		return ft.format(dt);
	}

	public static void dropDownaction(String Locatortype,String LocatorValue,String TestData) throws Throwable
	{

		if(Locatortype.equalsIgnoreCase("xpath"))
		{
			int element =Integer.parseInt(TestData);
			Thread.sleep(5000);
			Select is =new Select(driver.findElement(By.xpath(LocatorValue)));
			Thread.sleep(5000);
			is.selectByIndex(element);
		}
		if(Locatortype.equalsIgnoreCase("name"))
		{
			int element =Integer.parseInt(TestData);
			Select is =new Select(driver.findElement(By.name(LocatorValue)));
			Thread.sleep(2000);
			is.selectByIndex(element);
		}
		if(Locatortype.equalsIgnoreCase("id"))
		{
			int element =Integer.parseInt(TestData);
			Select is =new Select(driver.findElement(By.id(LocatorValue)));
			Thread.sleep(2000);
			is.selectByIndex(element);
		}

	}
	//mehtod to captire stocknumber into notepad
	public static void capStock(String Locatortype,String LocatorValue) throws Throwable
	{
		String strngnumber = "";	
		if(Locatortype.equalsIgnoreCase("xpath"))
		{
			Thread.sleep(4000);
			strngnumber=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if(Locatortype.equalsIgnoreCase("name"))
		{
			strngnumber=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if(Locatortype.equalsIgnoreCase("id"))
		{
			strngnumber=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		//create notepad under Capturedata folder
		FileWriter fw=new FileWriter("./CaptureData/StockNumber.txt");
		BufferedWriter bw =new BufferedWriter(fw);
		bw.write(strngnumber);
		bw.flush();
		bw.close();

	}
	//method for stockTable
	public static void stockTable() throws Throwable
	{
		//read stocknumber from old notepad
		FileReader fr =new FileReader("./CaptureData/StockNumber.txt");
		BufferedReader br =new BufferedReader(fr);
		String stocknumber =br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("Search-Panel"))).isDisplayed())
		{
			driver.findElement(By.xpath(conpro.getProperty("Search-Panel"))).click();
			driver.findElement(By.xpath(conpro.getProperty("Search-Textbox"))).clear();
			driver.findElement(By.xpath(conpro.getProperty("Search-Textbox"))).sendKeys(stocknumber);
			driver.findElement(By.xpath(conpro.getProperty("Search-Button"))).click();
			Thread.sleep(3000);
			String checksame=driver.findElement(By.xpath("//table[@class='ewTableHeader']/tbody/tr[1]/td[8]/div/span/span")).getText();
			Reporter.log(stocknumber+"   "+checksame,true);
			try {
				Assert.assertEquals(stocknumber, checksame,"Stocknumber not found");
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static void capSupplier(String Locatortype,String LocatorValue) throws Throwable
	{
		String Typedata="";
		if(Locatortype.equalsIgnoreCase("xpath"))
		{
			Typedata=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if(Locatortype.equalsIgnoreCase("name"))
		{
			Typedata=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if(Locatortype.equalsIgnoreCase("id"))
		{
			Typedata=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		//create notepad under Capturedata folder
		FileWriter fw=new FileWriter("./CaptureData/SupplierNumber.txt");
		BufferedWriter bw =new BufferedWriter(fw);
		bw.write(Typedata);
		bw.flush();
		bw.close();
	}
	public static void supplierTable() throws Throwable
	{
		//read stocknumber from old notepad
		FileReader fr =new FileReader("./CaptureData/SupplierNumber.txt");
		BufferedReader br =new BufferedReader(fr);
		String stocknumber =br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("Search-Panel"))).isDisplayed())
		{
			driver.findElement(By.xpath(conpro.getProperty("Search-Panel"))).click();
			driver.findElement(By.xpath(conpro.getProperty("Search-Textbox"))).clear();
			driver.findElement(By.xpath(conpro.getProperty("Search-Textbox"))).sendKeys(stocknumber);
			driver.findElement(By.xpath(conpro.getProperty("Search-Button"))).click();
			Thread.sleep(3000);
			String checksame=driver.findElement(By.xpath("//table[@class='ewTableHeader']/tbody/tr[1]/td[8]/div/span/span")).getText();
			Reporter.log(stocknumber+"   "+checksame,true);
			try {
				Assert.assertEquals(stocknumber, checksame,"Supplier number not found");
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}	



	}
	public static void capCustomernum(String Locatortype,String LocatorValue) throws Throwable
	{
		String CusNum="";
		if(Locatortype.equalsIgnoreCase("xpath"))
		{
			CusNum=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if(Locatortype.equalsIgnoreCase("name"))
		{
			CusNum=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if(Locatortype.equalsIgnoreCase("id"))
		{
			CusNum=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}

		//create notepad under Capturedata folder
		FileWriter fw=new FileWriter("./CaptureData/CustomerNumber.txt");
		BufferedWriter bw =new BufferedWriter(fw);
		bw.write(CusNum);
		bw.flush();
		bw.close();
	}

	public static void customerTable() throws Throwable
	{
		//read stocknumber from old notepad
		FileReader fr =new FileReader("./CaptureData/CustomerNumber.txt");
		BufferedReader br =new BufferedReader(fr);
		String customernumber =br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("Search-Panel"))).isDisplayed())
		{
			driver.findElement(By.xpath(conpro.getProperty("Search-Panel"))).click();
			driver.findElement(By.xpath(conpro.getProperty("Search-Textbox"))).clear();
			driver.findElement(By.xpath(conpro.getProperty("Search-Textbox"))).sendKeys(customernumber);
			driver.findElement(By.xpath(conpro.getProperty("Search-Button"))).click();
			Thread.sleep(3000);
			String checksame=driver.findElement(By.xpath("//table[@class='ewTableHeader']/tbody/tr[1]/td[8]/div/span/span")).getText();
			Reporter.log(customernumber+"   "+checksame,true);
			try {
				Assert.assertEquals(customernumber, checksame,"Customer number not found");
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}	

	}}



