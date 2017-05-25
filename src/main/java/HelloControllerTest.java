import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import example.HelloController;

public class HelloControllerTest {

	String message = "Robert";	
	   HelloController hello = new HelloController();
	 
	   @Test
	   public void testSalutationMessage() {
	      System.out.println("Inside testSalutationMessage()");
	      message = "Hi!" + "Robert";
	      LocalDateTime fromdate = LocalDateTime.parse("2017-01-25 10:42:38", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	      LocalDateTime todate = LocalDateTime.parse("2017-01-25 12:42:38", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	      System.out.println("Da ="+ hello.getTheDate("2017-01-25 10:42:38", "mm-dd-yyyy").toString());
			System.out.println("  te = ="+ hello.getTheDate("2017-01-25 10:42:38", "yyyy-MM-dd").toString());
//	      System.out.println( " Period = "+hello.getPeriod(fromdate, todate).getDays());
//	      System.out.println( " hour = "+hello.getTheDate(fromdate, "MM-dd-yyyy") + hello.getTheDate(todate, "MM-dd-yyyy"));
	   }
	   
}


