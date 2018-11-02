# PHP_Travel_Test
Practice Data driven Framework in Selenium Webdriver
This Test contains following Test cases
1. Check all the URLs on Hotel page
2. Check customer registration with Test data in Suite1.xls
3. Check customer login with Test data in Suite1.xls
4. Check Hotel booking with Test data in Suite1.xls

Run these test cases with a Suite Runner. Run the Test case only if Test Mode is Y in Suite1.xls. Create an HTML report for these Tests

This Project has following Packages

BaseClasses
- BaseClass.java - 
- ExtentManager.java - This is Base clase used to create HTML Reports

config
config.properties - It contains all selector. The name should contain _xpath, _name, _id,_css if the selector is xpath, name, id OR cssSelector respectively
or.properties - Object Repository
Suite1.xlsx - Test data for Test scenarios

datatable
Xls_Reader.java - This is a base file used to read data from Excel Sheet

Tests.Suite1
findBrokenLinks.java
HotelBooking_Test.java
LoginTest.java
MySuiteRunner.java
Register_Test.java
