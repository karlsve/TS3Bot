package de.karlsve.ts3;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class Log {
	
	public static void d(Object object) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		Date current = Calendar.getInstance().getTime();
		System.out.printf("%s: DEBUG %s%n", format.format(current), object);
	}

	public static void e(Exception e) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		Date current = Calendar.getInstance().getTime();
		System.out.printf("%s: ERROR %s%n", format.format(current), e.getMessage());
	}
	
}
