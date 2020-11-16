package com.albago.util;

import java.text.ParseException;
import java.util.Calendar;

public class Test {

	public static void main(String args[]) throws ParseException {

		Calendar now = Calendar.getInstance();
		System.out.println("Calendar now: " + now.getTime());
		now.set(Calendar.DAY_OF_WEEK, 3);
		System.out.println("Calendar now: " + now.getTime());
	}
}
