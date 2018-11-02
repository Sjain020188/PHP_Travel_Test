package BaseClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import datatable.Xls_Reader;


public class BaseClass {
	
	public static Properties CONFIG = null;
	public static Properties OR = null;
	public static WebDriver dr = null;
	public static boolean isLoggedIn = false;
	public ExtentReports rep;
	public static ExtentTest scenariorep;
	
	public void initialize() throws IOException
	{
		if(dr==null)
		{
			CONFIG = new Properties();
			FileInputStream fn = new FileInputStream(System.getProperty("user.dir")+"\\src\\config\\config.properties");
			CONFIG.load(fn);
			
			OR = new Properties();
			FileInputStream fn1 = new FileInputStream(System.getProperty("user.dir")+"\\src\\config\\or.properties");
			OR.load(fn1);
			
			if(CONFIG.getProperty("browser").equals("firefox"))
			{
				System.setProperty("webdriver.gecko.driver", "C:\\Users\\SJain\\Desktop\\Selenium\\geckodriver.exe");
				dr = new FirefoxDriver();
			}
			else if (CONFIG.getProperty("browser").equals("chrome"))
			{
				System.setProperty("webdriver.chrome.driver", "C:\\Users\\SJain\\Desktop\\Selenium\\chromedriver.exe");
				dr = new ChromeDriver();
				dr.manage().window().maximize();
			}
			
			dr.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
					
		}
		
	}
		public static WebElement getObject(String xpathval)
		{
			WebElement e=null;
			try
			{
			if(xpathval.endsWith("xpath"))
			{
				
		       e = dr.findElement(By.xpath(OR.getProperty(xpathval)));
	
		       WebDriverWait wait = new WebDriverWait(dr,10);
		   wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(OR.getProperty(xpathval))));
			
			}
			else if(xpathval.endsWith("css"))
			{
				e = dr.findElement(By.cssSelector(OR.getProperty(xpathval)));
				WebDriverWait wait = new WebDriverWait(dr,10);
				   wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(OR.getProperty(xpathval))));
			}
			else if(xpathval.endsWith("id"))
			{
				e = dr.findElement(By.id(OR.getProperty(xpathval)));
				WebDriverWait wait = new WebDriverWait(dr,10);
				   wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(OR.getProperty(xpathval))));
			}
			else if(xpathval.endsWith("name"))
			{
				e = dr.findElement(By.name(OR.getProperty(xpathval)));
				WebDriverWait wait = new WebDriverWait(dr,10);
				   wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name(OR.getProperty(xpathval))));
			}
			
			}
			catch(Throwable t)
			{
				System.out.println("Not able to find" + xpathval);
				return null;
			}
			return(e);
			
			
		}
		
		
		public Boolean isElementPresent(String ObjectKey)
		{
			List<WebElement> e = null;
			if(ObjectKey.endsWith("xpath"))
			e=dr.findElements(By.xpath(OR.getProperty(ObjectKey)));
			else if(ObjectKey.endsWith("id"))
				e=dr.findElements(By.id(OR.getProperty(ObjectKey)));
			else if(ObjectKey.endsWith("name"))
				e=dr.findElements(By.name(OR.getProperty(ObjectKey)));
			else if(ObjectKey.endsWith("css"))
				e=dr.findElements(By.cssSelector(OR.getProperty(ObjectKey)));
			
			if(e.size()==0)
				return false;
			else
				return true;
		}
		
		
		public static Boolean isLinkBroken() throws IOException
		{
			List<WebElement> linklist = dr.findElements(By.tagName("a"));
			URL url = null;
			String response = null;
			Boolean temp_status = false;
			for(WebElement element: linklist)
			{
				
				String link_href = element.getAttribute("href");
				if ((link_href != null) && !link_href.startsWith("javascript")) {
				url = new URL(link_href);
			
				
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				try
				{
					connection.connect();
					response = connection.getResponseMessage();
					connection.disconnect();
					
				}
				catch (Exception xp)
				{
					response = xp.getMessage();
					
				}
				if(!response.equalsIgnoreCase("OK"))
				{
					infoLogs("URL: "+ link_href + " IS BROKEN returns "+ response);
					temp_status = true;
				}
				
				
				}
				
			}
			return temp_status;
		}
		
		public static Object[][] getData(String testName)
		{
			Xls_Reader datatable = new Xls_Reader(System.getProperty("user.dir")+"\\src\\config\\Suite1.xlsx");
			int rows= datatable.getRowCount(testName)-1;
			if(rows<=0)
			{
				Object[][] testData=new Object[1][0];
			return testData;
			}
			rows=datatable.getRowCount(testName);
			int cols = datatable.getColumnCount(testName);
			
		    Object data[][] = new Object[rows-1][cols];
			for(int rowNum=2;rowNum<=rows;rowNum++)
			{
				for(int colNum=0;colNum<cols;colNum++)
				{
					data[rowNum-2][colNum]=datatable.getCellData(testName, colNum, rowNum);
					
				}
			}
			return data;
		}
		
		public static boolean isSkip(String testname)
		{
			Xls_Reader datatable = new Xls_Reader(System.getProperty("user.dir")+"\\src\\config\\Suite1.xlsx");
			for(int i=2;i<=datatable.getRowCount("TestName");i++)
			{
				if(testname.equals(datatable.getCellData("TestName", "TCID", i)))
				{
					if(datatable.getCellData("TestName", "Runmode", i).equals("N"))
						return true;
					else
						return false;
				}
			}
			return false;
		}
		
		
		public static void Logout()
		{
			getObject("logoutdropdown_xpath").click();
			getObject("logoutoutlink_xpath").click();
			if(getObject("myaccountlink_xpath").getText().equalsIgnoreCase("My Account"))
			{
				infoLogs("Logged out successfully");
			}
			else
			{
				errLogs("Not able to logged out");
			}
		}
		
		public void initReports(String scenarioName)
		{
			rep=ExtentManager.getInstance(CONFIG.getProperty("reportpath"));
			scenariorep = rep.createTest(scenarioName);
			scenariorep.log(Status.INFO, "Starting"+scenarioName);
		}

		public static void infoLogs(String msg)
		{
			scenariorep.log(Status.INFO, msg);
		}
		
		public static void errLogs(String msg)
		{
			scenariorep.log(Status.ERROR, msg);
			takeScreenshot();
		}
		
		public void generatereport()
		{
			if(rep!=null)
			rep.flush();
		}
		
		public static void takeScreenshot()
		{
			Date d = new Date();
			String screenshotFileName = d.toString().replace(":", "_").replaceAll(" ", "_");
			File srcFile=((TakesScreenshot)dr).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(srcFile,new File(ExtentManager.screenshotFolderPath+screenshotFileName));
				scenariorep.log(Status.FAIL, "Screenshot ->"+ scenariorep.addScreenCaptureFromPath(ExtentManager.screenshotFolderPath+screenshotFileName));
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		
		public static void select_checkin_date(String date) throws ParseException
		{
			  String dep_date = date;
			  SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy");
			  Date d1 = d.parse(dep_date);
			  String month = new SimpleDateFormat("MMMM").format(d1);
		    	int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(d1));
		    	String day = new SimpleDateFormat("d").format(d1);
		    	System.out.println(year + "--" + month + "--" + day);
		    	
		    	
		    	
		    		while(true)
		    		{
		    			String dateheader = dr.findElement(By.xpath("/html/body/div[8]/div[1]/table/thead/tr[1]/th[2]")).getText();
			    		System.out.println(dateheader);
			    		int header_year = Integer.parseInt(dateheader.split("\\s+")[1]);
			    		String header_month = dateheader.split("\\s+")[0];
			    		
			    		System.out.println("Header Year"+header_year);
			    		System.out.println("Header Month"+header_month);
		    		if(year>header_year)
		    		{
		    			System.out.println("Year doesn't match yet");
		    			dr.findElement(By.xpath("/html/body/div[8]/div[1]/table/thead/tr[1]/th[3]")).click();
		    		}
		    		else if(year == header_year)
		    		{
		    			if(month.equalsIgnoreCase(header_month))
		    			{
		    				dr.findElement(By.xpath("/html/body/div[8]/div[1]/table/tbody/tr/td[text()='"+day+"']")).click();
		    				break;
		    			}
		    			else
		    			{
		    				System.out.println("Month doesn't match yet");
			    			dr.findElement(By.xpath("/html/body/div[8]/div[1]/table/thead/tr[1]/th[3]")).click();
		    			}
		    			
		    		}
		    		else
		    		{
		    			System.out.println("You cannot book for past date");
		    			break;
		    		}
		    		}
		}
		
		public static void select_checkout_date(String date) throws ParseException
		{
			  String dep_date = date;
			  SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy");
			  Date d1 = d.parse(dep_date);
			  String month = new SimpleDateFormat("MMMM").format(d1);
		    	int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(d1));
		    	String day = new SimpleDateFormat("d").format(d1);
		    	System.out.println(year + "--" + month + "--" + day);
		    	
		    	
		    	
		    		while(true)
		    		{
		    			String dateheader = dr.findElement(By.xpath("/html/body/div[9]/div[1]/table/thead/tr[1]/th[2]")).getText();
			    		System.out.println(dateheader);
			    		int header_year = Integer.parseInt(dateheader.split("\\s+")[1]);
			    		String header_month = dateheader.split("\\s+")[0];
			    		
			    		System.out.println("Header Year"+header_year);
			    		System.out.println("Header Month"+header_month);
		    		if(year>header_year)
		    		{
		    			System.out.println("Year doesn't match yet");
		    			dr.findElement(By.xpath("/html/body/div[9]/div[1]/table/thead/tr[1]/th[3]")).click();
		    		}
		    		else if(year == header_year)
		    		{
		    			if(month.equalsIgnoreCase(header_month))
		    			{
		    				System.out.println("Month and year matched");
		    				dr.findElement(By.xpath("/html/body/div[9]/div[1]/table/tbody/tr/td[text()='"+day+"']")).click();
		    				break;
		    			}
		    			else
		    			{
		    				System.out.println("Month doesn't match yet");
			    			dr.findElement(By.xpath("/html/body/div[9]/div[1]/table/thead/tr[1]/th[3]")).click();
		    			}
		    			
		    		}
		    		else
		    		{
		    			System.out.println("You cannot book for past date");
		    			break;
		    		}
		    		}
		}
		
		
		
	
	

}
