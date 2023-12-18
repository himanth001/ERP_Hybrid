package driverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
	
	public WebDriver driver; //driver is an instant object
	String inputpath = "./FileInput/Controller.xlsx";
	String outputpath = "./FileOutput/HybridResults.xlsx";
	ExtentReports report;
	ExtentTest logger;
	String TestCases = "MasterTestCases";
	public void startTest() throws Throwable
	{
		String Module_Status = "";
		//create object for Excel file Util class
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		//iterate all rows in testcases sheet
		for(int i=1;i<=xl.rowCount(TestCases);i++)
		{
			//read cell
			String Execution_Status = xl.getCellData(TestCases, i, 2);
			if(Execution_Status.equalsIgnoreCase("Y"))
			{
				//store all modules into variable
				String TCModule = xl.getCellData(TestCases, i, 1);
				report = new ExtentReports("./target/Reports/"+TCModule+FunctionLibrary.generateDate()+".html");
				logger = report.startTest(TCModule);
				//iterate all rows in TCModule
				for(int j=1;j<=xl.rowCount(TCModule);j++)
				{
					String Description = xl.getCellData(TCModule, j, 0);
					String Object_type = xl.getCellData(TCModule, j, 1);
					String Locator_Type = xl.getCellData(TCModule, j, 2);
					String Locator_Value = xl.getCellData(TCModule, j, 3);
					String Test_Data = xl.getCellData(TCModule, j, 4);
					try {
						if(Object_type.equalsIgnoreCase("startBrowser"))
						{
							driver = FunctionLibrary.startBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("openUrl"))
						{
							FunctionLibrary.openUrl(driver);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("waitForElement"))
						{
							FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("typeAction"))
						{
							FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("clickAction"))
						{
							FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("validateTitle"))
						{
							FunctionLibrary.validateTitle(driver, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("closeBrowser"))
						{
							FunctionLibrary.closeBrowser(driver);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("mouseClick"))
						{
							FunctionLibrary.mouseClick(driver);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("categoryTable"))
						{
							FunctionLibrary.categoryTable(driver, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("dropDownAction"))
						{
							FunctionLibrary.dropDownAction(driver, Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("capturestock"))
						{
							FunctionLibrary.capturestock(driver, Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("stockTable"))
						{
							FunctionLibrary.stockTable(driver);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("captureSupplier"))
						{
							FunctionLibrary.captureSupplier(driver, Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("supplierTable"))
						{
							FunctionLibrary.supplierTable(driver);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("captureCustomer"))
						{
							FunctionLibrary.captureCustomer(driver, Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("customerTable"))
						{
							FunctionLibrary.customerTable(driver);
							logger.log(LogStatus.INFO, Description);
						}
						
							
						//write as pass into TCModule
						xl.setcellData(TCModule, j, 5, "Pass", outputpath);
						logger.log(LogStatus.PASS, Description);
						Module_Status = "True";
					}catch(Exception e)
					{
						//write as fail into TCModule
						xl.setcellData(TCModule, j, 5, "Fail", outputpath);
						logger.log(LogStatus.FAIL, Description);
						Module_Status = "False";
						System.out.println(e.getMessage());
					}
					if(Module_Status.equalsIgnoreCase("True"))
					{
						//write as pass into Testcases
						xl.setcellData(TestCases, i, 3, "Pass", outputpath);
					}
					if(Module_Status.equalsIgnoreCase("False"))
					{
						//write as fail into Testcases
						xl.setcellData(TestCases, i, 3, "Fail", outputpath);
						
					}
					report.endTest(logger);
					report.flush();
				}
			}
			else
			{
				//write as blocked into testcses sheet for flag N
			    xl.setcellData(TestCases, i, 3, "Blocked", outputpath);
			}
		}
	}
	

}
