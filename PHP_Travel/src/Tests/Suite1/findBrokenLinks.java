package Tests.Suite1;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import BaseClasses.BaseClass;

public class findBrokenLinks extends BaseClass {
	@Rule public TestName testName = new TestName();
	
	@Before
	public void BeforeTest() throws IOException
	{
		initialize();
		initReports(testName.getMethodName());
		if(isSkip("findBrokenLinks"))
		{	
			infoLogs("This test needs to be skipped");
			Assume.assumeTrue(false);
		}
		
		
	}
	
	@Test
	public void broken_links() throws IOException
	{
		dr.get(CONFIG.getProperty("testsitename"));
		getObject("hotelslink_xpath").click();
		Boolean b = isLinkBroken();
		if(b)
		{
			infoLogs("There are some broken links on this webpage");
		}
		else	
		{
			infoLogs("There are no broken links on this webpage");
		}
		
	}
	
	 @After
		public void after()
		{
			System.out.println("Executing After");
			generatereport();
		
		}

}
