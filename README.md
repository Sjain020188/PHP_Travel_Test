# PHP_Travel_Test
Practice Data driven Framework in Selenium Webdriver
This Test contains following Test cases
1. Check all the URLs on Hotel page
2. Check customer registration with Test data in Suite1.xls
3. Check customer login with Test data in Suite1.xls
4. Check Hotel booking with Test data in Suite1.xls

Run these test cases with a Suite Runner. Run the Test case only if Test Mode is Y in Suite1.xls. Create an HTML report for these Tests and save them in Reports folder of this Project

This Project has following Packages

BaseClasses
- BaseClass.java - 
- ExtentManager.java - This is Base class used to create HTML Reports

config

- config.properties - It contains all selector. The name should contain _xpath, _name, _id,_css if the selector is xpath, name, id OR cssSelector respectively
- or.properties - Object Repository
- Suite1.xlsx - Test data for Test scenarios

datatable
- Xls_Reader.java - This file contains class for reading data from excel file

Tests.Suite1
- findBrokenLinks.java   -  Check if there are any broken links on Hotel page
- HotelBooking_Test.java   - Book a Hotel by reading data from excel file
- LoginTest.java     -  Login to website using test data from excel sheet
- Register_Test.java   - Register as new user using test data from excel sheet
- MySuiteRunner.java    - Suite runner for running above test

