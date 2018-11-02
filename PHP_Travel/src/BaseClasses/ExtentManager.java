package BaseClasses;

import java.io.File;

import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class ExtentManager {
	
	private static ExtentReports extent;
	public static String screenshotFolderPath;
	
	public static ExtentReports getInstance(String reportPath)
	{
		if(extent==null)
		{
			String filename="Report.html";
			Date d = new Date();
			String foldername = d.toString().replace(":", "_").replace(" ","_");
			System.out.println("ReportPath:"+ reportPath);
			
			new File(reportPath+foldername+"//screenshots").mkdirs();
			reportPath = reportPath+foldername+"//";
			screenshotFolderPath = reportPath+"screenshots//";
			createInstance(reportPath+filename);
			
		}
		return extent;
	}
	
	public static ExtentReports createInstance(String fileName)
	{
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setEncoding("UTF-8");
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setDocumentTitle("Reports");
		htmlReporter.config().setReportName("PHP Travels - Report");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		
		return extent;
		
	}

}
