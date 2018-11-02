package Tests.Suite1;

import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import BaseClasses.BaseClass;

public class LoginTest extends BaseClass{
	
	    
	    @Rule public TestName testName = new TestName();
	    @Before
		public void BeforeTest() throws IOException
		{
	    	initialize();
			initReports(testName.getMethodName());
	    	if(isSkip("LoginTest"))
			{	
				infoLogs("This test needs to be skipped");
				Assume.assumeTrue(false);
			}
			
		}
		
		@Test
		public void Login_Test() throws InterruptedException
		{
			dr.get(CONFIG.getProperty("testsitename"));

			getObject("myaccountlink_xpath").click();
			getObject("loginlink_xpath").click();
			getObject("username_name").sendKeys("shruti.japan.vejapan1@gmail.com");
			getObject("pwd_name").sendKeys("Sel@1234");
		getObject("loginbutton_xpath").click();
		if(isElementPresent("loginerror_xpath"))
		{
			errLogs(getObject("loginerror_xpath").getText());
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

