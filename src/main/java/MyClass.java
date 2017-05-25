import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import ch.qos.logback.classic.net.SyslogAppender;

public class MyClass {
public static void main(String[] args) throws ParseException {
	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
	Calendar cal = Calendar.getInstance();
	String toDate = ft.format(cal.getTime());
	String date = new SimpleDateFormat("yyyy-MM-dd").parse("2017-03-23 11:00:36").toString();
	
	LocalDateTime fromdate = LocalDateTime.parse("2017-01-25 10:42:38", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	
//	System.out.println(toDate);
	System.out.println(fromdate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
}
}
