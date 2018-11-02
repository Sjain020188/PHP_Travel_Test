package Tests.Suite1;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import BaseClasses.BaseClass;

@RunWith(Parameterized.class)
public class Register_Test extends BaseClass{
	public String fname;
	public String lname;
	public String mobnum;
	public String email;
	public String pwd;
	
	public Register_Test(String fname, String lname, String mobnum, String email,String pwd)
	{
		this.fname =fname;
		this.lname = lname;
		this.mobnum = mobnum;
		this.email=email;
		this.pwd = pwd;
		
	}
	@Rule public TestName testName = new TestName();
	@Before
	public void BeforeTest() throws IOException
	{
		initialize();
		initReports(testName.getMethodName());
		if(isSkip("Register_Test"))
		{	
			infoLogs("This test needs to be skipped");
			Assume.assumeTrue(false);
		}
		
	}
	@Parameters
	public static Collection<Object[]> Data()
	{  
		Object data[][] = getData("Register_Test");
		return Arrays.asList(data);
	}
	
	@Test
	public void RegisterTest() throws InterruptedException
	{
		dr.get(CONFIG.getProperty("testsitename"));
		getObject("myaccountlink_xpath").click();
		getObject("signuplink_xpath").click();
	
		getObject("fname_name").sendKeys(fname);
		getObject("lname_name").sendKeys(lname);
		getObject("mobile_name").sendKeys(mobnum);
		getObject("email_name").sendKeys(email);
		getObject("pwd_name").sendKeys(pwd);
		getObject("conpwd_name").sendKeys(pwd);
		getObject("signupbutton_xpath").click();
		if(isElementPresent("verifyregistertext_xpath"))
		{
			if(getObject("verifyregistertext_xpath").getText().startsWith("Hi"))
			{
			infoLogs("Registered successfully " + email);
			Assert.assertTrue(true);
			Logout();
			}
			else
			{
				errLogs("Not able to register successfully with following email" + email);
				Assert.assertTrue(false);
			}
			
		}
		else
		{
			errLogs("Not able to register successfully with following email" + email);
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
