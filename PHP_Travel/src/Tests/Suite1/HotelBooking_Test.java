package Tests.Suite1;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import BaseClasses.BaseClass;
@RunWith(Parameterized.class)
public class HotelBooking_Test extends BaseClass{
       public String hotelname;
       public String checkindate;
       public String checkoutdate;
       public String fname;
       public String lname;
       public String email;
       public String mobile;
       public String address;
       public String country;
    
	public HotelBooking_Test(String hotelname, String checkindate, String checkoutdate, String fname, String lname, String email, String mobile, String address, String country)
	{
		System.out.println("constructor");
		 this.hotelname = hotelname;
	       this.checkindate = checkindate;
	      this.checkoutdate = checkoutdate;
	       this.fname = fname;
	       this.lname = lname;
	       this.email = email;
	       this.mobile = mobile;
	      this.address = address;
	       this.country=country;
	}
	@Rule public TestName testName = new TestName();
	@Before
			public void BeforeTest() throws IOException
			{
		initialize();
		initReports(testName.getMethodName());

		if(isSkip("HotelBooking_Test"))
		{	
			infoLogs("This test needs to be skipped");
			Assume.assumeTrue(false);
		}
		
	
					
			}
	  @Parameters
		public static Collection<Object[]> Data()
		{  
		
			Object data[][] = getData("BookHotel");
			return Arrays.asList(data);
		}
		
	  @Test
	  public void HotelBook_test() throws InterruptedException, ParseException
	  {
		  dr.get(CONFIG.getProperty("testsitename"));
		  getObject("hotelsearch_xpath").click();
		  getObject("hotelsearchinput_xpath").sendKeys(hotelname);
		
		  getObject("hotelsearchdropdwn_xpath").click();
		  getObject("hotelsearchfrom_xpath").click();
		  select_checkin_date(checkindate);
	
		  select_checkout_date(checkoutdate);
		  getObject("hotelsearchmembers_xpath").click();
		  getObject("memberincrease_xpath").click();
		  getObject("searchbtn_xpath").click();
		  if(isElementPresent("details_xpath"))
		  {
		  //scrolling down page to have element in view
		  WebElement webElement = getObject("availaiblerooms_xpath");
		  ((JavascriptExecutor)dr).executeScript("arguments[0].scrollIntoView();", webElement);
		  
		  getObject("chooseroomcheckbox_xpath").click();
		  getObject("booknow_xpath").click();	
	    		
		  getObject("fname_name").sendKeys(fname);
			getObject("lname_name").sendKeys(lname);
			
			getObject("email_name").sendKeys(email);
			getObject("confirmemail_name").sendKeys(email);
			getObject("mobile_name").sendKeys(mobile);
			getObject("address_name").sendKeys(address);
			getObject("selectcountry_xpath").click();
			
			//Selecting value from drop down
			Select drpCountry=new Select(getObject("country_name"));
			drpCountry.selectByVisibleText(country);
			
			getObject("confirmbooking_xpath").click();
			if(isElementPresent("bookingcomplete_xpath"))
			{
				if(getObject("bookingcomplete_xpath").getText().equalsIgnoreCase("Invoice"))
				{
					infoLogs("Booking completed sucessfully");
					
				}
				else
				{
					errLogs("Some error in Booking");
				}
			}
		  }
		  else if(isElementPresent("noresult_xpath"))
		  {
			  errLogs("No hotel found");
			  Assert.assertTrue(false);
		  }
		  else
		  {
			  errLogs("Some othe issue");
			  Assert.assertTrue(false);
		  }
	    	}
	  @After
		public void after()
		{
			System.out.println("Executing After");
			generatereport();
		
		}
	    	
	  }
