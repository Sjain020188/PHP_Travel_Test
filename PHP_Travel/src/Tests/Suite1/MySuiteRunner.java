package Tests.Suite1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;



@RunWith(Suite.class)
@SuiteClasses({
	Register_Test.class,
	LoginTest.class,
	findBrokenLinks.class,
	HotelBooking_Test.class})
public class MySuiteRunner {

}
