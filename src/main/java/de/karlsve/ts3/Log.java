package de.karlsve.ts3;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class Log {
	
	public static void d(Object ...args) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		Date current = Calendar.getInstance().getTime();
		for (Object arg : args) {
			System.out.printf("%s: DEBUG %s%n", format.format(current), arg);
		}
	}

	public static void e(Exception e) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		Date current = Calendar.getInstance().getTime();
		System.out.printf("%s: ERROR %s%n", format.format(current), e.getMessage());
	}

	public static void e(String msg) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		Date current = Calendar.getInstance().getTime();
		System.out.printf("%s: ERROR %s%n", format.format(current), msg);
	}
	
}
